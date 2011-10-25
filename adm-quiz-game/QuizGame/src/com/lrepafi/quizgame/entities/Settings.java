package com.lrepafi.quizgame.entities;
import java.util.ArrayList;

public class Settings {
	
	private String username;
	private String serverName;
	private String email;
	private ArrayList<Category> preferences;
	
	public Settings() {
		preferences = new ArrayList<Category>();
		username = "";
		serverName="";
		email="";
		
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

	public ArrayList<Category> getPreferences() {
		return preferences;
	}
	
	public void addPreferences(Category cat) {
		preferences.add(cat);
	}

	public void setPreferences(ArrayList<Category> preferences) {
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
