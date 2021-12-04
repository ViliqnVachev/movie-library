package com.vvachev.movielibrary.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.CommentEntity;
import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.service.CommentServiceModel;
import com.vvachev.movielibrary.model.view.CommentViewModel;
import com.vvachev.movielibrary.repository.CommentRepository;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.ICommentService;
import com.vvachev.movielibrary.web.exceptions.NotFoundException;

@Service
public class CommentServiceImpl implements ICommentService {

	private final MovieRepository movieRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final ModelMapper mapper;

	@Autowired
	public CommentServiceImpl(MovieRepository movieRepository, UserRepository userRepository,
			CommentRepository commentRepository, ModelMapper mapper) {
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public List<CommentViewModel> getComments(Long movieId) {
		Optional<MovieEntity> movieEnity = movieRepository.findById(movieId);

		if (movieEnity.isEmpty()) {
			throw new NotFoundException(String.format("Movie with id %s not found!", movieId));
		}

		return movieEnity.get().getComments().stream().map(this::convertToView).collect(Collectors.toList());
	}

	@Override
	public CommentViewModel addComment(CommentServiceModel commentServiceModel) {
		Objects.requireNonNull(commentServiceModel.getAuthor());

		MovieEntity movieEntity = movieRepository.findById(commentServiceModel.getMovieId())
				.orElseThrow(() -> new NotFoundException("Movie is not found!"));

		UserEntity userEntity = userRepository.findByUsername(commentServiceModel.getAuthor())
				.orElseThrow(() -> new NotFoundException(
						String.format("User with name %s not found!", commentServiceModel.getAuthor())));
		CommentEntity commentEntity = mapper.map(commentServiceModel, CommentEntity.class);
		commentEntity.setAuthor(userEntity);
		commentEntity.setMovie(movieEntity);
		commentEntity.setCreated(LocalDate.now());

		CommentViewModel commentViewModel = convertToView(commentRepository.save(commentEntity));

		return commentViewModel;
	}

	private CommentViewModel convertToView(CommentEntity commentEntity) {
		CommentViewModel commentViewModel = new CommentViewModel();
		commentViewModel.setId(commentEntity.getId());
		commentViewModel
				.setCreated(commentEntity.getCreated().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		commentViewModel.setCommentContent(commentEntity.getCommentContent());
		commentViewModel.setAuthor(commentEntity.getAuthor().getFullName());
		return commentViewModel;
	}

}
