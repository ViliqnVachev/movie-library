package com.vvachev.movielibrary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private CategoryEnum name;

	@Column(columnDefinition = "TEXT")
	private String description;

	public CategoryEntity() {
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
