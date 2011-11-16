package com.lrepafi.quizgame;

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
import com.lrepafi.quizgame.utils.Globals;
import com.lrepafi.quizgame.utils.RestMethodsHandler;


public class ServerScoreTab extends ScoreTab {


	private Dialog dialog;
	private boolean finalizedScoresExecution=false;

	private String email;
	private String servername;

	@Override
	protected void loadData(ScoreController s) {

		SharedPreferences preferences = 
			getSharedPreferences(Globals.PREFERENCES, Context.MODE_PRIVATE);

		email = preferences.getString(Globals.PREFERENCES_EMAIL,"");
		servername = preferences.getString(Globals.PREFERENCES_SERVER_NAME, "");

		dialog = ProgressDialog.show(ServerScoreTab.this, "", 
				"Loading scores. Please wait...", true);

		GetScoresAsyncTask task = new GetScoresAsyncTask();
		task.execute();
		TimeoutAsyncTask task2 = new TimeoutAsyncTask();
		task2.execute();
	}

	private class GetScoresAsyncTask extends AsyncTask<Void, ArrayList<LocalScore>, Void> {
		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... params) {

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

			publishProgress(list);			

			return null;
		}
		@Override
		protected void onProgressUpdate(ArrayList<LocalScore>... values) {


			try {
				finalizedScoresExecution=true;
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

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			publishProgress(1);


			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {

			if (finalizedScoresExecution) return;

			try {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(ServerScoreTab.this);

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

				e.printStackTrace();
			}

			return;
		}
	}

}