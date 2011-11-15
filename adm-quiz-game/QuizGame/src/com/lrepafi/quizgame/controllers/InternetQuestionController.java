package com.lrepafi.quizgame.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.lrepafi.quizgame.InternetQuestionActivity;
import com.lrepafi.quizgame.QuestionActivity;
import com.lrepafi.quizgame.R;
import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.DataBaseHelper;
import com.lrepafi.quizgame.utils.Globals;
import com.lrepafi.quizgame.utils.Helpers;
import com.lrepafi.quizgame.utils.XMLScoreFactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

public class InternetQuestionController {

	private static final String DB_NAME = "supertrivialgame";
	private static final String DB_PATH = "/data/data/com.lrepafi.quizgame/";
	public static String PREFERENCES = "QuizGamePreferences"; 
	public static String PREFERENCES_USER_NAME = "UserName"; 
	public static String PREFERENCES_EMAIL = "Email";
	public static String PREFERENCES_SERVER_NAME = "ServerName";
	public static String PREFERENCES_CATEGORY_PREFIX = "Category";
	
	private Settings settings = new Settings();
	
	public Settings getSettings() {
		return settings;
	}
	
	private InternetQuestionActivity caller;
	public InternetQuestionController(InternetQuestionActivity caller) {
		this.caller=caller;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public void setQ(int q) {
		this.q = q;
	}

	private List<Question> list = new ArrayList<Question>();
	int q=0;
	int score=0;

	private void loadSettings(SharedPreferences prefs) {
		
		
		settings.setUsername(prefs.getString(PREFERENCES_USER_NAME, ""));
		settings.setEmail(prefs.getString(PREFERENCES_EMAIL, ""));
		settings.setServerName(prefs.getString(PREFERENCES_SERVER_NAME, ""));
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			settings.getPreferences().get(i).setValue(prefs.getBoolean
					(PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(),
							false));

		}		
		
	}
	
	public void init(SharedPreferences prefs) {
		loadSettings(prefs);
		init();
	}
	
	private void saveScore() {
		
		if (this.score == 0) return;
		
		XMLScoreFactory s = new XMLScoreFactory();
		ArrayList<LocalScore>scores = s.load(caller.getFileInputStream());
		int index=0;
		for(int i=0;i<scores.size();i++) {
			if (scores.get(i).getScore()>=this.score) index=i;
		}
		
		if (index > Globals.MAX_SCORES) return;
		
		LocalScore current = new LocalScore(this.settings.getUsername(), this.score);
		scores.add(++index, current);
		
		try {
			scores.remove(Globals.MAX_SCORES);
		} catch (Exception e) {
			//It doesn't matter
		}
		
		s.save(caller.getFileOutputStream(), scores);
		
	}
		
	  private void CopyDatabase() throws Exception{
		  try{
		  //File f1 = new File(srcFile); 
		  
		  File f2 = new File("/data/data/com.lrepafi.quizgame/databases/supertrivialgame.db");
		  //InputStream in = new FileInputStream(f1);
		  InputStream in = caller.getResources().openRawResource(R.raw.supertrivialgame); 
		  //For Append the file.
		//  OutputStream out = new FileOutputStream(f2,true);

		  //For Overwrite the file.
		  OutputStream out = new FileOutputStream(f2);

		  byte[] buf = new byte[1024];
		  int len;
		  while ((len = in.read(buf)) > 0){
		  out.write(buf, 0, len);
		  }
		  in.close();
		  out.close();
		  
		  }
		  catch(FileNotFoundException ex){
			  Toast.makeText(caller, "Filenotfound", Toast.LENGTH_SHORT);
			  throw new Exception();
		  }
		  catch(IOException e){
			  Toast.makeText(caller, "IO", Toast.LENGTH_SHORT);
		  }
		  }
	
	/*private void CopyDataBase() throws IOException{

	    //Open your local db as the input stream
	    InputStream myInput = caller.getAssets().open(DB_NAME);

	    // Path to the just created empty db
	    String outFileName = DB_PATH + DB_NAME;

	    //Open the empty db as the output stream
	    OutputStream myOutput = new FileOutputStream(outFileName);

	    //transfer bytes from the inputfile to the outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = myInput.read(buffer))>0){
	        myOutput.write(buffer, 0, length);
	    }

	    //Close the streams
	    myOutput.flush();
	    myOutput.close();
	    myInput.close();

	}*/

	
	private void checkAndCreateDatabaseIfNotExists() {
		
		/*SQLiteDatabase db;
		
		try {

			db = SQLiteDatabase.openDatabase("supertrivialgame",
			null,
			SQLiteDatabase.CREATE_IF_NECESSARY);

			db.close();

			}

			catch (SQLiteException e) {

				
				try {
					CopyDatabase();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Toast.makeText(caller, "Database copied", Toast.LENGTH_SHORT);
				
			}*/
		
		 DataBaseHelper myDbHelper = new DataBaseHelper(caller);
	 
	        try {
	 
	        	myDbHelper.createDataBase();
	 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		myDbHelper.openDataBase();
	 		
	 
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}

		
		
	}
	
	
	public void init() {
		//TODO add sql binding for retrieve question

		//checkAndCreateDatabaseIfNotExists();
		/*SQLiteDatabase db=null;
		
		try {

			//db = SQLiteDatabase.openDatabase("supertrivialgame",
			//null,
			//SQLiteDatabase.CREATE_IF_NECESSARY);
			
			db = SQLiteDatabase.openOrCreateDatabase("supertrivialgame", null);
			
		}

		catch (SQLiteException e) {

			Toast.makeText(caller, "Exception", Toast.LENGTH_SHORT);
			
		}*/
		
		 DataBaseHelper myDbHelper = new DataBaseHelper((Context)caller);
		 
	        try {
	 
	        	myDbHelper.createDataBase();
	 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		myDbHelper.openDataBase();
	 		
	 
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
	 	
	 	//SQLiteDatabase db = myDbHelper.getReadableDatabase();

	 	
			//Cursor c = db.rawQuery("select * from questions",null);
	 	    Cursor c = myDbHelper.getCursor();
	 	
			int subjectCol = c.getColumnIndex("subject");
			int questionTextCol = c.getColumnIndex("questiontext");
			int answer1Col = c.getColumnIndex("answer1");
			int answer2Col = c.getColumnIndex("answer2");
			int answer3Col = c.getColumnIndex("answer3");
			int answer4Col = c.getColumnIndex("answer4");
			int rightanswerCol = c.getColumnIndex("rightanswer");
			int helpCol = c.getColumnIndex("help");

			while (c.moveToNext()) {

				Question q = new Question();
				q.setSubject(c.getString(subjectCol));
				
				if (!(Helpers.isPreferredPreference(settings.getPreferences(), q.getSubject()))) continue;
				
				q.setQuestionText(c.getString(questionTextCol));
				q.setAnswers(new String[]{
						c.getString(answer1Col),
						c.getString(answer2Col),
						c.getString(answer3Col),
						c.getString(answer4Col)
				});
				q.setRightAnswer(c.getInt(rightanswerCol));
				q.setHelp(c.getInt(helpCol));
				
				list.add(q);

			}
			
			myDbHelper.close();
			//db.close();

	}
	
	public void addQuestion(Question q) {
		list.add(q);
	}

	private int tot;
	public void setTotQuestions(int tot) {
		this.tot = tot;
	}
	
	public boolean getNextQuestion() {

		/*if (q < list.size()) return list.get(q++);
		else {
			saveScore();
			return null;
		}*/
		
		if (q< tot) {
			q++;
			return true;
		}
		else {
			saveScore();
			return false;
		}


	}

	public boolean submitAnswerAndEvaluate(int i, int time) {

		if (list.get(q-1).getRightAnswer() != (i+1)) return false;

		score = score + (time*100);
		return true;

	}

	public String getCorrectAnswer() {

		String answer="";
		try {
			int ans = list.get(q-1).getRightAnswer();
			answer = (list.get(q-1).getAnswers())[ans-1];
		}
		catch (Exception e) {
			Log.d("QUIZTRIVIAL", e.getMessage());
		}

		return answer;
	}

	public int getHelp() {
		return list.get(q-1).getHelp()-1;
	}

	public int getScore() {
		return score;
	}

	public int getQuestionNumber() {
		return q;
	}



}
