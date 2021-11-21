package com.vvachev.movielibrary.service.interfaces;

import com.vvachev.movielibrary.model.service.UserServiceModel;

public interface IUserService {

	void initUsers();

	boolean isUniqueName(String username);

	boolean register(UserServiceModel map);

	boolean isUniqueEmail(String email);

}
