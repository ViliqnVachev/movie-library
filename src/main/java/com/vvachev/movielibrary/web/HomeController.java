package com.vvachev.movielibrary.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
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
		List<MovieViewModel> topMovies = movieService.getTopMovies().stream().map(ser -> {
			MovieViewModel view = mapper.map(ser, MovieViewModel.class);
			view.setRaiting(String.format("%.2f", ser.getRaiting()));
			return view;
		}).collect(Collectors.toList());

		List<MovieViewModel> recentMovies = movieService.getRecentMovies().stream().map(ser -> {
			MovieViewModel view = mapper.map(ser, MovieViewModel.class);
			view.setRaiting(String.format("%.2f", ser.getRaiting()));
			return view;
		}).collect(Collectors.toList());

		model.addAttribute("topMovies", topMovies);
		model.addAttribute("recentMovies", recentMovies);
		if (topMovies.size() > 0) {
			topMovies = topMovies.stream().sorted(
					(a, b) -> Double.compare(Double.parseDouble(a.getRaiting()), Double.parseDouble(b.getRaiting())))
					.collect(Collectors.toList());
			model.addAttribute("mostLikedMovie", topMovies.get(topMovies.size() - 1));
		}
		return AppConstants.INDEX_VIEW;
	}

	@GetMapping(AppConstants.HOME_PATH)
	public String getHome(Model model) {
		List<MovieViewModel> topMovies = movieService.getTopMovies().stream().map(ser -> {
			MovieViewModel view = mapper.map(ser, MovieViewModel.class);
			view.setRaiting(String.format("%.2f", ser.getRaiting()));
			return view;
		}).collect(Collectors.toList());

		List<MovieViewModel> allMovies = movieService.getAllMovies().stream().map(ser -> {
			MovieViewModel view = mapper.map(ser, MovieViewModel.class);
			view.setRaiting(String.format("%.2f", ser.getRaiting()));
			return view;
		}).collect(Collectors.toList());

		Map<String, List<MovieViewModel>> views = new LinkedHashMap<>();
		for (CategoryEnum category : CategoryEnum.values()) {
			List<MovieViewModel> temp = new ArrayList<>();
			for (MovieViewModel movie : allMovies) {
				if (movie.getCategories().contains(category) && temp.size() < 5) {
					temp.add(movie);
				}
			}
			if (temp.size() < 5 && temp.size() != 0) {
				views.put(category.name(), temp);
			}
		}

		model.addAttribute("topMovies", topMovies);
		model.addAttribute("views", views);
		return AppConstants.HOME_VIEW;
	}
}
