package com.vvachev.movielibrary.model.view;

import java.time.LocalDate;
import java.util.Set;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class MovieViewModel {

	private String title;
	private LocalDate releaseDate;
	private Set<CategoryEnum> categories;
	private String user;
	private double raiting;

	public MovieViewModel() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Set<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEnum> categories) {
		this.categories = categories;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getRaiting() {
		return raiting;
	}

	public void setRaiting(double raiting) {
		this.raiting = raiting;
	}
}
