package com.vvachev.movielibrary.web;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.vvachev.movielibrary.model.binding.CommentBindingModel;
import com.vvachev.movielibrary.model.service.CommentServiceModel;
import com.vvachev.movielibrary.model.view.CommentViewModel;
import com.vvachev.movielibrary.service.interfaces.ICommentService;
import com.vvachev.movielibrary.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "comments", description = "REST API for Comments", tags = { "comments" })
@RequestMapping(AppConstants.CommentConfiguration.BASE_PATH)
public class CommentController {

	private final ICommentService commentService;
	private final ModelMapper mapper;

	@Autowired
	public CommentController(ICommentService commentService, ModelMapper mapper) {
		this.commentService = commentService;
		this.mapper = mapper;
	}

	@GetMapping(AppConstants.CommentConfiguration.COMMENT_PATH)
	@ApiOperation(value = "Find all Comments", notes = "Find all comments ", response = CommentViewModel.class)
	public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long movieId, Principal principal) {
		return ResponseEntity.ok(commentService.getComments(movieId));
	}

	@PostMapping(AppConstants.CommentConfiguration.COMMENT_PATH)
	@ApiOperation(value = "Create a comment", notes = "Create a comment", response = CommentViewModel.class)
	public ResponseEntity<CommentViewModel> addComment(@AuthenticationPrincipal UserDetails principal,
			@PathVariable Long movieId, @RequestBody @Valid CommentBindingModel commentBindingModel,
			UriComponentsBuilder builder) {

		CommentServiceModel commentServiceModel = mapper.map(commentBindingModel, CommentServiceModel.class);
		commentServiceModel.setAuthor(principal.getUsername());
		commentServiceModel.setMovieId(movieId);

		CommentViewModel commentViewModel = commentService.addComment(commentServiceModel);
		URI uri = builder.path(AppConstants.CommentConfiguration.BASE_PATH + "/moviecomment/{commentId}")
				.buildAndExpand(commentViewModel.getId())
				.toUri();
		return ResponseEntity.created(uri).body(commentViewModel);
	}

}
