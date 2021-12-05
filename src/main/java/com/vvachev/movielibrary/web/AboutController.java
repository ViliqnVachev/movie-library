package com.vvachev.movielibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvachev.movielibrary.utils.AppConstants;

@Controller
@RequestMapping(AppConstants.AboutConfiguration.BASE_PATH)
public class AboutController {

	@GetMapping
	public String about() {
		return "about";
	}

}
