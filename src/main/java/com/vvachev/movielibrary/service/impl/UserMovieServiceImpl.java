package com.vvachev.movielibrary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.utils.exceptions.NotFoundException;

@Service
public class UserMovieServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserMovieServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));
		return convertToUserDetails(user);
	}

	private static UserDetails convertToUserDetails(UserEntity userEntity) {
		List<GrantedAuthority> auhtorities = userEntity.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).collect(Collectors.toList());

		// User is the spring implementation of UserDetails interface.
		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isActive(), true, true, true,
				auhtorities);
	}

}
