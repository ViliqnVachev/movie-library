package com.vvachev.movielibrary.web.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionHandler implements ErrorController {
	private static final String NOT_FOUND = "Page not found!";

	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {

		String errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();
		int status = Integer.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

		if (status == HttpStatus.NOT_FOUND.value()) {
			model.addAttribute("errorMessage", NOT_FOUND);
		} else {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "error";
	}
}
