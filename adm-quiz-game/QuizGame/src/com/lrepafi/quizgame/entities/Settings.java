package com.lrepafi.quizgame.entities;
import java.util.ArrayList;
import java.util.*;

public class Settings {
	
	private String username;
	private String serverName;
	private String email;
	private ArrayList<Preference> preferences;
	
	public Settings() {
		preferences = new ArrayList<Preference>();
		username = "";
		serverName="";
		email="";
				
		preferences.add(new Preference("Sports", false));
		preferences.add(new Preference("Literature", false));
		preferences.add(new Preference("Science", false));
		preferences.add(new Preference("Movies", false));
		preferences.add(new Preference("History", false));
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Preference> getPreferences() {
		return preferences;
	}
	
	public void addPreferences(Preference cat) {
		preferences.add(cat);
	}

	public void setPreferences(ArrayList<Preference> preferences) {
		this.preferences = preferences;
	}

	public enum Category {
		SPORTS ("Sports"),
		LITERATURE ("Literature"),
		SCIENCE ("Science"),
		HISTORY ("History"),
		MOVIES ("Movies");
		
		private final String s;
		
		Category(String s) {
			this.s = s;
		}
		
		public String toString() {
			return this.s;
		}
		
	}
	
}
