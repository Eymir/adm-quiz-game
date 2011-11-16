package com.lrepafi.quizgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lrepafi.quizgame.controllers.ScoreController;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class LocalScoreTab extends ScoreTab {
	/** Called when the activity is first created. */

	@Override
	protected void loadData(ScoreController s) {
		
		FileInputStream fin=null;
		try {
			fin = openFileInput("scores.xml");
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		} 
		
		s.load(fin);
		
		drawTable();
	}
	
	@Override
	protected ScoreController getScoreController() {

		//return new ScoreController(false);
		return new ScoreController();
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
		
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.scores, menu); 
		return true; 
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		table.removeAllViews();
		sCtrl.deleteAll();
		drawTable();


		return super.onOptionsItemSelected(item);
	}

}