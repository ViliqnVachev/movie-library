package com.vvachev.movielibrary.model.view;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseViewModel {
	@ApiModelProperty(hidden = true)
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
