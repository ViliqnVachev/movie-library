package com.vvachev.movielibrary.service.interfaces;

import java.io.IOException;
import java.util.List;

import com.vvachev.movielibrary.model.service.MovieServiceModel;
import com.vvachev.movielibrary.model.view.MovieDetailsView;

public interface IMovieService {
	boolean isUniqueTitle(String title);

	void createMovie(MovieServiceModel movieServiceModel, String username) throws IOException;

	MovieServiceModel findByTitle(String movieTitle);

	List<MovieServiceModel> getUseresMovies(String username);

	MovieDetailsView findById(Long id, String name);

	void deleteOffer(Long id);

	void voteMovie(Long id, String username, boolean isLike);

	List<MovieServiceModel> getTopMovies();

	List<MovieServiceModel> getRecentMovies();

}
