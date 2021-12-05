package com.vvachev.movielibrary.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.repository.MovieRepository;
import com.vvachev.movielibrary.repository.PictureRepository;
import com.vvachev.movielibrary.repository.RoleRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.impl.CloudinaryServiceImpl;

@WithMockUser("vvachev")
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

	private static final String USERNAME = "vvachev";
	private static final String PASSWORD = "vvachev";
	private static final String EMAIL = "vvachev@abv.bg";
	private static final String FULL_NAME = "Viliyan Vachev";

	private String imageUrl;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private PictureRepository pictureRepository;

	@Autowired
	private CloudinaryServiceImpl cloudinaryServiceImpl;

	private UserEntity userEntity;

	@BeforeEach
	void setUp() {

		RoleEntity role = roleRepository.findByRole(RoleEnum.USER).get();

		userEntity = new UserEntity();
		userEntity.setPassword(PASSWORD);
		userEntity.setUsername(USERNAME);
		userEntity.setEmail(EMAIL);
		userEntity.setFullName(FULL_NAME);
		userEntity.setAge(15);
		userEntity.setRoles(List.of(role));

		userEntity = userRepository.save(userEntity);
	}

	@AfterEach
	void clean() {
		pictureRepository.deleteAll();
		movieRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void testOpenAddMovieForm() throws Exception {
		mockMvc.perform(get("/movies/add")).andExpect(status().isOk()).andExpect(view().name("movie-add"));
	}

	@Test
	void testAddMovie() throws Exception {
		try {
			Path path = Paths.get("src/main/resources/static/images/glass.jpg");
			byte[] data = Files.readAllBytes(path);

			MockMultipartFile fileMock = new MockMultipartFile("glass.jpg", "glass.jpg", MediaType.IMAGE_JPEG_VALUE,
					data);

			mockMvc.perform(MockMvcRequestBuilders.multipart("/movies/add").file("picture", fileMock.getBytes()).//
					param("title", "newMovie").//
					param("resume", "Test resume for newMovie").//
					param("videoUrl", "12345678901").//
					param("releaseDate", LocalDate.now().toString()).//
					param("categories", CategoryEnum.ACTION.name()).//
					with(csrf())).andExpect(status().is3xxRedirection());

			MovieEntity entity = movieRepository.findByTitle("newMovie").get();
			imageUrl = pictureRepository.findAll().get(0).getPublicId();

			Assert.assertEquals(entity.getTitle(), "newMovie");
			Assert.assertEquals(entity.getResume(), "Test resume for newMovie");
		} finally {
			cloudinaryServiceImpl.delete(imageUrl);
		}
	}

	@Test
	void testAddMovieAndMyMovieView() throws Exception {
		try {
			Path path = Paths.get("src/main/resources/static/images/glass.jpg");
			byte[] data = Files.readAllBytes(path);

			MockMultipartFile fileMock = new MockMultipartFile("glass.jpg", "glass.jpg", MediaType.IMAGE_JPEG_VALUE,
					data);

			mockMvc.perform(MockMvcRequestBuilders.multipart("/movies/add").file("picture", fileMock.getBytes()).//
					param("title", "newMovie").//
					param("resume", "Test resume for newMovie").//
					param("videoUrl", "12345678901").//
					param("releaseDate", LocalDate.now().toString()).//
					param("categories", CategoryEnum.ACTION.name()).//
					with(csrf())).andExpect(status().is3xxRedirection());

			MovieEntity entity = movieRepository.findByTitle("newMovie").get();
			imageUrl = pictureRepository.findAll().get(0).getPublicId();

			Assert.assertEquals(entity.getTitle(), "newMovie");
			Assert.assertEquals(entity.getResume(), "Test resume for newMovie");

			mockMvc.perform(get("/movies/mymovies")).andExpect(status().isOk()).andExpect(view().name("my-movies"));

		} finally {
			cloudinaryServiceImpl.delete(imageUrl);
		}
	}

}
