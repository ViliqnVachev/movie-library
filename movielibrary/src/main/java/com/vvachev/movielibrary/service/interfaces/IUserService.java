package com.vvachev.movielibrary.service.interfaces;

import javax.management.relation.RoleNotFoundException;

import com.vvachev.movielibrary.model.service.UserServiceModel;

public interface IUserService {

	void initUsers();

	boolean isUniqueName(String username);

	UserServiceModel register(UserServiceModel map) throws RoleNotFoundException;

	boolean isUniqueEmail(String email);

}
