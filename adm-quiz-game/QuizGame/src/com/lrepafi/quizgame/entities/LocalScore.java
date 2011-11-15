package com.lrepafi.quizgame.entities;

public class LocalScore {

	private String username;
	private int score;

	public LocalScore(String username, int score) {
		this.username=username;
		this.score=score;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

}
