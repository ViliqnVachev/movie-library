package com.vvachev.movielibrary.model.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vvachev.movielibrary.utils.AppConstants;

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
	private LocalDate releaseDate;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity author;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<CategoryEntity> categories;

	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PictureEntity> pictures;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserEntity> likes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "dislikes", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
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
		this.videoUrl = AppConstants.MovieConfiguration.YOUTUBE_PREFIX + videoUrl;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
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
