package com.vvachev.movielibrary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {
	public static final String PAGE_NOT_FOUND = "Page not found!";

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView handleAnyError(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		if (!e.getMessage().isEmpty()) {
			modelAndView.addObject("errorMessage", e.getMessage());
		} else {
			modelAndView.addObject("errorMessage", PAGE_NOT_FOUND);
		}
		modelAndView.setViewName("page.html");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		return modelAndView;
	}
}
