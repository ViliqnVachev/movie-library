package com.vvachev.movielibrary.model.entity;

import java.util.Set;

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

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<RoleEntity> roles;

	@ManyToMany(mappedBy = "likes", fetch = FetchType.EAGER)
	private Set<MovieEntity> likedMovies;

	@ManyToMany(mappedBy = "dislikes", fetch = FetchType.EAGER)
	private Set<MovieEntity> dislikedMovies;

	@Column
	private boolean isActive;

	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	private Set<MovieEntity> movies;

	public UserEntity() {
	}

	public Set<MovieEntity> getMovies() {
		return movies;
	}

	public void setMovies(Set<MovieEntity> movies) {
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

	public Set<MovieEntity> getDislikedMovies() {
		return dislikedMovies;
	}

	public void setDislikedMovies(Set<MovieEntity> dislikedMovies) {
		this.dislikedMovies = dislikedMovies;
	}

	public Set<MovieEntity> getLikedMovies() {
		return likedMovies;
	}

	public void setLikedMovies(Set<MovieEntity> likedMovies) {
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

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
}
