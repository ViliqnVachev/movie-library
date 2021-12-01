package com.vvachev.movielibrary.model.binding;

import javax.validation.constraints.Size;

public class CommentBindingModel {
	@Size(min = 10)
	private String commentContent;

	public CommentBindingModel() {
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
