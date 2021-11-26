package com.vvachev.movielibrary.web;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vvachev.movielibrary.model.binding.ChangePasswordBindingModel;
import com.vvachev.movielibrary.model.binding.RegisterUserBinding;
import com.vvachev.movielibrary.model.view.UserViewModel;
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
		return AppConstants.REGISTER_VIEW;
	}

	@PostMapping(AppConstants.UserConfiguration.REGISTER_PATH)
	public String register(@Valid RegisterUserBinding registerUserBinding, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

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

		ApplicationEvent event = new RegistrationCreateEvent(this, registerUserBinding.getUsername(),
				registerUserBinding.getEmail());
		eventPublisher.publishEvent(event);

		return "redirect:login";

	}

	@GetMapping(AppConstants.UserConfiguration.LOGIN_PATH)
	public String login() {
		return AppConstants.LOGIN_VIEW;
	}

	@PostMapping(AppConstants.UserConfiguration.LOGIN_ERROR_PATH)
	public String failedLogin(
			@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("username", username).addFlashAttribute("bad_credentials", true);

		return "redirect:/users/login";
	}

	@GetMapping(AppConstants.UserConfiguration.MYSELF_PATH)
	public String getMyself(@AuthenticationPrincipal User user, Model model) {
		UserViewModel view = mapper.map(userService.getCurrentUser(user.getUsername()), UserViewModel.class);
		model.addAttribute("user", view);
		return AppConstants.MY_PROFILE_VIEW;
	}

	@GetMapping(AppConstants.UserConfiguration.CHANGE_PASSWORD_PATH)
	public String getChangePassword() {
		return AppConstants.CANGE_PASSWORD_VIEW;
	}

	@PostMapping(AppConstants.UserConfiguration.CHANGE_PASSWORD_PATH)
	public String changePassword(ChangePasswordBindingModel changePasswordBindingModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {

		if (bindingResult.hasErrors() || !changePasswordBindingModel.getNewPassword()
				.equals(changePasswordBindingModel.getConfirmPassword())) {
			redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel)
					.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel",
							bindingResult)
					.addAttribute("isNotMatch", true);
			return "redirect:/users/myself/change";
		}

		userService.changePassowrd(changePasswordBindingModel.getNewPassword(), user.getUsername());

		return "redirect:/users/myself";
	}

	@ModelAttribute
	public RegisterUserBinding registerUserBinding() {
		return new RegisterUserBinding();
	}

	@ModelAttribute
	public ChangePasswordBindingModel changePasswordBindingModel() {
		return new ChangePasswordBindingModel();
	}
}
