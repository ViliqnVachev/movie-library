package com.vvachev.movielibrary.service.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.CategoryServiceModel;
import com.vvachev.movielibrary.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	private static final String DESCRIPTION = "test description";

	private CategoryEntity categoryEntity;
	private CategoryServiceImpl serviceTest;
	private ModelMapper mapper;

	@Mock
	private CategoryRepository categoryRepository;

	@BeforeEach
	void init() {
		mapper = new ModelMapper();
		serviceTest = new CategoryServiceImpl(categoryRepository, mapper);

		categoryEntity = new CategoryEntity();
		categoryEntity.setDescription(DESCRIPTION);
		categoryEntity.setName(CategoryEnum.ACTION);
	}

	@Test
	void testFindByName() {
		Mockito.when(categoryRepository.findByName(categoryEntity.getName())).thenReturn(Optional.of(categoryEntity));

		CategoryServiceModel model = serviceTest.findByCategoryName(categoryEntity.getName());

		CategoryEnum name = model.getName();
		String description = model.getDescription();

		Assert.assertEquals(name, CategoryEnum.ACTION);
		Assert.assertEquals(description, DESCRIPTION);

	}

	@Test
	void testNotFound() {
		Assert.assertThrows(EntityNotFoundException.class,
				() -> serviceTest.findByCategoryName(CategoryEnum.COMEDY));
	}

}
