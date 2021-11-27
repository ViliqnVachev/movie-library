package com.vvachev.movielibrary.model.view;

import java.util.Set;

public class UserViewModel extends BaseViewModel {

	private String username;
	private int age;
	private String fullName;
	private String email;
	private boolean isActive;
	private Set<String> movies;

	public UserViewModel() {
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public Set<String> getMovies() {
		return movies;
	}

	public void setMovies(Set<String> movies) {
		this.movies = movies;
	}

}
