package com.vvachev.movielibrary.service.interfaces;

import java.io.IOException;
import java.util.List;

import com.vvachev.movielibrary.model.entity.MovieEntity;
import com.vvachev.movielibrary.model.service.MovieServiceModel;

public interface IMovieService {
	boolean isUniqueTitle(String title);

	void createMovie(MovieServiceModel movieServiceModel, String username) throws IOException;

	MovieEntity findByTitle(String movieTitle);

	List<MovieServiceModel> getUseresMovies(String username);
}
