package com.vvachev.movielibrary.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "statistics")
public class StatisticEntity extends BaseEntity {

	@Column
	private LocalDate date;

	@Column
	private long authCount;

	public StatisticEntity() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getAuthCount() {
		return authCount;
	}

	public void setAuthCount(long authCount) {
		this.authCount = authCount;
	}

}
