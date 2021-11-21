package com.vvachev.movielibrary.service.interfaces;

public interface IEmailService {
	void sendSimpleMessage(String to, String subject, String text);
}
