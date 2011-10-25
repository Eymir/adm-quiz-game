package com.lrepafi.quizgame;

import com.lrepafi.quizgame.controllers.ScoreController;
import com.lrepafi.quizgame.entities.Score;
import com.lrepafi.quizgame.entities.Settings.Category;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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