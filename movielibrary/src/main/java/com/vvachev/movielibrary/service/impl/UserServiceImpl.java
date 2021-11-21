package com.vvachev.movielibrary.service.impl;

import java.util.Set;

import javax.management.relation.RoleNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.UserServiceModel;
import com.vvachev.movielibrary.repository.RoleRepository;
import com.vvachev.movielibrary.repository.UserRepository;
import com.vvachev.movielibrary.service.interfaces.IUserService;
import com.vvachev.movielibrary.utils.AppConstants;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;
	private final ModelMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepositoy;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder,
			RoleRepository roleRepositoy) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepositoy = roleRepositoy;
	}

	@Override
	public void initUsers() {
		if (this.userRepository.count() == 0) {
			RoleEntity adminRole = roleRepositoy.findByRole(RoleEnum.ADMIN).orElse(null);
			RoleEntity userRole = roleRepositoy.findByRole(RoleEnum.USER).orElse(null);

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

		RoleEntity role = roleRepositoy.findByRole(RoleEnum.USER).orElseThrow(
				() -> new RoleNotFoundException(String.format("Role with name %s not found!", RoleEnum.USER.name())));

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

}
