package com.vvachev.movielibrary.service.interfaces;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.CategoryServiceModel;

public interface ICategoryService {
	void initCategories();

	CategoryServiceModel findByCategoryName(CategoryEnum name);
}
