package com.vvachev.movielibrary.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.vvachev.movielibrary.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private static final String USERNAME = "vvachev";
	private static final String PASSWORD = "vvachev";
	private static final String EMAIL = "vvachev@abv.bg";
	private static final String FULL_NAME = "Viliyan Vachev";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void testOpenRegisterForm() throws Exception {
		mockMvc.perform(get("/users/register")).andExpect(status().isOk()).andExpect(view().name("register"));
	}

	@Test
	void testRegisterUser() throws Exception {
		mockMvc.perform(post("/users/register").
				param("username", USERNAME).//
				param("password", PASSWORD).//
				param("confirmPassword", PASSWORD).//
				param("fullName", FULL_NAME).//
				param("email", EMAIL).//
				param("age", "26").//
				with(csrf())
				).andExpect(status().is3xxRedirection());

				Assert.assertEquals(2, userRepository.count());
	}

}
