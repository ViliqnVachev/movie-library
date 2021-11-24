package com.vvachev.movielibrary.model.binding;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class AddMovieBinding {

	@NotBlank
	@Size(min = 1)
	private String title;

	@NotBlank
	@Size(min = 10)
	private String resume;

	@NotNull
	private MultipartFile picture;

	@NotNull
	@Size(min = 11, max = 11)
	private String videoUrl;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;

	@NotNull
	private Set<CategoryEnum> categories;

	public AddMovieBinding() {
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

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
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

	public Set<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEnum> categories) {
		this.categories = categories;
	}
}
