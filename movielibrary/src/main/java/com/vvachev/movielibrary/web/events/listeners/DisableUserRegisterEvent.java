package com.vvachev.movielibrary.web.events.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import com.vvachev.movielibrary.service.interfaces.IEmailService;
import com.vvachev.movielibrary.utils.EmailConstants;
import com.vvachev.movielibrary.web.events.Event;

public class DisableUserRegisterEvent {
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderListener.class);

	private final IEmailService emailService;

	@Autowired
	public DisableUserRegisterEvent(IEmailService emailService) {
		this.emailService = emailService;
	}

	@EventListener(Event.class)
	public void onDisable(Event disableEvent) {
		emailService.sendSimpleMessage(disableEvent.getEmail(), EmailConstants.SUBJECT,
				String.format(disableEvent.getContent(), disableEvent.getUsername()));
		LOGGER.info("Sending an email!");
	}
}
