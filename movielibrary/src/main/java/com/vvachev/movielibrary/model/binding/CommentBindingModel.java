package com.vvachev.movielibrary.model.binding;

import javax.validation.constraints.Size;

public class CommentBindingModel {
	@Size(min = 10)
	private String message;

	public CommentBindingModel() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
