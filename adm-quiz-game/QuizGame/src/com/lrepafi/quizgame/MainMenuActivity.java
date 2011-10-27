package com.lrepafi.quizgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showPlay();
			}


		});

		Button btnScores = (Button) findViewById(R.id.btnScores);
		btnScores.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showScores();
			}


		});


		Button btnCredits = (Button) findViewById(R.id.btnCredits);
		btnCredits.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				showCredits();
			}


		});

	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
		// TODO Auto‐generated method stub 
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.mainmenu, menu); 
		return true; 
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.options_help:
			showHelp();
			return true;
		case R.id.options_settings:
			showSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void showPlay() {

		Intent i = new Intent(this, QuestionActivity.class);
		startActivity(i);

	}

	private void showScores() {

		Intent i = new Intent(this, ScoresActivity.class);
		startActivity(i);

	}

	private void showCredits() {

		Intent i = new Intent(this, CreditsActivity.class);
		startActivity(i);

	}

	private void showHelp() {

		Intent i = new Intent(this, HelpActivity.class);
		startActivity(i);

	}

	private void showSettings() {

		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);

	}
}
