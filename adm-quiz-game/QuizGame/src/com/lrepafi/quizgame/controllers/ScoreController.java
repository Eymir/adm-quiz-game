package com.lrepafi.quizgame.controllers;
import java.util.ArrayList;
import com.lrepafi.quizgame.entities.*;

public class ScoreController {

	ArrayList<Score> scores = new ArrayList<Score>();

	//FIXME The code below has to be removed until method load and persist are implemented

	public ScoreController(boolean server) {

		if (!server) {
			scores.add(new Score("netlopa",51000));
			scores.add(new Score("marcinho",43100));
			scores.add(new Score("goez",33500));
			for (int i=0, cscore=33000;i<=20;i++) {
				scores.add(new Score("local"+i,cscore));
				cscore-=1000;
			}

		}
		else {
			scores.add(new Score("netlopa",61000));
			scores.add(new Score("razz",49400));
			scores.add(new Score("billy",44200));
			for (int i=0, cscore=44000;i<=20;i++) {
				scores.add(new Score("remote"+i,cscore));
				cscore-=1000;				
			}
		}

	}

	public ArrayList<Score> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Score> scores) {
		this.scores = scores;
	}

	private void load() {
		//TODO
	}

	private void persist() {
		//TODO
	}

	private void load(String server) {
		//TODO
	}

	public void addScore(String username, int score) {
		//TODO
	}

	public void deleteAll() {
		scores = new ArrayList<Score>();
		persist();
	}



}
