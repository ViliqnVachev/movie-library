package com.vvachev.movielibrary.service.impl;

import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.RoleServiceModel;
import com.vvachev.movielibrary.repository.RoleRepository;
import com.vvachev.movielibrary.web.exceptions.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

	@Mock
	private RoleRepository repository;

	private ModelMapper mapper;
	private RoleEntity entity;
	private RoleServiceImpl serviceTest;

	@BeforeEach
	void init() {
		mapper = new ModelMapper();
		serviceTest = new RoleServiceImpl(repository, mapper);

		entity = new RoleEntity();
		entity.setRole(RoleEnum.ADMIN);
	}

	@Test
	void testFindByRole() throws RoleNotFoundException {
		Mockito.when(repository.findByRole(entity.getRole())).thenReturn(Optional.of(entity));

		RoleServiceModel model = serviceTest.findByRole(entity.getRole());

		RoleEnum role = model.getRole();

		Assert.assertEquals(role, RoleEnum.ADMIN);
	}

	@Test
	void testNotFound() throws NotFoundException {
		Assert.assertThrows(NotFoundException.class, () -> serviceTest.findByRole(RoleEnum.USER));
	}

}
