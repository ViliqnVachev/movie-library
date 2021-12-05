package com.vvachev.movielibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vvachev.movielibrary.web.interceptor.ChangeBackgrountInterceptor;
import com.vvachev.movielibrary.web.interceptor.StatisticInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	private final StatisticInterceptor statisticInterceptor;
	private final ChangeBackgrountInterceptor changeBackgrountInterceptor;

	@Autowired
	public InterceptorConfig(StatisticInterceptor statisticInterceptor,
			ChangeBackgrountInterceptor changeBackgrountInterceptor) {
		this.statisticInterceptor = statisticInterceptor;
		this.changeBackgrountInterceptor = changeBackgrountInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(statisticInterceptor);
		registry.addInterceptor(changeBackgrountInterceptor);
	}
}
