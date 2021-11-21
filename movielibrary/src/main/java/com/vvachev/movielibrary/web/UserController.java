package com.vvachev.movielibrary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vvachev.movielibrary.service.interfaces.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private final IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login-error")
	public String failedLogin(
			@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("bad_credentials", true);
		attributes.addFlashAttribute("username", userName);

		return "redirect:/users/login";
	}

}
