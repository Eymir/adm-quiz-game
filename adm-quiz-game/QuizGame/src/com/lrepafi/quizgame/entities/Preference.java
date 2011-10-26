package com.lrepafi.quizgame.entities;

public class Preference {

	private String pref;
	private boolean value;
	
	public Preference(String pref, boolean value) {
		this.pref=pref;
		this.value=value;
	}
	
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	
	
}
