package com.vvachev.movielibrary.model.view;

public class StatisticView {
	private long authCount;

	public StatisticView(long authCount) {
		this.authCount = authCount;
	}

	public long getAuthCount() {
		return authCount;
	}

	public void setAuthCount(long authCount) {
		this.authCount = authCount;
	}

}
