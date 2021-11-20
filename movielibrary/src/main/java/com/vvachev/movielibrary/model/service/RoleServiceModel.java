package com.vvachev.movielibrary.model.service;

import javax.validation.constraints.NotNull;

import com.vvachev.movielibrary.model.entity.enums.RoleEnum;

public class RoleServiceModel extends BaseServiceModel {

	@NotNull
	private RoleEnum role;

	public RoleServiceModel() {
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
}
