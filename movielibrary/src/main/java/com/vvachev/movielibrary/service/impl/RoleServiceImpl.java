package com.vvachev.movielibrary.service.impl;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.RoleServiceModel;
import com.vvachev.movielibrary.repository.RoleRepository;
import com.vvachev.movielibrary.service.interfaces.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	private final RoleRepository roleRepository;
	private final ModelMapper mapper;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, ModelMapper mapper) {
		this.roleRepository = roleRepository;
		this.mapper = mapper;
	}

	@Override
	public void initRoles() {
		if (roleRepository.count() == 0) {
			RoleServiceModel adminRole = new RoleServiceModel();
			adminRole.setRole(RoleEnum.ADMIN);

			RoleServiceModel userRole = new RoleServiceModel();
			userRole.setRole(RoleEnum.USER);

			roleRepository.saveAll(List.of(convertToEntity(adminRole), convertToEntity(userRole)));
		}
	}

	@Override
	public RoleEntity findByRole(RoleEnum role) throws RoleNotFoundException {
		return roleRepository.findByRole(role).orElseThrow(
				() -> new RoleNotFoundException(String.format("Role with name %s not found!", RoleEnum.USER.name())));
	}

	private RoleEntity convertToEntity(RoleServiceModel model) {
		return mapper.map(model, RoleEntity.class);
	}

}
