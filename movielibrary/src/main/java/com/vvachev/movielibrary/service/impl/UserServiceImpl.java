package com.vvachev.movielibrary.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepo;
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepo, ModelMapper mapper, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void initUsers() {
		// TODO Auto-generated method stub

	}

}
