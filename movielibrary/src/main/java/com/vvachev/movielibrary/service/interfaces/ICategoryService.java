package com.vvachev.movielibrary.service.interfaces;

import com.vvachev.movielibrary.model.entity.CategoryEntity;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public interface ICategoryService {
	void initCategories();

	CategoryEntity findByCategoryName(CategoryEnum name);
}
