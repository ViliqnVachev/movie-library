package com.vvachev.movielibrary.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vvachev.movielibrary.service.interfaces.IStatisticService;
import com.vvachev.movielibrary.utils.AppConstants;

@Component
public class StatisticInterceptor implements HandlerInterceptor {
	private final IStatisticService statsService;

	public StatisticInterceptor(IStatisticService statsService) {
		this.statsService = statsService;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String path = request.getRequestURI();
		if (path.equals(AppConstants.HOME_PATH)) {
			statsService.onRequest();
		}
	}
}
