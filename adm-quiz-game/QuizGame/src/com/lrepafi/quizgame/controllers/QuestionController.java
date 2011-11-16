package com.lrepafi.quizgame.controllers;

import java.io.IOException;
import java.util.*;
import com.lrepafi.quizgame.QuestionActivity;
import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.DataBaseHelper;
import com.lrepafi.quizgame.utils.Globals;
import com.lrepafi.quizgame.utils.Helpers;
import com.lrepafi.quizgame.utils.XMLScoreFactory;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class QuestionController {


	private Settings settings = new Settings();

	public Settings getSettings() {
		return settings;
	}

	private QuestionActivity caller;
	public QuestionController(QuestionActivity caller) {
		this.caller=caller;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setQ(int q) {
		this.q = q;
	}

	private List<Question> list = new ArrayList<Question>();
	int q=0;
	int score=0;

	private void loadSettings(SharedPreferences prefs) {


		settings.setUsername(prefs.getString(Globals.PREFERENCES_USER_NAME, ""));
		settings.setEmail(prefs.getString(Globals.PREFERENCES_EMAIL, ""));
		settings.setServerName(prefs.getString(Globals.PREFERENCES_SERVER_NAME, ""));

		for(int i=0;i<settings.getPreferences().size();i++) {

			settings.getPreferences().get(i).setValue(prefs.getBoolean
					(Globals.PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(),
							false));

		}		

	}

	public void init(SharedPreferences prefs) {
		loadSettings(prefs);
		init();
	}

	private void saveScore() {

		if (this.score == 0) return;

		XMLScoreFactory s = new XMLScoreFactory();
		ArrayList<LocalScore>scores = s.load(caller.getFileInputStream());
		int index=0;
		for(int i=0;i<scores.size();i++) {
			if (scores.get(i).getScore()>=this.score) index=i;
		}

		if (index > Globals.MAX_SCORES) return;

		LocalScore current = new LocalScore(this.settings.getUsername(), this.score);
		scores.add(++index, current);

		try {
			scores.remove(Globals.MAX_SCORES);
		} catch (Exception e) {
			//It doesn't matter
		}

		s.save(caller.getFileOutputStream(), scores);

	}


	public void init() {

		DataBaseHelper myDbHelper = new DataBaseHelper((Context)caller);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();


		}catch(SQLException sqle){

			throw sqle;

		}

		Cursor c = myDbHelper.getCursor();

		int subjectCol = c.getColumnIndex("subject");
		int questionTextCol = c.getColumnIndex("questiontext");
		int answer1Col = c.getColumnIndex("answer1");
		int answer2Col = c.getColumnIndex("answer2");
		int answer3Col = c.getColumnIndex("answer3");
		int answer4Col = c.getColumnIndex("answer4");
		int rightanswerCol = c.getColumnIndex("rightanswer");
		int helpCol = c.getColumnIndex("help");

		while (c.moveToNext()) {

			Question q = new Question();
			q.setSubject(c.getString(subjectCol));

			if (!(Helpers.isPreferredPreference(settings.getPreferences(), q.getSubject()))) continue;

			q.setQuestionText(c.getString(questionTextCol));
			q.setAnswers(new String[]{
					c.getString(answer1Col),
					c.getString(answer2Col),
					c.getString(answer3Col),
					c.getString(answer4Col)
			});
			q.setRightAnswer(c.getInt(rightanswerCol));
			q.setHelp(c.getInt(helpCol));

			list.add(q);

		}

		myDbHelper.close();

	}

	public Question getNextQuestion() {

		if (q < list.size()) return list.get(q++);
		else {
			saveScore();
			return null;
		}

	}

	public boolean submitAnswerAndEvaluate(int i, int time) {

		if (list.get(q-1).getRightAnswer() != (i+1)) return false;

		score = score + (time*100);
		return true;

	}

	public String getCorrectAnswer() {

		String answer="";
		try {
			int ans = list.get(q-1).getRightAnswer();
			answer = (list.get(q-1).getAnswers())[ans-1];
		}
		catch (Exception e) {
			Log.d("QUIZTRIVIAL", e.getMessage());
		}

		return answer;
	}

	public int getHelp() {
		return list.get(q-1).getHelp()-1;
	}

	public int getScore() {
		return score;
	}

	public int getQuestionNumber() {
		return q;
	}



}
