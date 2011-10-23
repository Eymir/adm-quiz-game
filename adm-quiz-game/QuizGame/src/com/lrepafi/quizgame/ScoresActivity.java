package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.TabHost.TabSpec;

public class ScoresActivity extends Activity {
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.scores);
	        
	        
	        TabHost host = (TabHost) findViewById(R.id.tabhost);
	        host.setup();
	        TabSpec spec = host.newTabSpec("TAB1");
	        spec.setIndicator("Label1",
	        getResources().getDrawable(R.drawable.question_mark_icon_48));
	        spec.setContent(R.id.textViewN);
	        host.addTab(spec);
	        spec = host.newTabSpec("TAB2");
	        spec.setIndicator("Label2",
	        getResources().getDrawable(R.drawable.question_mark_icon_48));
	        spec.setContent(R.id.textViewO);
	        host.addTab(spec);
	        host.setCurrentTabByTag("TAB1"); 

	        
	        
	        
	   }
}
