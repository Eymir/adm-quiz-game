package com.lrepafi.quizgame.controllers;

import java.util.*;
import com.lrepafi.quizgame.entities.*;

import android.content.SharedPreferences;
import android.util.Log;

public class QuestionController {

	public void setScore(int score) {
		this.score = score;
	}

	public void setQ(int q) {
		this.q = q;
	}

	private List<Question> list = new ArrayList<Question>();
	int q=0;
	int score=0;

	public void init(SharedPreferences prefs) {
		
	}
	
	public void init() {
		// TODO Auto-generated method stub
		//List<Question> list = new ArrayList<Question>();

		Question question = new Question();
		question.setSubject("Literature");
		question.setQuestionText("Who wrote the Dunwich Horror?");
		question.setAnswers(new String[]{
				"August Derleth",
				"Howard Phillips Lovecraft",
				"Edgard Allan Poe",
		"Clark Ashton Smith"}
		);
		question.setRightAnswer(2);
		question.setHelp(3);
		list.add(question);

		question = new Question();
		question.setSubject("Sports");
		question.setQuestionText("Whos is the current 100 meters men's world record holder?");
		question.setAnswers(new String[]{
				"Usain Bolt",
				"Tyson Gay",
				"Asafa Powell",
		"Nesta Carter"}
		);
		question.setRightAnswer(1);
		question.setHelp(4);
		list.add(question);

		question = new Question();
		question.setSubject("History");
		question.setQuestionText("The name of which city was changed to Petrograd and Leningrad?");
		question.setAnswers(new String[]{
				"Moscow",
				"Tashkent",
				"Kiev",
		"St. Petersburg"}
		);
		question.setRightAnswer(4);
		question.setHelp(1);
		list.add(question);

		question = new Question();
		question.setSubject("Science");
		question.setQuestionText("What is the meaning of elephant in Latin?");
		question.setAnswers(new String[]{
				"Long pole",
				"Huge arch",
				"Palm tree",
		"Mountain"}
		);
		question.setRightAnswer(2);
		question.setHelp(1);
		list.add(question);

		question = new Question();
		question.setSubject("Movies");
		question.setQuestionText("For which film did Audrey Hepburn get Academy Award for Best Actress?");
		question.setAnswers(new String[]{
				"Roman Holiday",
				"Wait Until Dark",
				"Sabrina",
		"Funny Face"}
		);
		question.setRightAnswer(1);
		question.setHelp(2); //2-1-4-2-1
		list.add(question);

	}

	public Question getNextQuestion() {

		if (q < list.size()) return list.get(q++);
		else return null;

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
