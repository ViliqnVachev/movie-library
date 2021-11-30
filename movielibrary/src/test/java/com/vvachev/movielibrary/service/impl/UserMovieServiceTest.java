package com.vvachev.movielibrary.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserMovieServiceTest {

	private UserEntity user;
	private RoleEntity adminRole;
	private RoleEntity userRole;
	private UserMovieServiceImpl userMovieService;

	@Mock
	private UserRepository mockUserRepository;

	@BeforeEach
	void init() {

		// ARRANGE
		userMovieService = new UserMovieServiceImpl(mockUserRepository);

		adminRole = new RoleEntity();
		adminRole.setRole(RoleEnum.ADMIN);

		userRole = new RoleEntity();
		userRole.setRole(RoleEnum.USER);

		user = new UserEntity();
		user.setUsername("vvachev");
		user.setEmail("vvachev@test.com");
		user.setPassword("1234");
		user.setActive(true);
		user.setRoles(List.of(adminRole, userRole));
	}

	@Test
	void testUserNotFound() {
		Assertions.assertThrows(UsernameNotFoundException.class,
				() -> userMovieService.loadUserByUsername("invalid_user_email@not-exist.com"));
	}

	@Test
	void testUserFound() {

		// Arrange
		Mockito.when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

		// Act
		UserDetails actual = userMovieService.loadUserByUsername(user.getUsername());

		// Assert

		String expectedRoles = "ROLE_ADMIN, ROLE_USER";
		String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));

		Assertions.assertEquals(actual.getUsername(), user.getUsername());
		Assertions.assertEquals(expectedRoles, actualRoles);
	}

}
