package com.lrepafi.quizgame.controllers;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.content.Context;

import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.XMLScoreFactory;

public class ScoreController {

	ArrayList<Score> scores = new ArrayList<Score>();

	//FIXME The code below has to be removed until method load and persist are implemented

	public ScoreController(boolean server) {

		//TODO implement loading of scores
		//use asynctask for manage the parsing
		
		/*
		 * ScoresScreen.this.setProgressBarIndeterminate(true);
ScoresScreen.this.setProgressBarIndeterminateVisibility(true); 

		 */
		
		//Local
		if (!server) {

			/*scores.add(new Score("netlopa",51000));
			scores.add(new Score("marcinho",43100));
			scores.add(new Score("goez",33500));
			for (int i=0, cscore=33000;i<=20;i++) {
				scores.add(new Score("local"+i,cscore));
				cscore-=1000;
			}*/

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
		
		//FileOutputStream fos = openFileOutput("contacts.xml",  
          //      Context.MODE_PRIVATE);

		//XMLScoreFactory s = new XMLScoreFactory();
		//s.save(fos, scores);

	}

	public ArrayList<Score> getScores() {
		return scores;
	}

	public void setScores(ArrayList<Score> scores) {
		this.scores = scores;
	}

	public void load(FileInputStream fin) {
		//TODO
		XMLScoreFactory s = new XMLScoreFactory();
		scores = s.load(fin);
	}

	public void persist(FileOutputStream fos) {
		//TODO
		XMLScoreFactory s = new XMLScoreFactory();
		s.save(fos, scores);
		
	}

	private void load(String server) {
		//TODO
	}

	public void addScore(String username, int score) {
		//TODO
	}

	public void deleteAll() {
		scores = new ArrayList<Score>();
		//persist();
	}



}
