package com.vvachev.movielibrary.web;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvachev.movielibrary.model.view.CommentViewModel;
import com.vvachev.movielibrary.service.interfaces.ICommentService;
import com.vvachev.movielibrary.utils.AppConstants;

@RestController
@RequestMapping(AppConstants.CommentConfiguration.BASE_PATH)
public class CommentController {

	private final ICommentService commentService;
	private final ModelMapper mapper;

	@Autowired
	public CommentController(ICommentService commentService, ModelMapper mapper) {
		this.commentService = commentService;
		this.mapper = mapper;
	}

	@GetMapping("/api/{movieId}")
	public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long movieId, Principal principal) {
		return ResponseEntity.ok(commentService.getComments(movieId));
	}

}
