package com.vvachev.movielibrary.web.events;

import org.springframework.context.ApplicationEvent;

import com.vvachev.movielibrary.model.service.UserServiceModel;

public class RegistrationCreateEvent extends ApplicationEvent {
	private static final long serialVersionUID = 7099057708183571937L;
	private final UserServiceModel user;

	public RegistrationCreateEvent(Object source, UserServiceModel user) {
		super(source);
		this.user = user;
	}

	public UserServiceModel user() {
		return user;
	}

//	private final JavaMailSender mailSender;


}
