package com.lrepafi.quizgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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



//import android.widget.TextView;

import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.RestMethodsHandler;

public class InternetQuestionActivity extends Activity {

	public static String PREFERENCES = "QuizGamePreferences"; 
	public static String PREFERENCES_TIME = "Time"; 
	public static String PREFERENCES_QUESTION_NO = "QuestionNo";
	public static String PREFERENCES_QUESTION_SCORE = "QuestionScore";
	private SharedPreferences preferences = null;
	private ProgressDialog dialog = null;
	private boolean finalizedQuestionExecution=false;

	InternetQuestionController qController = new InternetQuestionController(this);
	//Button btnPlay = (Button) findViewById(R.id.btnPlay);
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
		// TODO Auto-generated method stub
		Editor editor = preferences.edit();
		editor.putInt(PREFERENCES_QUESTION_SCORE, qController.getScore());
		editor.putInt(PREFERENCES_TIME, time);

		if (qController.getQuestionNumber() > 0) editor.putInt(PREFERENCES_QUESTION_NO, qController.getQuestionNumber()-1);
		else editor.putInt(PREFERENCES_QUESTION_NO, 0);

		editor.commit();

		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.question);

		preferences = 
			getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

		time = preferences.getInt(PREFERENCES_TIME, 10*TOTAL_TIME);
		qController.init(preferences);
		
		qController.setQ(preferences.getInt(PREFERENCES_QUESTION_NO, 0));
		qController.setScore(preferences.getInt(PREFERENCES_QUESTION_SCORE, 0));
				
		//Internet operations: i have to start a new game and obtain the nr of questions
		//TODO asynctask
		Log.d("AAAAA",qController.getSettings().getEmail());
		
		Log.e("AAAAA", qController.getSettings().getEmail());
		
		try {
			dialog = ProgressDialog.show(InternetQuestionActivity.this, "", 
                "Loading number of questions. Please wait...", true);
		}
		catch (Exception e) {
			;
		}
		
		NewGameAsyncTask task = new NewGameAsyncTask();
		task.execute();
		this.finalizedQuestionExecution=false;
		//TimeoutAsyncTask task2 = new TimeoutAsyncTask();
		//task2.execute();
		
		
		/*RestMethodsHandler rmh = new RestMethodsHandler(qController.getSettings().getServerName());
		int no = rmh.invokeNewGame(qController.getSettings().getEmail());
		qController.setTotQuestions(no);
		Toast.makeText(this, "Hi!This game has "+no+" questions!", Toast.LENGTH_SHORT).show();
*/
		
		//Binding button/variables
		questionText = (TextView) findViewById(R.id.textViewQuestion);
		questionTextN = (TextView) findViewById(R.id.textViewQuestionN);
		scoreText = (TextView) findViewById(R.id.textViewScore);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		answerBtn[0] = (Button) findViewById(R.id.btnAnswer1);
		answerBtn[1] = (Button) findViewById(R.id.btnAnswer2);
		answerBtn[2] = (Button) findViewById(R.id.btnAnswer3);
		answerBtn[3] = (Button) findViewById(R.id.btnAnswer4);
		
		//Assign initial values
		questionText.setText("");
		questionTextN.setText("");
		scoreText.setText("");
		answerBtn[0].setText("");
		answerBtn[1].setText("");
		answerBtn[2].setText("");
		answerBtn[3].setText("");

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
		//loadQuestion();


	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
		// TODO Auto‐generated method stub 
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.question, menu); 
		return true; 
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int n = qController.getHelp();
		answerBtn[n].setTextColor(Color.GRAY);

		return super.onOptionsItemSelected(item);
	}

	private void loadQuestion() {

		for (int k=0;k<answerBtn.length;k++) answerBtn[k].setTextColor(InternetQuestionActivity.this.getResources().getColor(R.color.questions_items));

		//TODO this instruction must be under asynctask
		if (!(qController.getNextQuestion())) loadQuestion(null);
		else {
			
			dialog = ProgressDialog.show(InternetQuestionActivity.this, "", 
                    "Loading question. Please wait...", true);
			GetQuestionAsyncTask task = new GetQuestionAsyncTask();
			task.execute();
			
		}
	}
	private void loadQuestion(Question q) {

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
					InternetQuestionActivity.this.finish();


				}
			});

			AlertDialog alert = builder.create();
			alert.show();
			
			FinalizeAsyncTask task = new FinalizeAsyncTask();
			task.execute();
		}
		else {

			questionText.setText(q.getQuestionText());
			questionTextN.setText("Question "+qController.getQuestionNumber());

			for(int i=0;i<4;i++) {
				answerBtn[i].setText((q.getAnswers())[i]);
			}

			//time=10*TOTAL_TIME;

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
			//message = "Sorry!Your answer was wrong!";
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
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

	private class NewGameAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			RestMethodsHandler rmh = new RestMethodsHandler(qController.getSettings().getServerName());
			int no = 0;
			
			try {
			no = rmh.invokeNewGame(qController.getSettings().getEmail());
			}
			catch (Exception e) {
				;
			}
			publishProgress(no);


			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub

			try {
				finalizedQuestionExecution=true;
				dialog.hide();
				qController.setTotQuestions(values[0]);
				Toast.makeText(InternetQuestionActivity.this, "Hi!This game has "+values[0]+" questions!", Toast.LENGTH_SHORT).show();

				loadQuestion();
				
							}
			catch (Exception e) {
				Log.d("QUIZGAME", "Timeout screen in AsyncTask: "+e.getMessage());
			}


			return;
		}
	}
	
	private class GetQuestionAsyncTask extends AsyncTask<Void, Question, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			RestMethodsHandler rmh = new RestMethodsHandler(qController.getSettings().getServerName());
			Question q = rmh.invokeGetQuestion(qController.getSettings().getEmail(), qController.getQuestionNumber());
			publishProgress(q);


			return null;
		}
		@Override
		protected void onProgressUpdate(Question... values) {
			// TODO Auto-generated method stub

			try {
				dialog.dismiss();
				qController.addQuestion(values[0]);
				loadQuestion(values[0]);
			}
			catch (Exception e) {
				Log.d("QUIZGAME", "Timeout screen in AsyncTask: "+e.getMessage());
			}


			return;
		}
	}
	
	private class FinalizeAsyncTask extends AsyncTask<String, Integer, Long> {
		@Override
		protected Long doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			int count = params.length;
			if (count<1) return null;
			
			RestMethodsHandler rmh = new RestMethodsHandler(qController.getSettings().getServerName());
			rmh.invokeEndGame(qController.getSettings().getEmail());
			
			return null;
		}

	}



	public FileInputStream getFileInputStream() {

		FileInputStream fin=null;
		try {
			fin = openFileInput("scores.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return fos;
	}

	private class TimeoutAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			publishProgress(1);


			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			if (finalizedQuestionExecution) return;
			
			try {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(InternetQuestionActivity.this);

				//InternetQuestionActivity.this.finish();
				//Toast.makeText(InternetQuestionActivity.this, "Sorry!There was a problem in the internet connection!", Toast.LENGTH_SHORT);

				builder.setMessage("Ops!There was a problem with the internet connection!")
				.setCancelable(false)
				.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						InternetQuestionActivity.this.finish();

					}
				});

				AlertDialog alert = builder.create();
				alert.show();				
				
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
		}
	}
}


