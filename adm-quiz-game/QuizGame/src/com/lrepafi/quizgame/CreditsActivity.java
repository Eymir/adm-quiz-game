package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class CreditsActivity extends Activity {
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        //FIXME Relink the proper layout instead of R.layout.mainmenu once i have .xml layout file
	        setContentView(R.layout.credits);
	        
	        final TextView txt1 = (TextView) findViewById(R.id.textView1);
	        final TextView txt2 = (TextView) findViewById(R.id.textView2);
	        final TextView txt3 = (TextView) findViewById(R.id.textView3);
	        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_credits); 
	        txt1.setAnimation(anim);
	        txt2.setAnimation(anim);
	        txt3.setAnimation(anim);

	   }
	   
}
