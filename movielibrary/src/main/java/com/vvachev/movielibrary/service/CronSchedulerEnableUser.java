package com.vvachev.movielibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vvachev.movielibrary.service.interfaces.IUserService;

@Component
public class CronSchedulerEnableUser {
	private static final Logger LOGGER = LoggerFactory.getLogger(CronSchedulerEnableUser.class);

	private final IUserService userServiceModel;

	@Autowired
	public CronSchedulerEnableUser(IUserService userServiceModel) {
		this.userServiceModel = userServiceModel;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void enableUsers() {
		LOGGER.info("Enable users");
		userServiceModel.enableUsers();
	}
}
