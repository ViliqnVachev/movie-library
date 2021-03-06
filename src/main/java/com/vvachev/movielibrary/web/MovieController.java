package com.vvachev.movielibrary.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vvachev.movielibrary.model.binding.AddMovieBinding;
import com.vvachev.movielibrary.model.entity.enums.CategoryEnum;
import com.vvachev.movielibrary.model.service.MovieServiceModel;
import com.vvachev.movielibrary.model.view.MovieDetailsView;
import com.vvachev.movielibrary.model.view.MovieViewModel;
import com.vvachev.movielibrary.service.interfaces.IMovieService;
import com.vvachev.movielibrary.utils.AppConstants;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) throws IOException {

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

		movieService.createMovie(mapper.map(addMovieBinding, MovieServiceModel.class), user.getUsername());

		return "redirect:/movies/mymovies";
	}

	@GetMapping(AppConstants.MovieConfiguration.MY_MOVIES_PATH)
	public String myMovies(Model model, @AuthenticationPrincipal User user) {
		List<MovieServiceModel> movies = movieService.getUseresMovies(user.getUsername());
		List<MovieViewModel> viewModels = movies.stream().map(serviceModel -> {
			MovieViewModel view = mapper.map(serviceModel, MovieViewModel.class);
			view.setReleaseDate(serviceModel.getReleaseDate().toString());
			view.setCanDelete(serviceModel.getAuthor().equals(user.getUsername()));
			view.setRaiting(String.format("%.2f", serviceModel.getRaiting()));
			return view;
		}).collect(Collectors.toList());
		model.addAttribute("movies", viewModels);
		return AppConstants.MY_MOVIES_VIEW;
	}

	@GetMapping(AppConstants.MovieConfiguration.DETAILS_PATH)
	public String details(@PathVariable Long id, Model model, Principal principal) {
		MovieDetailsView view = movieService.findById(id, principal.getName());
		model.addAttribute("movie", view);
		return AppConstants.MOVIE_DETAILS_VIEW;
	}

	@PreAuthorize("@movieServiceImpl.canDelete(#principal.name, #id)")
	@DeleteMapping(AppConstants.MovieConfiguration.DELETE_PATH)
	public String deleteMovie(@PathVariable Long id, Principal principal) {

		movieService.deleteMovie(id);
		return "redirect:/movies/mymovies";
	}

	@PreAuthorize("@movieServiceImpl.canVote(#principal.name, #id)")
	@PostMapping(AppConstants.MovieConfiguration.LIKE_PATH)
	public String likeMovie(@PathVariable Long id, Principal principal) {

		movieService.voteMovie(id, principal.getName(), true);

		return "redirect:/movies/details/" + id;
	}

	@PreAuthorize("@movieServiceImpl.canVote(#principal.name, #id)")
	@PostMapping(AppConstants.MovieConfiguration.DISLIKE_PATH)
	public String dislikeMovie(@PathVariable Long id, Principal principal) {

		movieService.voteMovie(id, principal.getName(), false);

		return "redirect:/movies/details/" + id;
	}

	@GetMapping(AppConstants.MovieConfiguration.ALL_MOVIES_PATH)
	public String allMovies(Model model) {
		List<MovieServiceModel> movies = movieService.getAllMovies();
		List<MovieViewModel> viewModels = movies.stream().map(serviceModel -> {
			MovieViewModel view = mapper.map(serviceModel, MovieViewModel.class);
			view.setReleaseDate(serviceModel.getReleaseDate().toString());
			view.setCanDelete(true);
			view.setRaiting(String.format("%.2f", serviceModel.getRaiting()));
			return view;
		}).collect(Collectors.toList());
		model.addAttribute("movies", viewModels);
		return AppConstants.AL_MOVIES_VIEW;
	}

	@ModelAttribute
	public AddMovieBinding addMovieBinding() {
		return new AddMovieBinding();
	}

}
