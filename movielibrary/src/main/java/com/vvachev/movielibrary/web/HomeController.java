package com.vvachev.movielibrary.web;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vvachev.movielibrary.model.view.MovieViewModel;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.utils.AppConstants;

@Controller
public class HomeController {

	private final IMovieService movieService;
	private final ModelMapper mapper;

	@Autowired
	public HomeController(IMovieService movieService, ModelMapper mapper) {
		this.movieService = movieService;
		this.mapper = mapper;
	}

	@GetMapping
	public String getIndex(Model model) {
		List<MovieViewModel> topMovies = movieService.getTopMovies().stream()
				.map(ser -> mapper.map(ser, MovieViewModel.class)).collect(Collectors.toList());

		List<MovieViewModel> recentMovies = movieService.getRecentMovies().stream()
				.map(ser -> mapper.map(ser, MovieViewModel.class)).collect(Collectors.toList());

		model.addAttribute("topMovies", topMovies);
		model.addAttribute("recentMovies", recentMovies);
		model.addAttribute("mostLikedMovie", topMovies.get(0));
		return AppConstants.INDEX_VIEW;
	}

	@GetMapping(AppConstants.HOME_PATH)
	public String getHome(Model model) {
		List<MovieViewModel> topMovies = movieService.getTopMovies().stream()
				.map(ser -> mapper.map(ser, MovieViewModel.class)).collect(Collectors.toList());

		model.addAttribute("topMovies", topMovies);
		return AppConstants.HOME_VIEW;
	}
}
