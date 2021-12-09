package com.vvachev.movielibrary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.vvachev.movielibrary.utils.exceptions.NotFoundException;

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
	public void initUsers() {
		if (this.userRepository.count() == 0) {
			RoleEntity adminRole = mapper.map(roleService.findByRole(RoleEnum.ADMIN), RoleEntity.class);
			RoleEntity userRole = mapper.map(roleService.findByRole(RoleEnum.USER), RoleEntity.class);

			UserEntity admin = new UserEntity();
			admin.setUsername(AppConstants.UserConfiguration.ADMIN);
			admin.setFullName(AppConstants.UserConfiguration.ADMIN);
			admin.setPassword(passwordEncoder.encode(AppConstants.UserConfiguration.ADMIN));
			admin.setRoles(List.of(adminRole, userRole));
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
	public UserServiceModel register(UserServiceModel userServiceModel) {

		RoleEntity role = mapper.map(roleService.findByRole(RoleEnum.USER), RoleEntity.class);

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userServiceModel.getUsername());
		userEntity.setAge(userServiceModel.getAge());
		userEntity.setFullName(userServiceModel.getFullName());
		userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
		userEntity.setActive(true);
		userEntity.setEmail(userServiceModel.getEmail());
		userEntity.setRoles(List.of(role));

		userRepository.save(userEntity);
		return mapper.map(userEntity, UserServiceModel.class);
	}

	@Override
	public UserServiceModel findByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));

		return mapToServiceModel(userEntity);
	}

	@Transactional
	@Override
	public UserServiceModel getCurrentUser(String username) {
		return findByUsername(username);
	}

	@Override
	public void changePassowrd(String newPassword, String username) {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User with name %s not found!", username)));
		userEntity.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(userEntity);
	}

	@Transactional
	@Override
	public List<UserServiceModel> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return users.stream().map(user -> mapToServiceModel(user)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public UserServiceModel disableUser(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("User with id %d not found!", id)));

		userEntity.setActive(false);

		return mapToServiceModel(userRepository.save(userEntity));
	}

	@Transactional
	@Override
	public void enableUsers() {
		List<UserEntity> disabledUsers = userRepository.findAllDisabledUsers();

		for (UserEntity userEntity : disabledUsers) {
			userEntity.setActive(true);
			userRepository.save(userEntity);
		}

	}

	@Transactional
	public boolean isAdmin(String username) {
		UserServiceModel user = findByUsername(username);
		if (user.getRoles().contains(RoleEnum.ADMIN.name())) {
			return true;
		}
		return false;
	}

	private UserServiceModel mapToServiceModel(UserEntity entity) {
		UserServiceModel userServiceModel = mapper.map(entity, UserServiceModel.class);
		List<String> roles = entity.getRoles().stream().map(ent -> ent.getRole().name()).collect(Collectors.toList());
		userServiceModel.setRoles(roles);
		userServiceModel.setMovies(entity.getMovies().stream().map(ent -> ent.getTitle()).collect(Collectors.toList()));
		return userServiceModel;
	}

}
