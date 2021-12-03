package com.vvachev.movielibrary.config;

import java.util.Collections;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.Cloudinary;
import com.vvachev.movielibrary.utils.CloudinaryConstants;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
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

	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Book REST API", "This is a REST API for Books and authors", "API TOS", "Terms of service",
				new Contact("Viliyan Vachev", "https://github.com/ViliqnVachev", "viliqnvachev@abv.bg"), "License of API",
				"API license URL", Collections.emptyList());
	}

}
