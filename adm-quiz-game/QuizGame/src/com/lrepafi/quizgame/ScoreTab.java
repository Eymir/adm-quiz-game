package com.lrepafi.quizgame;

import com.lrepafi.quizgame.controllers.ScoreController;
import com.lrepafi.quizgame.entities.LocalScore;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Typeface;

import android.graphics.Color;
import android.content.Context;

public class ScoreTab extends Activity {
	/** Called when the activity is first created. */
	ScoreController sCtrl;
	TableLayout table;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tablescore);

		sCtrl = new ScoreController();
		loadData(sCtrl);


	}

	protected void loadData(ScoreController s) {

	}

	protected void drawTable() {

		table = (TableLayout) findViewById(R.id.tableLayoutScores);
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
		tv.setPadding(10, 0, 10, 0);
		row.addView(tv);
		tv = new TextView(this);
		tv.setText("Ranking");
		tv.setTypeface(null, Typeface.BOLD);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		row.addView(tv);
		table.addView(row);

		for (int i=0;i<sCtrl.getScores().size();i++) {
			addScore(ScoreTab.this, table, sCtrl.getScores().get(i), i+1);
		}


	}

	protected void addScore(Context c, TableLayout table, LocalScore s, int ranking) {

		TableRow row = new TableRow(c);
		TextView tv = new TextView(c);
		tv.setText(s.getUsername());
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		row.addView(tv);
		tv = new TextView(c);
		tv.setText(String.valueOf(s.getScore()));
		tv.setTextSize(20);
		tv.setPadding(10, 0, 10, 0);
		tv.setTextColor(Color.BLACK);
		row.addView(tv);
		tv = new TextView(c);
		tv.setText(String.valueOf(ranking));
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		row.addView(tv);
		table.addView(row);

	}

}
