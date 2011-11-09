package com.lrepafi.quizgame.controllers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lrepafi.quizgame.entities.*;

public class SettingController {




	public static String PREFERENCES = "QuizGamePreferences"; 
	public static String PREFERENCES_USER_NAME = "UserName"; 
	public static String PREFERENCES_EMAIL = "Email";
	public static String PREFERENCES_SERVER_NAME = "ServerName";
	public static String PREFERENCES_CATEGORY_PREFIX = "Category";



	private Settings settings = new Settings();
	
	public Settings getSettings() {
		return settings;
	}

	public void updateServer(String server) {

		settings.setServerName(server);
		//persist();
	}

	public void persist(SharedPreferences prefs) {
		//TODO
		
		Editor editor = prefs.edit();
		editor.putString(PREFERENCES_USER_NAME, settings.getUsername());
		editor.putString(PREFERENCES_EMAIL, settings.getEmail());
		editor.putString(PREFERENCES_SERVER_NAME, settings.getServerName());
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			editor.putBoolean(PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(), 
					(settings.getPreferences().get(i).getValue()));
			
		}
		
		editor.commit();
		/*
		 * 
		 * SharedPreferences preferences = 
      getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE); 
  EditText text = (EditText) findViewById(R.id.UserNameEditText); 
  Editor editor = preferences.edit(); 
  editor.putString(PREFERENCES_USER_NAME, text.getText().toString()); 
  editor.commit(); 

		 */
	}

	public void load(SharedPreferences prefs) {
		//TODO
		
		settings.setUsername(prefs.getString(PREFERENCES_USER_NAME, ""));
		settings.setEmail(prefs.getString(PREFERENCES_EMAIL, ""));
		settings.setServerName(prefs.getString(PREFERENCES_SERVER_NAME, ""));
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			settings.getPreferences().get(i).setValue(prefs.getBoolean
					(PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(),
							false));

		}		
		
	}

	public String[] getPrefsKeys() {
		/*String[] ret = new String[] {""};

			try {

			Set<String> s = null;
			s = settings.getPreference().keySet();
			ret = ((String[]) s.toArray());
			}
			catch (Exception e) {
				return new String[] {"Error"};
			}
			return ret;*/

		String[] ret = new String[settings.getPreferences().size()];

		for(int i=0;i<ret.length;i++) 
			ret[i]=settings.getPreferences().get(i).getPref();

		return ret;
	}

	public boolean[] getPrefsValues() {

		/*boolean[] vals = new boolean[]{false};

			try {
				Collection<Boolean> c = settings.getPreference().values();
			Boolean[] values = ((Boolean[]) c.toArray());
			vals = new boolean[values.length];
			for(int i=0;i<values.length;i++) {
				vals[i] = values[i].booleanValue();
			}
			}
			catch (Exception e) {
				vals = new boolean[] {false};
			}

			return vals;*/

		boolean[] ret = new boolean[settings.getPreferences().size()];

		for(int i=0;i<ret.length;i++) 
			ret[i]=settings.getPreferences().get(i).getValue();

		return ret;
	}

	public void updatePreference(int index, boolean value) {
		settings.getPreferences().get(index).setValue(value);
	}
}
