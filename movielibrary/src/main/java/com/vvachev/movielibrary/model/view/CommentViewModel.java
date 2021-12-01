package com.vvachev.movielibrary.model.view;

public class CommentViewModel extends BaseViewModel {

	private String commentContent;
	private String author;
	private String created;

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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
