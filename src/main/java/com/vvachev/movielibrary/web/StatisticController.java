package com.vvachev.movielibrary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vvachev.movielibrary.service.interfaces.IStatisticService;
import com.vvachev.movielibrary.utils.AppConstants;

@Controller
@RequestMapping(AppConstants.AdminStatistic.BASE_PATH)
public class StatisticController {

	private final IStatisticService statServer;

	@Autowired
	public StatisticController(IStatisticService statServer) {
		this.statServer = statServer;
	}

	@GetMapping(AppConstants.AdminStatistic.AUTH_STAT_PATH)
	public ModelAndView getStats() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("stats", statServer.getStats());
		modelAndView.setViewName("statistic");
		return modelAndView;
	}

}
