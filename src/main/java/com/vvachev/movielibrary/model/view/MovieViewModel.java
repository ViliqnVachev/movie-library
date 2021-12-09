package com.vvachev.movielibrary.model.view;

import java.util.List;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;

public class MovieViewModel extends BaseViewModel {

	private String title;
	private String releaseDate;
	private List<CategoryEnum> categories;
	private String author;
	private String raiting;
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

	public List<CategoryEnum> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEnum> categories) {
		this.categories = categories;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRaiting() {
		return raiting;
	}

	public void setRaiting(String raiting) {
		this.raiting = raiting;
	}
}
