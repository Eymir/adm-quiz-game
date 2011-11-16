package com.lrepafi.quizgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.lrepafi.quizgame.controllers.ScoreController;
import com.lrepafi.quizgame.entities.HighScore;
import com.lrepafi.quizgame.entities.HighScoreList;
import com.lrepafi.quizgame.entities.LocalScore;
import com.lrepafi.quizgame.entities.Question;
import com.lrepafi.quizgame.entities.Settings;
import com.lrepafi.quizgame.utils.RestMethodsHandler;


public class ServerScoreTab extends ScoreTab {

	public static String PREFERENCES = "QuizGamePreferences"; 
	public static String PREFERENCES_SERVER_NAME = "ServerName";
	public static String PREFERENCES_USER_NAME = "UserName";
	public static String PREFERENCES_EMAIL = "Email";
	
	private Dialog dialog;
	private boolean finalizedQuestionExecution=false;
	
	private Settings settings;
	
	private String email;
	private String servername;
	
	@Override
	protected void loadData(ScoreController s) {
		
		SharedPreferences preferences = 
		      getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		
		email = preferences.getString(this.PREFERENCES_EMAIL,"");
		servername = preferences.getString(this.PREFERENCES_SERVER_NAME, "");
		
		dialog = ProgressDialog.show(ServerScoreTab.this, "", 
                "Loading scores. Please wait...", true);
		
		GetQuestionAsyncTask task = new GetQuestionAsyncTask();
		task.execute();
		TimeoutAsyncTask task2 = new TimeoutAsyncTask();
		task2.execute();
	}
	
	@Override
	protected ScoreController getScoreController() {

		return new ScoreController(true);
	}
	
	private class GetQuestionAsyncTask extends AsyncTask<Void, ArrayList<LocalScore>, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//sCtrl.load(servername, email);
			
			RestMethodsHandler rmh = new RestMethodsHandler(servername);
			HighScoreList hsl = rmh.invokeGetScores(email);
			ArrayList<LocalScore> list = new ArrayList<LocalScore>();
			
			try {
			List<HighScore> hslist = hsl.getScores();
			
			for (int i=0;i<hslist.size();i++) {
				list.add(new LocalScore(hslist.get(i).getUsername(), hslist.get(i).getScore()));			
			}
			} catch (Exception e) {
				;
			}
			
			//Question q = rmh.invokeGetQuestion(qController.getSettings().getEmail(), qController.getQuestionNumber());
			publishProgress(list);			

			return null;
		}
		@Override
		protected void onProgressUpdate(ArrayList<LocalScore>... values) {
			// TODO Auto-generated method stub

			try {
				finalizedQuestionExecution=true;
				sCtrl.setScores(values[0]);
				dialog.dismiss();
				drawTable();
			}
			catch (Exception e) {
				Log.d("QUIZGAME", "Timeout screen in AsyncTask: "+e.getMessage());
			}


			return;
		}
	}
	
	
	private class TimeoutAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(10000);
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
				AlertDialog.Builder builder = new AlertDialog.Builder(ServerScoreTab.this);

				//InternetQuestionActivity.this.finish();
				//Toast.makeText(InternetQuestionActivity.this, "Sorry!There was a problem in the internet connection!", Toast.LENGTH_SHORT);

				builder.setMessage("Ops!There was a problem with the internet connection!")
				.setCancelable(false)
				.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						;

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