package com.vvachev.movielibrary.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.repository.RoleRepository;
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

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@AfterEach
	void clean() {
		userRepository.deleteAll();
	}

	@Test
	void testOpenRegisterForm() throws Exception {
		mockMvc.perform(get("/users/register")).andExpect(status().isOk()).andExpect(view().name("register"));
	}

	@Test
	void testRegisterUser() throws Exception {
		mockMvc.perform(post("/users/register").param("username", USERNAME).//
				param("password", PASSWORD).//
				param("confirmPassword", PASSWORD).//
				param("fullName", FULL_NAME).//
				param("email", EMAIL).//
				param("age", "26").//
				with(csrf())).andExpect(status().is3xxRedirection());

		UserEntity newUser = userRepository.findByUsername(USERNAME).get();

		Assert.assertEquals(newUser.getUsername(), USERNAME);
		Assert.assertEquals(newUser.getEmail(), EMAIL);
	}

	@Test
	void testRegisterUserWithDifferntPasswords() throws Exception {
		mockMvc.perform(post("/users/register").param("username", USERNAME).//
				param("password", PASSWORD).//
				param("confirmPassword", "confirmPassword").//
				param("fullName", FULL_NAME).//
				param("email", EMAIL).//
				param("age", "26").//
				with(csrf())).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/users/register"));
	}

	@Test
	void testRegisterUserWithNotUniqueName() throws Exception {
		mockMvc.perform(post("/users/register").param("username", "admin").//
				param("password", PASSWORD).//
				param("confirmPassword", "confirmPassword").//
				param("fullName", FULL_NAME).//
				param("email", EMAIL).//
				param("age", "26").//
				with(csrf())).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/users/register"));
	}

	@Test
	void testOpenLoginForm() throws Exception {
		mockMvc.perform(get("/users/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	void testLoginUser() throws Exception {
		encoder = new Pbkdf2PasswordEncoder();
		RoleEntity role = roleRepository.findByRole(RoleEnum.USER).get();

		UserEntity newUser = new UserEntity();
		newUser.setUsername(USERNAME);
		newUser.setFullName(FULL_NAME);
		newUser.setPassword(encoder.encode(PASSWORD));
		newUser.setAge(23);
		newUser.setEmail(EMAIL);
		newUser.setRoles(List.of(role));
		userRepository.save(newUser);

		Assert.assertEquals(newUser.getUsername(), USERNAME);
		Assert.assertEquals(newUser.getEmail(), EMAIL);

		mockMvc.perform(post("/users/login").//
				param("username", newUser.getUsername()).//
				param("password", PASSWORD).//
				with(csrf())).andExpect(status().isOk());

	}

}
