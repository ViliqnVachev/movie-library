package com.vvachev.movielibrary.config;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import com.cloudinary.Cloudinary;
import com.vvachev.movielibrary.utils.CloudinaryConstants;

@Configuration
public class AppConfig {

	private final CloudinaryConfig cnf;

	@Autowired
	public AppConfig(CloudinaryConfig cnf) {
		this.cnf = cnf;
	}

	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(Map.of(CloudinaryConstants.CLOUD_NAME, cnf.getCloudName(), CloudinaryConstants.API_KEY,
				cnf.getApiKey(), CloudinaryConstants.API_SECRET, cnf.getApiSecret()));
	}

}
