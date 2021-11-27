package com.vvachev.movielibrary.web.events;

import org.springframework.context.ApplicationEvent;

public class Event extends ApplicationEvent {
	private static final long serialVersionUID = 7099057708183571937L;
	private final String username;
	private final String email;
	private final String content;

	public Event(Object source, String username, String email, String content) {
		super(source);
		this.username = username;
		this.email = email;
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getContent() {
		return content;
	}
}
