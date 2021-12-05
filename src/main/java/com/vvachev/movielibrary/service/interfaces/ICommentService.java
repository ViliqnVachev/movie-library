package com.vvachev.movielibrary.service.interfaces;

import java.util.List;

import com.vvachev.movielibrary.model.service.CommentServiceModel;
import com.vvachev.movielibrary.model.view.CommentViewModel;

public interface ICommentService {

	List<CommentViewModel> getComments(Long movieId);

	CommentViewModel addComment(CommentServiceModel commentServiceModel);

}
