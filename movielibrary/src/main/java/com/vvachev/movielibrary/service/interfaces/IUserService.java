package com.vvachev.movielibrary.service.interfaces;

import javax.management.relation.RoleNotFoundException;

import com.vvachev.movielibrary.model.entity.UserEntity;
import com.vvachev.movielibrary.model.service.UserServiceModel;

public interface IUserService {

	void initUsers() throws RoleNotFoundException;

	boolean isUniqueName(String username);

	UserServiceModel register(UserServiceModel map) throws RoleNotFoundException;

	boolean isUniqueEmail(String email);

	UserEntity findByUsername(String username);

}
