package com.lrepafi.quizgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//Bind animation to the 1st question mark
		final ImageView image1 = (ImageView) findViewById(R.id.imageView1); 

		Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.questionmarkanimation); 

		anim1.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation animation) {

				closeSplashScreen();

			}

			public void onAnimationRepeat(Animation animation) {


			}

			public void onAnimationStart(Animation animation) {


			}


		});

		image1.setAnimation(anim1);

		//Bind animation to the 2nd question mark
		final ImageView image2 = (ImageView) findViewById(R.id.imageView2); 
		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.questionmarkanimation); 
		image2.setAnimation(anim2);

		final TextView textview1 = (TextView) findViewById(R.id.textView1);
		Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.anim_credits);
		textview1.setAnimation(anim3);

	}

	private void closeSplashScreen() {

		Intent i = new Intent(this, MainMenuActivity.class);
		startActivity(i);
		SplashScreenActivity.this.finish();

	}


}