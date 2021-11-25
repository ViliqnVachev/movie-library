package com.vvachev.movielibrary.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.management.relation.RoleNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.UserServiceModel;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.IRoleService;
import com.vvachev.movielibrary.service.interfaces.IUserService;
import com.vvachev.movielibrary.utils.AppConstants;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final IRoleService roleService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder,
			IRoleService roleService) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	@Override
	public void initUsers() throws RoleNotFoundException {
		if (this.userRepository.count() == 0) {
			RoleEntity adminRole = mapper.map(roleService.findByRole(RoleEnum.ADMIN), RoleEntity.class);
			RoleEntity userRole = mapper.map(roleService.findByRole(RoleEnum.USER), RoleEntity.class);

			UserEntity admin = new UserEntity();
			admin.setUsername(AppConstants.UserConfiguration.ADMIN);
			admin.setFullName(AppConstants.UserConfiguration.ADMIN);
			admin.setPassword(passwordEncoder.encode(AppConstants.UserConfiguration.ADMIN));
			admin.setRoles(Set.of(adminRole, userRole));
			admin.setActive(true);
			admin.setEmail(AppConstants.UserConfiguration.ADMIN_EMAIL);

			userRepository.save(admin);
		}
	}

	@Override
	public boolean isUniqueEmail(String email) {
		UserEntity user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isUniqueName(String username) {
		UserEntity user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			return true;
		}
		return false;
	}

	@Override
	public UserServiceModel register(UserServiceModel userServiceModel) throws RoleNotFoundException {

		RoleEntity role = mapper.map(roleService.findByRole(RoleEnum.USER), RoleEntity.class);

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userServiceModel.getUsername());
		userEntity.setAge(userServiceModel.getAge());
		userEntity.setFullName(userServiceModel.getFullName());
		userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
		userEntity.setActive(true);
		userEntity.setEmail(userServiceModel.getEmail());
		userEntity.setRoles(Set.of(role));

		userRepository.save(userEntity);
		return mapper.map(userEntity, UserServiceModel.class);
	}

	@Override
	public UserServiceModel findByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with name %s not found!", username)));

		return mapper.map(userEntity, UserServiceModel.class);
	}

	@Override
	public UserServiceModel getCurrentUser(String username) {
		UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with name %s not found!", username)));
		UserServiceModel model = mapper.map(userEntity, UserServiceModel.class);
		model.setMovies(userEntity.getMovies().stream().map(ent -> ent.getTitle()).collect(Collectors.toSet()));
		return model;
	}

}
