package com.vvachev.movielibrary.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.CategoryServiceModel;
import com.vvachev.movielibrary.repository.CategoryRepository;
import com.vvachev.movielibrary.service.interfaces.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	private final CategoryRepository repo;
	private final ModelMapper mapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepository repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public void initCategories() {
		if (repo.count() == 0) {
			List<CategoryEntity> categories = Arrays.stream(CategoryEnum.values()).map(catEnum -> {
				CategoryServiceModel category = new CategoryServiceModel();
				category.setName(catEnum);

				return convertToEntity(category);
			}).collect(Collectors.toList());
			repo.saveAll(categories);
		}
	}

	private CategoryEntity convertToEntity(CategoryServiceModel model) {
		return mapper.map(model, CategoryEntity.class);
	}

}
