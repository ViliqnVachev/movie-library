package com.vvachev.movielibrary.service.interfaces;

import com.vvachev.movielibrary.model.entity.enums.RoleEnum;
import com.vvachev.movielibrary.model.service.RoleServiceModel;

public interface IRoleService {
	void initRoles();

	RoleServiceModel findByRole(RoleEnum role);
}
