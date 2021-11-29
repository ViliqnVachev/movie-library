package com.vvachev.movielibrary.service.interfaces;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import com.vvachev.movielibrary.model.service.UserServiceModel;

public interface IUserService {

	void initUsers() throws RoleNotFoundException;

	boolean isUniqueName(String username);

	UserServiceModel register(UserServiceModel map) throws RoleNotFoundException;

	boolean isUniqueEmail(String email);

	UserServiceModel findByUsername(String username);

	UserServiceModel getCurrentUser(String username);

	void changePassowrd(String newPassword, String username);

	List<UserServiceModel> getAllUsers();

	UserServiceModel disableUser(Long id);

	void enableUsers();

}
