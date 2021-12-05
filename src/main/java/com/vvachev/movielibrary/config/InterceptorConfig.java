package com.vvachev.movielibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vvachev.movielibrary.web.interceptor.StatisticInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	private final StatisticInterceptor statisticInterceptor;

	public InterceptorConfig(StatisticInterceptor statisticInterceptor) {
		this.statisticInterceptor = statisticInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(statisticInterceptor);
	}
}
