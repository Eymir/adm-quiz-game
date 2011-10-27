package com.lrepafi.quizgame;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


public class HelpActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.instructions);

		final TextView txtInstructions = (TextView) findViewById(R.id.textViewInstructions);
		txtInstructions.setText(instructions());
		txtInstructions.setTextColor(Color.BLACK);

	}

	//TODO this method will be deprecated when we implement raw storage
	public String instructions() {
		String res = new String();
		res = "Welcome to the Quiz Game!\n";
		res = res.concat("You will have a pool of 5 questions to answer: for each question you have 10 seconds for answer\n");
		res = res.concat("And if you are unsure between 2 answer, you can press Menu->Help (in the game screen) and you will know what question is surely wrong\n");
		res = res.concat("Also, are you good in questions about Science, Movie and Sports but do you don't know anything about History and Literature?Good, you can set your preferences ");
		res = res.concat("clicking on Menu->Settings in the dashboard!\n");
		res = res.concat("Enjoy the quiz game!!!");

		return res;
	}

}
