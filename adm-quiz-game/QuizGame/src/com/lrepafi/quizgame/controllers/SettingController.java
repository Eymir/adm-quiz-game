package com.lrepafi.quizgame.controllers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lrepafi.quizgame.entities.*;
import com.lrepafi.quizgame.utils.Globals;

public class SettingController {

	private Settings settings = new Settings();
	
	public Settings getSettings() {
		return settings;
	}

	public void updateServer(String server) {

		settings.setServerName(server);

	}

	public void persist(SharedPreferences prefs) {
		
		
		Editor editor = prefs.edit();
		editor.putString(Globals.PREFERENCES_USER_NAME, settings.getUsername());
		editor.putString(Globals.PREFERENCES_EMAIL, settings.getEmail());
		editor.putString(Globals.PREFERENCES_SERVER_NAME, settings.getServerName());
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			editor.putBoolean(Globals.PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(), 
					(settings.getPreferences().get(i).getValue()));
			
		}
		
		editor.commit();

	}

	public boolean isPreferencesSetted() {
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			if (settings.getPreferences().get(i).getValue()) return true;

		}
		
		return false;
		
	}
	
	public void load(SharedPreferences prefs) {
		
		
		settings.setUsername(prefs.getString(Globals.PREFERENCES_USER_NAME, ""));
		settings.setEmail(prefs.getString(Globals.PREFERENCES_EMAIL, ""));
		settings.setServerName(prefs.getString(Globals.PREFERENCES_SERVER_NAME, ""));
		
		for(int i=0;i<settings.getPreferences().size();i++) {
			
			settings.getPreferences().get(i).setValue(prefs.getBoolean
					(Globals.PREFERENCES_CATEGORY_PREFIX+settings.getPreferences().get(i).getPref(),
							false));

		}		
		
	}

	public String[] getPrefsKeys() {

		String[] ret = new String[settings.getPreferences().size()];

		for(int i=0;i<ret.length;i++) 
			ret[i]=settings.getPreferences().get(i).getPref();

		return ret;
	}

	public boolean[] getPrefsValues() {


		boolean[] ret = new boolean[settings.getPreferences().size()];

		for(int i=0;i<ret.length;i++) 
			ret[i]=settings.getPreferences().get(i).getValue();

		return ret;
	}

	public void updatePreference(int index, boolean value) {
		settings.getPreferences().get(index).setValue(value);
	}
}
