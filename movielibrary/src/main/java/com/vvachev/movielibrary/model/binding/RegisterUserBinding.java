package com.vvachev.movielibrary.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RegisterUserBinding {

	@NotNull
	@Size(min = 4, max = 50)
	private String username;

	@NotNull
	@Size(min = 4, max = 50)
	private String fullName;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Positive
	private int age;

	@NotNull
	@Size(min = 4, max = 50)
	private String password;

	@NotNull
	@Size(min = 4, max = 50)
	private String confirmPassword;

	public RegisterUserBinding() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
