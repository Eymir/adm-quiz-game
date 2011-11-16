package com.lrepafi.quizgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.os.AsyncTask;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.Globals;

public class QuestionActivity extends Activity {


	private SharedPreferences preferences = null;


	QuestionController qController = new QuestionController(this);

	Button[] answerBtn = new Button[4];
	TextView questionText = null;
	TextView questionTextN = null;
	TextView scoreText = null;
	ProgressBar progress = null;
	private final int TOTAL_TIME=10;
	int time;
	TimeAsyncTask task = null;
	boolean stop=false;

	@Override
	protected void onPause() {

		Editor editor = preferences.edit();
		editor.putInt(Globals.PREFERENCES_QUESTION_SCORE, qController.getScore());
		editor.putInt(Globals.PREFERENCES_TIME, time);

		if (qController.getQuestionNumber() > 0) editor.putInt(Globals.PREFERENCES_QUESTION_NO, qController.getQuestionNumber()-1);
		else editor.putInt(Globals.PREFERENCES_QUESTION_NO, 0);


		editor.commit();

		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.question);

		preferences = 
			getSharedPreferences(Globals.PREFERENCES, Context.MODE_PRIVATE);

		time = preferences.getInt(Globals.PREFERENCES_TIME, 10*TOTAL_TIME);
		qController.setQ(preferences.getInt(Globals.PREFERENCES_QUESTION_NO, 0));
		qController.setScore(preferences.getInt(Globals.PREFERENCES_QUESTION_SCORE, 0));
		qController.init(preferences);

		//Binding button/variables
		questionText = (TextView) findViewById(R.id.textViewQuestion);
		questionTextN = (TextView) findViewById(R.id.textViewQuestionN);
		scoreText = (TextView) findViewById(R.id.textViewScore);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		answerBtn[0] = (Button) findViewById(R.id.btnAnswer1);
		answerBtn[1] = (Button) findViewById(R.id.btnAnswer2);
		answerBtn[2] = (Button) findViewById(R.id.btnAnswer3);
		answerBtn[3] = (Button) findViewById(R.id.btnAnswer4);

		//Adding onclick listeners to every button

		answerBtn[0].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				answer(0);
			}


		});


		answerBtn[1].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				answer(1);
			}


		});

		answerBtn[2].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				answer(2);
			}


		});

		answerBtn[3].setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				answer(3);
			}


		});

		//Update score on text
		updateScore();
		//Load first question
		loadQuestion();


	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 

		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.question, menu); 
		return true; 
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int n = qController.getHelp();
		answerBtn[n].setTextColor(Color.GRAY);

		return super.onOptionsItemSelected(item);
	}

	private void loadQuestion() {

		for (int k=0;k<answerBtn.length;k++) answerBtn[k].setTextColor(QuestionActivity.this.getResources().getColor(R.color.questions_items));

		Question q = qController.getNextQuestion();

		if (q == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			String outMsg;
			if (qController.getQuestionNumber()==0) outMsg = "I'm sorry, I don't have questions for you!Maybe you haven't setted your preferences";
			else outMsg = "We finished this game, your score is "+qController.getScore()+"!";


			builder.setMessage(outMsg)
			.setCancelable(false)
			.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					time=10*TOTAL_TIME;
					qController.setQ(0);
					qController.setScore(0);
					QuestionActivity.this.finish();


				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		}
		else {

			questionText.setText(q.getQuestionText());
			questionTextN.setText("Question "+qController.getQuestionNumber());

			for(int i=0;i<4;i++) {
				answerBtn[i].setText((q.getAnswers())[i]);
			}

			progress.setMax(10*TOTAL_TIME);
			progress.setProgress(time);


			task = new TimeAsyncTask();
			task.execute();

		}

	}

	private void updateScore() {
		scoreText.setText("Score: " + qController.getScore());
	}

	private void answer(int i) {

		String message="";
		String correctAns="";
		boolean ok=false;

		try {
			stop=true;
			ok = qController.submitAnswerAndEvaluate(i, time);
			updateScore();
			correctAns = qController.getCorrectAnswer();

		}
		catch (Exception e) {

			Log.d("TRIVIAL",e.getMessage());
		}		   


		if (ok) {
			message = "Yeah!You got the answer!";
		}
		else {
			message = "Sorry!The correct answer was "+correctAns;

		}




		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				time=10*TOTAL_TIME;
				loadQuestion();
				dialog.cancel();


			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	private void showEndTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Sorry, your time has expired!")
		.setCancelable(false)
		.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				time= 10*TOTAL_TIME;
				loadQuestion();
				dialog.cancel();


			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private class TimeAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {

			stop=false;
			while ((time>=0) && (!stop)) {
				try {

					publishProgress(time);
					Thread.sleep(100);
					time=time-1;

				} catch (InterruptedException e) {
					Log.d("Debug", "Error while managing the thread");
				}
			}

			if(stop) return null;


			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {

			progress.setProgress(time);

			if(time<=0) {
				try {
					showEndTime();
				}
				catch (Exception e) {
					Log.d("QUIZGAME", "Timeout screen in AsyncTask: "+e.getMessage());
				}
			}

			return;
		}
	}

	public FileInputStream getFileInputStream() {

		FileInputStream fin=null;
		try {
			fin = openFileInput("scores.xml");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return fin;

	}

	public FileOutputStream getFileOutputStream() {
		FileOutputStream fos=null;
		try {
			fos = openFileOutput("scores.xml",  
					Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} 

		return fos;
	}

}


