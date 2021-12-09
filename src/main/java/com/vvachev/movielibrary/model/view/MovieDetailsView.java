package com.vvachev.movielibrary.model.view;

import java.util.List;

public class MovieDetailsView extends BaseViewModel {
	private String videoUrl;
	private String title;
	private String resume;
	private List<String> comments;
	private String raiting;
	private boolean canVote;

	public MovieDetailsView() {
	}

	public boolean isCanVote() {
		return canVote;
	}

	public void setCanVote(boolean canVote) {
		this.canVote = canVote;
	}

	public String getRaiting() {
		return raiting;
	}

	public void setRaiting(String raiting) {
		this.raiting = raiting;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
}
