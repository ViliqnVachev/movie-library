package com.vvachev.movielibrary.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.PictureEntity;
import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.MovieServiceModel;
import com.vvachev.movielibrary.model.service.PictureServiceModel;
import com.vvachev.movielibrary.model.view.MovieDetailsView;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.repository.PictureRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.ICategoryService;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.service.interfaces.IPictureService;
import com.vvachev.movielibrary.utils.exceptions.NotFoundException;

@Service
public class MovieServiceImpl implements IMovieService {

	private final MovieRepository movieRepository;
	private final ModelMapper mapper;
	private final ICategoryService categoryService;
	private final IPictureService pictureService;
	private final UserRepository userRepository;
	private final PictureRepository pictureRepository;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository, ModelMapper mapper, ICategoryService categoryService,
			IPictureService pictureService, UserRepository userRepository, PictureRepository pictureRepository) {
		this.movieRepository = movieRepository;
		this.mapper = mapper;
		this.categoryService = categoryService;
		this.pictureService = pictureService;
		this.userRepository = userRepository;
		this.pictureRepository = pictureRepository;
	}

	@Override
	public boolean isUniqueTitle(String title) {
		MovieEntity movieEntity = movieRepository.findByTitle(title).orElse(null);
		if (movieEntity == null) {
			return true;
		}
		return false;
	}

	@Override
	public void createMovie(MovieServiceModel movieServiceModel, String username) throws IOException {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));
		List<CategoryEntity> categoryEntity = movieServiceModel.getCategories().stream()
				.map(cat -> convertToEntity(cat)).collect(Collectors.toList());

		MovieEntity movieEntity = mapper.map(movieServiceModel, MovieEntity.class);
		movieEntity.setAuthor(userEntity);
		movieEntity.setCategories(categoryEntity);
		movieEntity = movieRepository.save(movieEntity);

		PictureServiceModel pictureModel = pictureService.uploadPicture(movieServiceModel.getPicture(), username,
				movieServiceModel.getTitle());
		PictureEntity pictureEntity = pictureRepository.findById(pictureModel.getId()).orElseThrow(
				() -> new NotFoundException(String.format("Picture with id %d not found!", pictureModel.getId())));
		List<PictureEntity> pictures = new ArrayList<PictureEntity>();
		pictures.add(pictureEntity);
		movieEntity.setPictures(pictures);

		movieRepository.save(movieEntity);
	}

	@Override
	public MovieServiceModel findByTitle(String movieTitle) {
		return mapper.map(movieRepository.findByTitle(movieTitle), MovieServiceModel.class);
	}

	@Transactional
	@Override
	public List<MovieServiceModel> getUseresMovies(String username) {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));

		List<MovieServiceModel> usersMovies = userEntity.getMovies().stream().map(movEnt -> {
			MovieServiceModel model = convertToServiceModel(movEnt, username);
			model.setRaiting(calculateRating(movEnt));
			return model;
		}).collect(Collectors.toList());

		return usersMovies;
	}

	@Transactional
	@Override
	public MovieDetailsView findById(Long id, String username) {
		MovieDetailsView view = movieRepository.findById(id).map(movEnt -> mapDetailsView(movEnt, username))
				.orElseThrow(() -> new NotFoundException("Movie is not found!"));
		return view;
	}

	@Transactional
	@Override
	public void deleteMovie(Long id) {
		MovieEntity entity = movieRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Movie is not found!"));
		List<PictureEntity> pictures = entity.getPictures();
		pictureService.deletePicture(pictures.iterator().next().getPublicId());
		movieRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void voteMovie(Long id, String username, boolean isLike) {
		MovieEntity movieEntity = movieRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Movie is not found!"));
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));

		if (isLike) {
			if (movieEntity.getLikes() == null) {
				movieEntity.setLikes(List.of(userEntity));
			} else {
				List<UserEntity> likes = movieEntity.getLikes();
				likes.add(userEntity);
				movieEntity.setLikes(likes);
			}
		}

		if (!isLike) {
			if (movieEntity.getDislikes() == null) {
				movieEntity.setDislikes(List.of(userEntity));
			} else {
				List<UserEntity> dislikes = movieEntity.getDislikes();
				dislikes.add(userEntity);
				movieEntity.setDislikes(dislikes);
			}
		}

		movieRepository.save(movieEntity);
	}

	@Transactional
	@Override
	public List<MovieServiceModel> getTopMovies() {
		List<MovieServiceModel> movies = movieRepository.findAll().stream()
				.map(ent -> convertToServiceModel(ent, ent.getAuthor().getUsername()))
				.sorted((a, b) -> Double.compare(b.getRaiting(), a.getRaiting())).collect(Collectors.toList());
		List<MovieServiceModel> topMovies = new ArrayList<>();
		if (movies.size() < 3) {
			return movies;
		}
		for (int i = 0; i < 3; i++) {
			topMovies.add(movies.get(i));
		}
		return topMovies;
	}

	@Transactional
	@Override
	public List<MovieServiceModel> getRecentMovies() {
		List<MovieServiceModel> movies = movieRepository.findAll().stream()
				.sorted((a, b) -> a.getLikes().size() - b.getDislikes().size())
				.map(ent -> convertToServiceModel(ent, ent.getAuthor().getUsername())).collect(Collectors.toList());
		List<MovieServiceModel> recentMovies = new ArrayList<>();
		if (movies.size() < 5) {
			return movies;
		}
		for (int i = 0; i < 5; i++) {
			recentMovies.add(movies.get(i));
		}
		return recentMovies;
	}

	@Transactional
	@Override
	public List<MovieServiceModel> getAllMovies() {
		return movieRepository.findAll().stream().sorted((a, b) -> a.getLikes().size() - b.getDislikes().size())
				.map(ent -> convertToServiceModel(ent, ent.getAuthor().getUsername())).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public boolean canDelete(String username, Long id) {
		Optional<MovieEntity> movieOpt = movieRepository.findById(id);
		UserEntity caller = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));

		if (movieOpt.isEmpty()) {
			return false;
		}
		MovieEntity movieEntity = movieOpt.get();

		return isAdmin(caller) || movieEntity.getAuthor().getUsername().equals(username);
	}

	@Transactional
	@Override
	public boolean canVote(String currentUser, Long id) {
		Optional<MovieEntity> movieOpt = movieRepository.findById(id);
		if (isOwner(currentUser, id) || alreadyVoted(movieOpt.get(), currentUser) || movieOpt.isEmpty()) {
			return false;
		}

		return true;
	}

	private MovieDetailsView mapDetailsView(MovieEntity movie, String currentUser) {
		MovieDetailsView view = mapper.map(movie, MovieDetailsView.class);
		view.setCanVote(canVote(currentUser, movie.getId()));
		view.setRaiting(String.format("%.2f", calculateRating(movie)));
		return view;
	}

	private double calculateRating(MovieEntity movie) {
		double likes = movie.getLikes().size();
		double dislikes = movie.getDislikes().size();

		if (likes == 0 && dislikes == 0) {
			return 0;
		}

		return (likes / (likes + dislikes)) * 10;
	}

	private boolean alreadyVoted(MovieEntity movieEntity, String username) {
		for (UserEntity user : movieEntity.getLikes()) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}

		for (UserEntity user : movieEntity.getDislikes()) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}

		return false;
	}

	private boolean isOwner(String username, Long id) {
		Optional<MovieEntity> movieOpt = movieRepository.findById(id);

		if (movieOpt.isEmpty()) {
			return false;
		}
		MovieEntity movieEntity = movieOpt.get();

		return movieEntity.getAuthor().getUsername().equals(username);

	}

	private boolean isAdmin(UserEntity user) {
		return user.getRoles().stream().map(RoleEntity::getRole).anyMatch(r -> r == RoleEnum.ADMIN);
	}

	private CategoryEntity convertToEntity(CategoryEnum name) {
		return mapper.map(categoryService.findByCategoryName(name), CategoryEntity.class);
	}

	private MovieServiceModel convertToServiceModel(MovieEntity movieEntity, String username) {
		MovieServiceModel movieServiceModel = mapper.map(movieEntity, MovieServiceModel.class);
		movieServiceModel.setPictureUrl(movieEntity.getPictures().iterator().next().getUrl());
		List<CategoryEnum> categories = movieEntity.getCategories().stream().map(catEnt -> catEnt.getName())
				.collect(Collectors.toList());
		movieServiceModel.setCategories(categories);
		movieServiceModel.setAuthor(username);
		movieServiceModel.setRaiting(calculateRating(movieEntity));

		return movieServiceModel;
	}
}
