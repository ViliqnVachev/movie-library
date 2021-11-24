package com.vvachev.movielibrary.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vvachev.movielibrary.model.binding.AddMovieBinding;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.MovieServiceModel;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.utils.AppConstants;

@Controller
@RequestMapping(AppConstants.MovieConfiguration.BASE_PATH)
public class MovieController {

	private final IMovieService movieService;
	private final ModelMapper mapper;

	@Autowired
	public MovieController(IMovieService movieService, ModelMapper mapper) {
		this.movieService = movieService;
		this.mapper = mapper;
	}

	@GetMapping(AppConstants.MovieConfiguration.ADD_PATH)
	public String getAddMoive(Model model) {
		model.addAttribute("categories", CategoryEnum.values());
		return AppConstants.MOVIE_ADD_VIEW;
	}

	@PostMapping(AppConstants.MovieConfiguration.ADD_PATH)
	public String addMovie(@Valid AddMovieBinding addMovieBinding, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("addMovieBinding", addMovieBinding)
					.addFlashAttribute("org.springframework.validation.BindingResult.addMovieBinding", bindingResult);
			if (addMovieBinding.getCategories() == null) {
				redirectAttributes.addFlashAttribute("isEmpty", true);
			}
			return "redirect:/movies/add";
		}

		if (!movieService.isUniqueTitle(addMovieBinding.getTitle())) {
			redirectAttributes.addFlashAttribute("addMovieBinding", addMovieBinding)
					.addFlashAttribute("org.springframework.validation.BindingResult.addMovieBinding", bindingResult)
					.addFlashAttribute("isNotUniqueTitle", true);
			return "redirect:/movies/add";
		}

		try {
			movieService.createMovie(mapper.map(addMovieBinding, MovieServiceModel.class), user.getUsername());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/movies/mymovies";
	}

	@GetMapping("/mymovies")
	public String myMovies(@AuthenticationPrincipal User user) {
		List<MovieServiceModel> movies = movieService.getUseresMovies(user.getUsername());
//		List<MovieViewModel> viewModels=movies
		return "my-movies";
	}

	@ModelAttribute
	public AddMovieBinding addMovieBinding() {
		return new AddMovieBinding();
	}

}
