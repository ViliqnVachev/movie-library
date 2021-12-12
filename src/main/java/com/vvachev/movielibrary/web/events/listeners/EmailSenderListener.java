package com.vvachev.movielibrary.web.events.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.vvachev.movielibrary.service.interfaces.IEmailService;
import com.vvachev.movielibrary.utils.EmailConstants;
import com.vvachev.movielibrary.web.events.Event;

@Component
public class EmailSenderListener {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderListener.class);

	private final IEmailService emailService;

	@Autowired
	public EmailSenderListener(IEmailService emailService) {
		this.emailService = emailService;
	}
	// igin/master

	@EventListener(Event.class)
	public void onRegistration(Event registrationCreateEvent) {
		emailService.sendSimpleMessage(registrationCreateEvent.getEmail(), EmailConstants.SUBJECT,
				String.format(registrationCreateEvent.getContent(), registrationCreateEvent.getUsername()));
		LOGGER.info("Sending an email!");
	}
}
