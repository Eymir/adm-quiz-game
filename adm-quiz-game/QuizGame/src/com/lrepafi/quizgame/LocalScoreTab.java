package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Typeface;
import 	android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.graphics.Color;

public class LocalScoreTab extends Activity {
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

/* Second Tab Content */
/*TextView textView = new TextView(this);
textView.setText("Local tab");
setContentView(textView);*/
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
table.addView(row);*/

try {
addScore(table, "netlopa", 51000, 1);
addScore(table, "marcinho", 43100, 2);
addScore(table, "goez", 33500, 3);
}
catch (Exception e) {
	Log.d("TRIVIAL", e.getMessage());
}

}

private void addScore(TableLayout table, String u, int score, int ranking) {

	TableRow row = new TableRow(LocalScoreTab.this);
	TextView tv = new TextView(LocalScoreTab.this);
	tv.setText(u);
	tv.setTextSize(20);
	row.addView(tv);
	tv = new TextView(LocalScoreTab.this);
	tv.setText(String.valueOf(score));
	tv.setTextSize(20);
	row.addView(tv);
	tv = new TextView(LocalScoreTab.this);
	tv.setText(String.valueOf(ranking));
	tv.setTextSize(20);
	row.addView(tv);
	table.addView(row);
	
}

}