package com.vvachev.movielibrary.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.vvachev.movielibrary.model.service.CloudinaryImage;
import com.vvachev.movielibrary.service.interfaces.ICloudinaryService;

@Service
public class CloudinaryServiceImpl implements ICloudinaryService {
	private static final String TEMP_FILE = "temp-file";
	private static final String URL = "url";
	private static final String PUBLIC_ID = "public_id";

	private final Cloudinary cloudinary;

	public CloudinaryServiceImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	@Override
	public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

		File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
		multipartFile.transferTo(tempFile);
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> uploadResult = cloudinary.uploader().upload(tempFile, Map.of());

			String url = uploadResult.getOrDefault(URL,
					"https://st.depositphotos.com/1653909/4590/i/950/depositphotos_45905257-stock-photo-movie-clapper.jpg");
			String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

			return new CloudinaryImage().setPublicId(publicId).setUrl(url);

		} finally {
			tempFile.delete();
		}
	}

	@Override
	public boolean delete(String publicId) {
		try {
			this.cloudinary.uploader().destroy(publicId, Map.of());
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
