package com.vvachev.movielibrary.service.impl;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.PictureEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.service.CloudinaryImage;
import com.vvachev.movielibrary.repository.PictureRepository;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.service.interfaces.IPictureService;
import com.vvachev.movielibrary.service.interfaces.IUserService;

@Service
public class PictureServiceImpl implements IPictureService {

	private final CloudinaryServiceImpl cloudinaryServiceImpl;
	private final PictureRepository pictureRepository;
	private final ModelMapper mapper;
	private final IUserService userService;
	private final IMovieService movieService;

	@Autowired
	public PictureServiceImpl(CloudinaryServiceImpl cloudinaryServiceImpl, PictureRepository pictureRepository,
			ModelMapper mapper, IUserService userService, @Lazy IMovieService movieService) {
		this.cloudinaryServiceImpl = cloudinaryServiceImpl;
		this.pictureRepository = pictureRepository;
		this.mapper = mapper;
		this.userService = userService;
		this.movieService = movieService;
	}

	@Override
	public PictureEntity uploadPicture(MultipartFile multipartFile, String username, String movieTitle)
			throws IOException {
		CloudinaryImage image = cloudinaryServiceImpl.upload(multipartFile);

		UserEntity userEntity = userService.findByUsername(username);
		MovieEntity movieEntity = movieService.findByTitle(movieTitle);

		PictureEntity pictureEntity = new PictureEntity();
		pictureEntity.setPublicId(image.getPublicId());
		pictureEntity.setTitle(movieTitle);
		pictureEntity.setUrl(image.getUrl());
		pictureEntity.setAuthor(userEntity);
		pictureEntity.setMovie(movieEntity);

		return pictureRepository.save(pictureEntity);
	}

	@Override
	public void deletePicture(String publicId) {
		cloudinaryServiceImpl.delete(publicId);

	}
}
