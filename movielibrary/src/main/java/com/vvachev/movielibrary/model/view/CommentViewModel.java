package com.vvachev.movielibrary.model.view;

import java.time.LocalDate;

public class CommentViewModel extends BaseViewModel {

	private String commentContent;
	private String author;
	private LocalDate created;

	public CommentViewModel() {
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

}
