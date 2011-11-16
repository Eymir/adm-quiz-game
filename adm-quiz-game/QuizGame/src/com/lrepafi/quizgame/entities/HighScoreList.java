package com.lrepafi.quizgame.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HighScoreList {

	@SerializedName("highScore")
	private List<HighScore> scores;

	public List<HighScore> getScores() {
		return scores;
	}

	public void setScores(List<HighScore> scores) {
		this.scores = scores;
	}

}