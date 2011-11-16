package com.lrepafi.quizgame;

import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.graphics.Color;
import android.app.TabActivity;

public class ScoresActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scores);

		TabHost mTabHost = null;

		mTabHost = getTabHost();

		mTabHost.addTab(mTabHost.newTabSpec("TAB1").setIndicator("Local users",getResources().getDrawable(R.drawable.local_users)).setContent(new Intent(this, LocalScoreTab.class)));
		mTabHost.addTab(mTabHost.newTabSpec("TAB2").setIndicator("User friends",getResources().getDrawable(R.drawable.remote_users)).setContent(new Intent(this, ServerScoreTab.class)));

		mTabHost.setCurrentTab(0);

		mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#111177"));
		mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#111177"));

	}
}
