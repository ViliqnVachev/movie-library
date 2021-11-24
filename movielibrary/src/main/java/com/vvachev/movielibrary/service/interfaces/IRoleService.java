package com.vvachev.movielibrary.service.interfaces;

import javax.management.relation.RoleNotFoundException;

import com.vvachev.movielibrary.model.entity.RoleEntity;
import com.vvachev.movielibrary.model.entity.enums.RoleEnum;

public interface IRoleService {
	void initRoles();

	RoleEntity findByRole(RoleEnum role) throws RoleNotFoundException;
}
