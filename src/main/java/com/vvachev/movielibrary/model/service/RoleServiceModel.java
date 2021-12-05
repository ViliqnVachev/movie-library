package com.vvachev.movielibrary.model.service;

import com.vvachev.movielibrary.model.entity.enums.RoleEnum;

public class RoleServiceModel extends BaseServiceModel {

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
