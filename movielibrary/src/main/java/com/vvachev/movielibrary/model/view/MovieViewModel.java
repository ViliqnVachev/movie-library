package com.vvachev.movielibrary.model.view;

import java.util.Set;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class MovieViewModel {

	private Long id;
	private String title;
	private String releaseDate;
	private Set<CategoryEnum> categories;
	private String author;
	private double raiting;
	private String pictureUrl;
	private boolean canDelete;
	private String videoUrl;

	public MovieViewModel() {
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Set<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEnum> categories) {
		this.categories = categories;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getRaiting() {
		return raiting;
	}

	public void setRaiting(double raiting) {
		this.raiting = raiting;
	}
}
