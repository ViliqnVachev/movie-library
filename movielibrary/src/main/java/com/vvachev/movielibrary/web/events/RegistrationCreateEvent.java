package com.vvachev.movielibrary.web.events;

import org.springframework.context.ApplicationEvent;

public class RegistrationCreateEvent extends ApplicationEvent {
	private static final long serialVersionUID = 7099057708183571937L;
	private final String username;
	private final String email;

	public RegistrationCreateEvent(Object source, String username, String email) {
		super(source);
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}
