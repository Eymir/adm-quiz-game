package com.lrepafi.quizgame.utils;

import java.util.ArrayList;

import com.lrepafi.quizgame.entities.Preference;

public class Helpers {

	public static boolean isPreferredPreference(ArrayList<Preference> p, String s) {
		
		for(int i=0;i<p.size();i++) {
			if (p.get(i).getPref().equals(s)) {
				if (p.get(i).getValue()) return true;
				return false;
			}
		}
		
		return false;
	}
}
