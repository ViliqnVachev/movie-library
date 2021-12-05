package com.vvachev.movielibrary.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.repository.RoleRepository;
import com.vvachev.movielibrary.repository.UserRepository;

@WithMockUser("vvachev")
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

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
		userRepository.deleteAll();
	}

	@Test
	void testOpenHome() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

}
