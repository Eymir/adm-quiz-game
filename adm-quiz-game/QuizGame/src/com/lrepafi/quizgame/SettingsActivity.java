package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        //FIXME Relink the proper layout instead of R.layout.mainmenu once i have .xml layout file
	        setContentView(R.layout.mainmenu);
	   }
}
