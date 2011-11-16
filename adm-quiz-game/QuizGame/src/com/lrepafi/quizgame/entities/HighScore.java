package com.lrepafi.quizgame.entities;

public class HighScore implements Comparable<HighScore>{

	String email;
	String username;
	int score;

	public HighScore() {
	}

	public HighScore (String email, String username, int score) {
		this.email = email;
		this.username = username;
		this.score = score;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int compareTo(HighScore o) {

		if (this.getScore() > o.getScore()) {
			return 1;
		}
		else if (this.getScore() < o.getScore()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
