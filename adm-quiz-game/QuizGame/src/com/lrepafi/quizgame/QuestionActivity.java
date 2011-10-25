package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.AsyncTask;
import android.util.Log;
import android.app.Dialog.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;



//import android.widget.TextView;

import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.entities.*;

public class QuestionActivity extends Activity {
	
	QuestionController qController = new QuestionController();
	//Button btnPlay = (Button) findViewById(R.id.btnPlay);
	Button[] answerBtn = new Button[4];
	TextView questionText = null;
	TextView questionTextN = null;
	TextView scoreText = null;
	ProgressBar progress = null;
	private final int TOTAL_TIME=70;
	int time;
	myAsyncTask task = null;
	boolean stop=false;
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.question);
	        
	        qController.init();
	        
	        //Binding button/variables
	        questionText = (TextView) findViewById(R.id.textViewQuestion);
	        questionTextN = (TextView) findViewById(R.id.textViewQuestionN);
	        scoreText = (TextView) findViewById(R.id.textViewScore);
	        progress = (ProgressBar) findViewById(R.id.progressBar1);
	        answerBtn[0] = (Button) findViewById(R.id.btnAnswer1);
	        answerBtn[1] = (Button) findViewById(R.id.btnAnswer2);
	        answerBtn[2] = (Button) findViewById(R.id.btnAnswer3);
	        answerBtn[3] = (Button) findViewById(R.id.btnAnswer4);
	        
	        //Adding onclick listeners to every button
	        	
	        answerBtn[0].setOnClickListener(new OnClickListener() {

	        	public void onClick(View v) {
				
	        		answer(0);
	        	}
	        	
	        	
	        });
	        
	        
	        answerBtn[1].setOnClickListener(new OnClickListener() {

	        	public void onClick(View v) {
				
	        		answer(1);
	        	}
	        	
	        	
	        });
	        
	        answerBtn[2].setOnClickListener(new OnClickListener() {

	        	public void onClick(View v) {
				
	        		answer(2);
	        	}
	        	
	        	
	        });
	        
	        answerBtn[3].setOnClickListener(new OnClickListener() {

	        	public void onClick(View v) {
				
	        		answer(3);
	        	}
	        	
	        	
	        });
	        	        
	        //Load first question
	        loadQuestion();
	        	
	        
	   }
	   
	   @Override 
	   public boolean onCreateOptionsMenu(Menu menu) { 
	     // TODO Auto‐generated method stub 
	     MenuInflater inflater = getMenuInflater(); 
	     inflater.inflate(R.menu.question, menu); 
	     return true; 
	   }

	   
	   
	   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		   int n = qController.getHelp();
		   answerBtn[n].setTextColor(Color.GRAY);
		   
		return super.onOptionsItemSelected(item);
	}

	private void loadQuestion() {
		
		for (int k=0;k<answerBtn.length;k++) answerBtn[k].setTextColor(Color.WHITE);
		
		   Question q = qController.getNextQuestion();
		   
		   if (q == null) {
			   AlertDialog.Builder builder = new AlertDialog.Builder(this);
			   builder.setMessage("We finished this game, your score is "+qController.getScore()+"!")
			          .setCancelable(false)
			          .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			              public void onClick(DialogInterface dialog, int id) {
			                   
			            	  QuestionActivity.this.finish();
			            	  
			            	  
			              }
			          });

			   AlertDialog alert = builder.create();
			   alert.show();
		   }
		   else {
			   
			   questionText.setText(q.getQuestionText());
			   questionTextN.setText("Question "+qController.getQuestionNumber());
			   			   
			   for(int i=0;i<4;i++) {
				   answerBtn[i].setText((q.getAnswers())[i]);
			   }

			   time=10*TOTAL_TIME;
			   
			   progress.setMax(time);
			   progress.setProgress(time);

			   
			   task = new myAsyncTask();
			   task.execute();
			   			   
		   }
	   
	   }
	   
	   private void updateScore() {
		   scoreText.setText("Score: " + qController.getScore());
	   }
	   
	   private void answer(int i) {
		   
		   String message="";
		   String correctAns="";
		   boolean ok=false;
		   
		   try {
		   stop=true;
		   ok = qController.submitAnswerAndEvaluate(i, time);
		   updateScore();
		   correctAns = qController.getCorrectAnswer();

		   }
		   catch (Exception e) {
			   
			   Log.d("TRIVIAL",e.getMessage());
		   }		   
		   
		   
		   if (ok) {
			   message = "Yeah!You got the answer!";
		   }
		   else {
			   message = "Sorry!The correct answer was "+correctAns;
			   //message = "Sorry!Your answer was wrong!";
		   }
		   

		   
		   
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		   builder.setMessage(message)
		          .setCancelable(false)
		          .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog, int id) {
		                   
		            	  loadQuestion();
		            	  dialog.cancel();
		            	  
		            	  
		              }
		          });

		   AlertDialog alert = builder.create();
		   alert.show();
		   
	   }
	   
	   private void showEndTime() {
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		   builder.setMessage("Sorry, your time has expired!")
		          .setCancelable(false)
		          .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog, int id) {
		                   
		            	  loadQuestion();
		            	  dialog.cancel();
		            	  
		            	  
		              }
		          });

		   AlertDialog alert = builder.create();
		   alert.show();
	   }
	   
	   private class myAsyncTask extends AsyncTask<Void, Integer, Void> {
		   @Override
		   protected Void doInBackground(Void... params) {
		   // TODO Auto-generated method stub
			   stop=false;
			   while ((time>=0) && (!stop)) {
				   try {
					   
					   publishProgress(time);
					   Thread.sleep(100);
					   time=time-1;

				   } catch (InterruptedException e) {
					   Log.d("Debug", "Error while managing the thread");
				   }
			   }
			   
			   if(stop) return null;
			   
			  			   
			   return null;
		   }
		   @Override
		   protected void onProgressUpdate(Integer... values) {
		   // TODO Auto-generated method stub
			   progress.setProgress(time);
			   
			   if(time<=0) showEndTime();

			   return;
		   		}
		   }


}


