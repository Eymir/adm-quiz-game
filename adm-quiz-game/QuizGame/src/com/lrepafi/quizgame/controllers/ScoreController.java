package com.lrepafi.quizgame.controllers;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.RestMethodsHandler;
import com.lrepafi.quizgame.utils.XMLScoreFactory;

public class ScoreController {

	ArrayList<LocalScore> scores = new ArrayList<LocalScore>();

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
			scores.add(new LocalScore("netlopa",61000));
			scores.add(new LocalScore("razz",49400));
			scores.add(new LocalScore("billy",44200));
			for (int i=0, cscore=44000;i<=20;i++) {
				scores.add(new LocalScore("remote"+i,cscore));
				cscore-=1000;				
			}
		}
		
		//FileOutputStream fos = openFileOutput("contacts.xml",  
          //      Context.MODE_PRIVATE);

		//XMLScoreFactory s = new XMLScoreFactory();
		//s.save(fos, scores);

	}

	public ArrayList<LocalScore> getScores() {
		return scores;
	}

	public void setScores(ArrayList<LocalScore> scores) {
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

	public void load(String server, String email) {
		//TODO
		RestMethodsHandler rmh = new RestMethodsHandler(server);
		HighScoreList hsl = rmh.invokeGetScores(email);
				
		List<HighScore> hslist = hsl.getScores();
		if (hslist == null) return;
		
		ArrayList<LocalScore> list = new ArrayList<LocalScore>();
		
		for (int i=0;i<hslist.size();i++) {
			list.add(new LocalScore(hslist.get(i).getUsername(), hslist.get(i).getScore()));			
		}
				
		scores = list;
		
	}
	
	public void deleteAll() {
		scores = new ArrayList<LocalScore>();
		//persist();
	}



}
