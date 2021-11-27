package com.vvachev.movielibrary.model.view;

public abstract class BaseViewModel {

	private Long id;

	public BaseViewModel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
