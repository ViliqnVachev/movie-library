package com.vvachev.movielibrary.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String fullName;

	@Column
	private int age;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<RoleEntity> roles;

	@ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
	private List<MovieEntity> likedMovies;

	@ManyToMany(mappedBy = "dislikes", fetch = FetchType.LAZY)
	private List<MovieEntity> dislikedMovies;

	@Column
	private boolean isActive;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<MovieEntity> movies;

	public UserEntity() {
	}

	public List<MovieEntity> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieEntity> movies) {
		this.movies = movies;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<MovieEntity> getDislikedMovies() {
		return dislikedMovies;
	}

	public void setDislikedMovies(List<MovieEntity> dislikedMovies) {
		this.dislikedMovies = dislikedMovies;
	}

	public List<MovieEntity> getLikedMovies() {
		return likedMovies;
	}

	public void setLikedMovies(List<MovieEntity> likedMovies) {
		this.likedMovies = likedMovies;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
}
