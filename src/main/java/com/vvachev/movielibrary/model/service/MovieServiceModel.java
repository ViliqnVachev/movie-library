package com.vvachev.movielibrary.model.service;

import java.time.LocalDate;
import java.util.List;

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

	private double raiting;

	private List<CategoryEnum> categories;

	public MovieServiceModel() {
	}

	public double getRaiting() {
		return raiting;
	}

	public void setRaiting(double raiting) {
		this.raiting = raiting;
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

	public List<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEnum> categories) {
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
