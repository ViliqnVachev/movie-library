package com.vvachev.movielibrary.model.service;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class CategoryServiceModel extends BaseServiceModel {

	private CategoryEnum name;

	private String description;

	public CategoryServiceModel() {
	}

	public CategoryEnum getName() {
		return name;
	}

	public void setName(CategoryEnum name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
