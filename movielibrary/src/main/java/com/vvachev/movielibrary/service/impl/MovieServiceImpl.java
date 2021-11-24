package com.vvachev.movielibrary.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.PictureEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.MovieServiceModel;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.service.interfaces.ICategoryService;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.service.interfaces.IPictureService;
import com.vvachev.movielibrary.service.interfaces.IUserService;

@Service
public class MovieServiceImpl implements IMovieService {

	private final MovieRepository movieRepository;
	private final ModelMapper mapper;
	private final IUserService userService;
	private final ICategoryService categoryService;
	private final IPictureService pictureService;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository, ModelMapper mapper, IUserService userRepository,
			CloudinaryServiceImpl cloudinaryService, ICategoryService categoryService, IPictureService pictureService) {
		this.movieRepository = movieRepository;
		this.mapper = mapper;
		this.userService = userRepository;
		this.categoryService = categoryService;
		this.pictureService = pictureService;
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
		UserEntity userEntity = userService.findByUsername(username);
		Set<CategoryEntity> categoryEntity = movieServiceModel.getCategories().stream().map(cat -> convertToEntity(cat))
				.collect(Collectors.toSet());

		MovieEntity movieEntity = mapper.map(movieServiceModel, MovieEntity.class);
		movieEntity.setAuthor(userEntity);
		movieEntity.setCategories(categoryEntity);

		movieEntity = movieRepository.save(movieEntity);

		PictureEntity pictureEntity = pictureService.uploadPicture(movieServiceModel.getPicture(), username,
				movieServiceModel.getTitle());

		movieEntity.setPictures(Set.of(pictureEntity));

		movieRepository.save(movieEntity);
	}

	@Override
	public MovieEntity findByTitle(String movieTitle) {
		return movieRepository.findByTitle(movieTitle).orElseThrow(
				() -> new EntityNotFoundException(String.format("Movie with title %s not found!", movieTitle)));
	}

	@Override
	public List<MovieServiceModel> getUseresMovies(String username) {
		UserEntity userEntity = userService.findByUsername(username);

		List<MovieServiceModel> usersMovies = userEntity.getMovies().stream()
				.map(movEnt -> convertToServiceModel(movEnt, username)).collect(Collectors.toList());

		return usersMovies;
	}

	private CategoryEntity convertToEntity(CategoryEnum name) {
		return categoryService.findByCategoryName(name);
	}

	private MovieServiceModel convertToServiceModel(MovieEntity movieEntity, String username) {
		MovieServiceModel movieServiceModel = mapper.map(movieEntity, MovieServiceModel.class);
		movieServiceModel.setPictureUrl(movieEntity.getPictures().iterator().next().getUrl());
		Set<CategoryEnum> categories = movieEntity.getCategories().stream().map(catEnt -> catEnt.getName())
				.collect(Collectors.toSet());
		movieServiceModel.setCategories(categories);
		movieServiceModel.setAuthor(username);

		return movieServiceModel;
	}
}
