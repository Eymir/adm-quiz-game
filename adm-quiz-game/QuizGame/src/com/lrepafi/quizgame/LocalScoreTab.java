package com.lrepafi.quizgame;

import com.lrepafi.quizgame.controllers.ScoreController;
import com.lrepafi.quizgame.entities.Score;
import com.lrepafi.quizgame.entities.Settings.Category;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Typeface;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.graphics.Color;
import android.content.Context;

public class LocalScoreTab extends ScoreTab {
	/** Called when the activity is first created. */
	
	@Override
	protected ScoreController getScoreController() {

		return new ScoreController(false);
	}
	
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Second Tab Content */
		/*TextView textView = new TextView(this);
textView.setText("Local tab");
setContentView(textView);*
		setContentView(R.layout.tabletest);

		TableLayout table = (TableLayout) findViewById(R.id.tableLayoutScores);
		TableRow row = new TableRow(this);
		TextView tv = new TextView(this);

		//Set headers
		tv.setText("Username");
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		row.addView(tv);
		tv = new TextView(this);
		tv.setText("Score");
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		row.addView(tv);
		tv = new TextView(this);
		tv.setText("Ranking");
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		row.addView(tv);
		table.addView(row);

		/*row = new TableRow(this);
tv = new TextView(this);
tv.setText("Test info 1b");
row.addView(tv);
tv = new TextView(this);
tv.setText("Test info 2b");
row.addView(tv);
tv = new TextView(this);
tv.setText("Test info 3b");
row.addView(tv);
table.addView(row);*

		ScoreController sCtrl = new ScoreController(false);
		
		for (int i=0;i<sCtrl.getScores().size();i++) {
			addScore(LocalScoreTab.this, table, sCtrl.getScores().get(i), i+1);
		}

	}
	
	private void addScore(Context c, TableLayout table, Score s, int ranking) {

		TableRow row = new TableRow(c);
		TextView tv = new TextView(c);
		tv.setText(s.getUsername());
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(c);
		tv.setText(String.valueOf(s.getScore()));
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(c);
		tv.setText(String.valueOf(ranking));
		tv.setTextSize(20);
		row.addView(tv);
		table.addView(row);

	}*/

}