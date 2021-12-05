package com.vvachev.movielibrary.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordBindingModel {

	@NotBlank
	@Size(min = 4, max = 50)
	private String newPassword;

	@NotBlank
	@Size(min = 4, max = 50)
	private String confirmPassword;

	public ChangePasswordBindingModel() {
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
