package com.lrepafi.quizgame.controllers;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.RestMethodsHandler;
import com.lrepafi.quizgame.utils.XMLScoreFactory;

public class ScoreController {

	ArrayList<LocalScore> scores = new ArrayList<LocalScore>();

	public ScoreController() {


	}

	public ArrayList<LocalScore> getScores() {
		return scores;
	}

	public void setScores(ArrayList<LocalScore> scores) {
		this.scores = scores;
	}

	public void load(FileInputStream fin) {

		XMLScoreFactory s = new XMLScoreFactory();
		scores = s.load(fin);
	}

	public void persist(FileOutputStream fos) {

		XMLScoreFactory s = new XMLScoreFactory();
		s.save(fos, scores);

	}

	public void load(String server, String email) {

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
	}



}
