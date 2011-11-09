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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		s.load(fin);
	}
	
	@Override
	protected ScoreController getScoreController() {

		/*FileOutputStream fos=null;
		try {
			fos = openFileOutput("scores.xml",  
			        Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		FileInputStream fin=null;
		try {
			fin = openFileInput("scores.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */


		return new ScoreController(false);
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
		// TODO Auto‐generated method stub 
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.scores, menu); 
		return true; 
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//TODO create code for deletion
		table.removeAllViews();
		sCtrl.deleteAll();
		drawTable();


		return super.onOptionsItemSelected(item);
	}

}