package com.vvachev.movielibrary.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.service.PictureServiceModel;

public interface IPictureService {

	PictureServiceModel uploadPicture(MultipartFile multipartFile, String username, String movieTitle)
			throws IOException;

	void deletePicture(String publicId);

}
