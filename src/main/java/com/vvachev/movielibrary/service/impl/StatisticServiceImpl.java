package com.vvachev.movielibrary.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vvachev.movielibrary.model.entity.StatisticEntity;
import com.vvachev.movielibrary.model.view.StatisticView;
import com.vvachev.movielibrary.repository.StatisticRepository;
import com.vvachev.movielibrary.service.interfaces.IStatisticService;

@Service
public class StatisticServiceImpl implements IStatisticService {

	private final StatisticRepository repository;
	private long authCount;

	@Autowired
	public StatisticServiceImpl(StatisticRepository repository) {
		this.repository = repository;
	}

	@Override
	public void onRequest() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			authCount++;
		}

	}

	@Override
	public StatisticView getStats() {
		StatisticEntity entity = repository.findByDate(LocalDate.now()).orElse(null);

		if (entity == null) {
			entity = new StatisticEntity();
			entity.setAuthCount(authCount);
			entity.setDate(LocalDate.now());
		} else {
			entity.setAuthCount(authCount);
		}
		repository.save(entity);

		return new StatisticView(authCount);
	}

}
