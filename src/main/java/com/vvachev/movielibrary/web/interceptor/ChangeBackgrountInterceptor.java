package com.vvachev.movielibrary.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vvachev.movielibrary.utils.AppConstants;

@Component
public class ChangeBackgrountInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		if (url.equals(AppConstants.AboutConfiguration.BASE_PATH)) {
			response.setHeader("change", "true");
		}
		return true;
	}

}
