package com.vvachev.movielibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.vvachev.movielibrary.utils.AppConstants;

@Controller
public class HomeController {

	@GetMapping
	public String index() {
		return AppConstants.INDEX_VIEW;
	}

	@GetMapping(AppConstants.HOME_PATH)
	public String home() {
		return AppConstants.HOME_VIEW;
	}
}
