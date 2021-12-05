package com.vvachev.movielibrary.model.entity;

import java.time.LocalDate;
import java.util.List;

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

	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity author;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<CategoryEntity> categories;

	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PictureEntity> pictures;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserEntity> likes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "dislikes", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserEntity> dislikes;

	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CommentEntity> comments;

	public MovieEntity() {
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public List<UserEntity> getDislikes() {
		return dislikes;
	}

	public void setDislikes(List<UserEntity> dislikes) {
		this.dislikes = dislikes;
	}

	public List<UserEntity> getLikes() {
		return likes;
	}

	public void setLikes(List<UserEntity> likes) {
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

	public List<CategoryEntity> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}

	public List<PictureEntity> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureEntity> pictures) {
		this.pictures = pictures;
	}
}
