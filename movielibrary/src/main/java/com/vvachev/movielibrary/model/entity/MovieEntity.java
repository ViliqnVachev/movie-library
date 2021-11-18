package com.vvachev.movielibrary.model.entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieEntity extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String resume;

	@Column(nullable = false)
	private String videoUrl;

	@Column(nullable = false)
	private Instant releaseDate;

	@ManyToOne
	private UserEntity author;

	@ManyToMany
	private Set<CategoryEntity> categories;

	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
	private Set<PictureEntity> pictures;

	@ManyToMany(mappedBy = "likedMovies")
	private Set<UserEntity> likes;

	@ManyToMany(mappedBy = "dislikedMovies")
	private Set<UserEntity> dislikes;

	public MovieEntity() {
	}

	public Set<UserEntity> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<UserEntity> dislikes) {
		this.dislikes = dislikes;
	}

	public Set<UserEntity> getLikes() {
		return likes;
	}

	public void setLikes(Set<UserEntity> likes) {
		this.likes = likes;
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

	public Instant getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Instant releaseDate) {
		this.releaseDate = releaseDate;
	}

	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
	}

	public Set<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryEntity> categories) {
		this.categories = categories;
	}

	public Set<PictureEntity> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureEntity> pictures) {
		this.pictures = pictures;
	}
}
