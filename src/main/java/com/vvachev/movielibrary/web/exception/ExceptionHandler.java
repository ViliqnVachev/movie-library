package com.vvachev.movielibrary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {
	private static final String NOT_FOUND = "Page not found!";

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView handleErrors(Exception e, RuntimeException ex) {
		ModelAndView modelAndView = new ModelAndView("error");
		if (!e.getMessage().isEmpty()) {
			modelAndView.addObject("errorMessage", e.getMessage());
		} else {
			modelAndView.addObject("errorMessage", NOT_FOUND);
		}
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		return modelAndView;
	}
}
