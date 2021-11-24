package com.vvachev.movielibrary.web;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vvachev.movielibrary.model.binding.RegisterUserBinding;
import com.vvachev.movielibrary.model.service.UserServiceModel;
import com.vvachev.movielibrary.service.interfaces.IUserService;
import com.vvachev.movielibrary.utils.AppConstants;
import com.vvachev.movielibrary.web.events.RegistrationCreateEvent;

@Controller
@RequestMapping(AppConstants.UserConfiguration.BASE_PATH)
public class UserController {

	private final IUserService userService;
	private final ModelMapper mapper;
	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	public UserController(IUserService userService, ModelMapper mapper, ApplicationEventPublisher eventPublisher) {
		this.userService = userService;
		this.mapper = mapper;
		this.eventPublisher = eventPublisher;
	}

	@GetMapping(AppConstants.UserConfiguration.REGISTER_PATH)
	public String getRegister() {
		return AppConstants.UserConfiguration.REGISTER;
	}

	@PostMapping(AppConstants.UserConfiguration.REGISTER_PATH)
	public String register(@Valid RegisterUserBinding registerUserBinding, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (bindingResult.hasErrors()
				|| !registerUserBinding.getPassword().equals(registerUserBinding.getConfirmPassword())) {
			redirectAttributes.addFlashAttribute("registerUserBinding", registerUserBinding).addFlashAttribute(
					"org.springframework.validation.BindingResult.registerUserBinding", bindingResult);
			return "redirect:/users/register";
		}

		boolean isUniqueName = userService.isUniqueName(registerUserBinding.getUsername());
		boolean isUniequeEmail = userService.isUniqueEmail(registerUserBinding.getEmail());

		if (!isUniqueName || !isUniequeEmail) {
			redirectAttributes.addFlashAttribute("registerUserBinding", registerUserBinding).addFlashAttribute(
					"org.springframework.validation.BindingResult.registerUserBinding", bindingResult);
			if (!isUniqueName) {
				redirectAttributes.addFlashAttribute("isNotUniqueUserName", true);
			}
			if (!isUniequeEmail) {
				redirectAttributes.addFlashAttribute("isNotUniqueEmail", true);
			}
			return "redirect:/users/register";
		}

		try {
			UserServiceModel registeredUser = userService
					.register(mapper.map(registerUserBinding, UserServiceModel.class));
			ApplicationEvent event = new RegistrationCreateEvent(this, registeredUser);
			eventPublisher.publishEvent(event);
		} catch (RoleNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:login";

	}

	@GetMapping(AppConstants.UserConfiguration.LOGIN_PATH)
	public String login() {
		return AppConstants.UserConfiguration.LOGIN;
	}

	@PostMapping(AppConstants.UserConfiguration.LOGIN_ERROR_PATH)
	public String failedLogin(
			@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("bad_credentials", true);

		return "redirect:/users/login";
	}

	@ModelAttribute
	public RegisterUserBinding registerUserBinding() {
		return new RegisterUserBinding();
	}
}
