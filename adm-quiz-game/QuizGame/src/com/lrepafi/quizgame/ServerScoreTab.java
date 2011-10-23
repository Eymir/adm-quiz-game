package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Typeface;
import 	android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.graphics.Color;

public class ServerScoreTab extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

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

	try {
	addScore(table, "netlopa", 51000, 1);
	addScore(table, "razz", 49400, 2);
	addScore(table, "billy", 44200, 3);
	}
	catch (Exception e) {
		Log.d("TRIVIAL", e.getMessage());
	}

	}

	private void addScore(TableLayout table, String u, int score, int ranking) {

		TableRow row = new TableRow(ServerScoreTab.this);
		TextView tv = new TextView(ServerScoreTab.this);
		tv.setText(u);
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(ServerScoreTab.this);
		tv.setText(String.valueOf(score));
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(ServerScoreTab.this);
		tv.setText(String.valueOf(ranking));
		tv.setTextSize(20);
		row.addView(tv);
		table.addView(row);
		
	}
}