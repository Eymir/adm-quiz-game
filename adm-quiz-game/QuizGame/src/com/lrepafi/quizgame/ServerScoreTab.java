package com.lrepafi.quizgame;

import com.lrepafi.quizgame.controllers.ScoreController;


public class ServerScoreTab extends ScoreTab {

	@Override
	protected ScoreController getScoreController() {

		return new ScoreController(true);
	}


	/** Called when the activity is first created. */
	/*	@Override
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

	ScoreController sCtrl = new ScoreController(true);

	for (int i=0;i<sCtrl.getScores().size();i++) {
		addScore(table, sCtrl.getScores().get(i), i+1);
	}

	}

	private void addScore(TableLayout table, Score s, int ranking) {

		TableRow row = new TableRow(ServerScoreTab.this);
		TextView tv = new TextView(ServerScoreTab.this);
		tv.setText(s.getUsername());
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(ServerScoreTab.this);
		tv.setText(String.valueOf(s.getScore()));
		tv.setTextSize(20);
		row.addView(tv);
		tv = new TextView(ServerScoreTab.this);
		tv.setText(String.valueOf(ranking));
		tv.setTextSize(20);
		row.addView(tv);
		table.addView(row);

	}*/
}