package com.vvachev.movielibrary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.service.interfaces.IEmailService;
import com.vvachev.movielibrary.utils.EmailConstants;

@Service
public class EmailServiceImpl implements IEmailService {

	private final JavaMailSender emailSender;

	@Autowired
	public EmailServiceImpl(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(EmailConstants.SENDER);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

}