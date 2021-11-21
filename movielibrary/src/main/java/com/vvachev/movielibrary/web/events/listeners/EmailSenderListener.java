package com.vvachev.movielibrary.web.events.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.vvachev.movielibrary.service.interfaces.IEmailService;
import com.vvachev.movielibrary.web.events.RegistrationCreateEvent;

@Component
public class EmailSenderListener {

	private Logger LOGGER = LoggerFactory.getLogger(EmailSenderListener.class);

	private final IEmailService emailService;

	@Autowired
	public EmailSenderListener(IEmailService emailService) {
		this.emailService = emailService;
	}

	@EventListener(RegistrationCreateEvent.class)
	public void onRegistration(RegistrationCreateEvent registrationCreateEvent) {
		emailService.sendSimpleMessage(registrationCreateEvent.user().getEmail(), "test", "test");
		LOGGER.info("Send email!");
	}
}
