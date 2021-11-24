package com.vvachev.movielibrary.model.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class MovieServiceModel extends BaseServiceModel {

	private String title;

	private String resume;

	private String videoUrl;

	private LocalDate releaseDate;

	private MultipartFile picture;

	private String pictureUrl;

	private String author;

	private Set<CategoryEnum> categories;

	public MovieServiceModel() {
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Set<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEnum> categories) {
		this.categories = categories;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
}
