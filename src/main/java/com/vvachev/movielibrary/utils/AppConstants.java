package com.vvachev.movielibrary.utils;

public final class AppConstants {

	public static final String INDEX_VIEW = "index";

	public static final String HOME_VIEW = "home";

	public static final String MOVIE_ADD_VIEW = "movie-add";

	public static final String MY_MOVIES_VIEW = "my-movies";

	public static final String AL_MOVIES_VIEW = "all-movies";

	public static final String MOVIE_DETAILS_VIEW = "movie-details";

	public static final String REGISTER_VIEW = "register";

	public static final String LOGIN_VIEW = "login";

	public static final String MY_PROFILE_VIEW = "myprofile";

	public static final String CANGE_PASSWORD_VIEW = "change-password";

	public static final String ALL_USERS_VIEW = "all-users";

	public static final String HOME_PATH = "/home";

	public static final String ABOUT_VIEW = "about";

	public static final String NOT_FOUND_VIEW = "404";

	public static final String STAT_VIEW = "statistic";

	public static class UserConfiguration {

		public static final String ADMIN = "admin";

		public static final String ADMIN_EMAIL = "admin@abv.bg";

		public static final String BASE_PATH = "/users";

		public static final String REGISTER_PATH = "/register";

		public static final String LOGIN_PATH = "/login";

		public static final String LOGIN_ERROR_PATH = "/login-error";

		public static final String MYSELF_PATH = "/myself";

		public static final String CHANGE_PASSWORD_PATH = MYSELF_PATH + "/change";

		public static final String ALL_USERS_PATH = "/all";

		public static final String DISABLE_USER_PATH = "/disable/{id}";

	}

	public static final class AboutConfiguration {
		public static final String BASE_PATH = "/about";
	}

	public static final class MovieConfiguration {

		public static final String BASE_PATH = "/movies";

		public static final String ADD_PATH = "/add";

		public static final String MY_MOVIES_PATH = "/mymovies";

		public static final String ALL_MOVIES_PATH = "/all";

		public static final String DETAILS_PATH = "/details/{id}";

		public static final String LIKE_PATH = "/like/{id}";

		public static final String DISLIKE_PATH = "/dislike/{id}";

		public static final String DELETE_PATH = "/delete/{id}";

		public static final String YOUTUBE_PREFIX = "https://www.youtube.com/embed/";
	}

	public static final class CommentConfiguration {

		public static final String BASE_PATH = "/api/comments";

		public static final String COMMENT_PATH = "/{movieId}";
	}

	public static final class AdminStatistic {

		public static final String BASE_PATH = "/admin";

		public static final String AUTH_STAT_PATH = "/statistic";
	}

}
