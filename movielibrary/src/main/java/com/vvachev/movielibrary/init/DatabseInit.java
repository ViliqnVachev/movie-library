package com.vvachev.movielibrary.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vvachev.movielibrary.service.interfaces.ICategoryService;
import com.vvachev.movielibrary.service.interfaces.IRoleService;
import com.vvachev.movielibrary.service.interfaces.IUserService;

@Component
public class DatabseInit implements CommandLineRunner {

	private final IRoleService roleService;
	private final IUserService userService;
	private final ICategoryService categoryService;

	@Autowired
	public DatabseInit(IRoleService roleService, IUserService userService, ICategoryService categoryService) {
		this.roleService = roleService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@Override
	public void run(String... args) throws Exception {
		categoryService.initCategories();
		roleService.initRoles();
		userService.initUsers();
	}

}
