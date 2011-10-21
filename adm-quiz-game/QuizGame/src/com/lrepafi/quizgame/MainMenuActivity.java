package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
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
	   
	   private void showPlay() {
		   //TODO
	   }
	   
	   private void showScores() {
		 //TODO
	   }
	   
	   private void showCredits() {
		 //TODO
	   }
}
