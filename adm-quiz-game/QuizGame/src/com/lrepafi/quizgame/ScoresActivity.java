package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.TabHost.TabSpec;
import android.content.Intent;
import android.app.TabActivity;

public class ScoresActivity extends TabActivity {
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.scores);
	        
	        TabHost mTabHost = null;

	        mTabHost = getTabHost();
	        
	        //mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 1",getResources().getDrawable(R.drawable.question_mark_icon_48)).setContent(R.id.tableLayout1));
	        mTabHost.addTab(mTabHost.newTabSpec("TAB1").setIndicator("Local users",getResources().getDrawable(R.drawable.question_mark_icon_48)).setContent(new Intent(this, LocalScoreTab.class)));
	        mTabHost.addTab(mTabHost.newTabSpec("TAB2").setIndicator("User friends",getResources().getDrawable(R.drawable.question_mark_icon_48)).setContent(new Intent(this, ServerScoreTab.class)));
	        
	        mTabHost.setCurrentTab(0);

	        
	       /* TabHost host = (TabHost) findViewById(R.id.tabhost);
	        host.setup();
	        TabSpec spec = host.newTabSpec("TAB1");
	        spec.setIndicator("Label1",
	        getResources().getDrawable(R.drawable.question_mark_icon_48));
	        //spec.setContent(R.id.tex);
	        //spec.setContent(new Intent(this, MainMenuActivity.class));
	        
	        //secondTabSpec.setIndicator("Second Tab Name")
	        
	        host.addTab(spec);
	        spec = host.newTabSpec("TAB2");
	        spec.setIndicator("Label2",
	        getResources().getDrawable(R.drawable.question_mark_icon_48));
	        //spec.setContent(R.id.textViewO);
	        //spec.setContent(new Intent(this, QuestionActivity.class));
	        host.addTab(spec);
	        host.setCurrentTabByTag("TAB1"); */
	        
        
	        /*
	        TableLayout table = (TableLayout) findViewById(R.id.tableLayoutScoreLocal);
	        TableRow row = new TableRow(this);
	        TextView tv = new TextView(this);
	        tv.setText("Test info 1");
	        row.addView(tv);
	        tv = new TextView(this);
	        tv.setText("Test info 2");
	        row.addView(tv);
	        table.addView(row); */

	        
	        
	        
	   }
}
