package com.vvachev.movielibrary.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.service.CloudinaryImage;

public interface ICloudinaryService {

	CloudinaryImage upload(MultipartFile file) throws IOException;

	boolean delete(String publicId);
}
