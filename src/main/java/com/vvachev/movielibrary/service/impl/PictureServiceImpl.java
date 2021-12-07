package com.vvachev.movielibrary.service.impl;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.PictureEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.service.CloudinaryImage;
import com.vvachev.movielibrary.model.service.PictureServiceModel;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.repository.PictureRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.IPictureService;
import com.vvachev.movielibrary.utils.exceptions.NotFoundException;

@Service
public class PictureServiceImpl implements IPictureService {

	private final CloudinaryServiceImpl cloudinaryServiceImpl;
	private final PictureRepository pictureRepository;
	private final UserRepository userRepository;
	private final ModelMapper mapper;
	private final MovieRepository movieRepository;

	@Autowired
	public PictureServiceImpl(CloudinaryServiceImpl cloudinaryServiceImpl, PictureRepository pictureRepository,
			UserRepository userRepository, ModelMapper mapper, MovieRepository movieRepository) {
		this.cloudinaryServiceImpl = cloudinaryServiceImpl;
		this.pictureRepository = pictureRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.movieRepository = movieRepository;
	}

	@Override
	public PictureServiceModel uploadPicture(MultipartFile multipartFile, String username, String movieTitle)
			throws IOException {
		CloudinaryImage image = cloudinaryServiceImpl.upload(multipartFile);

		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));
		MovieEntity movieEntity = movieRepository.findByTitle(movieTitle)
				.orElseThrow(() -> new NotFoundException(String.format("Movie with title %s not found!", movieTitle)));

		PictureEntity pictureEntity = new PictureEntity();
		pictureEntity.setPublicId(image.getPublicId());
		pictureEntity.setTitle(movieTitle);
		pictureEntity.setUrl(image.getUrl());
		pictureEntity.setAuthor(userEntity);
		pictureEntity.setMovie(movieEntity);

		return mapper.map(pictureRepository.save(pictureEntity), PictureServiceModel.class);
	}

	@Override
	public void deletePicture(String publicId) {
		cloudinaryServiceImpl.delete(publicId);
	}
}
