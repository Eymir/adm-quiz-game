package com.lrepafi.quizgame.controllers;

import com.lrepafi.quizgame.entities.*;

public class SettingController {




	Settings settings = new Settings();

	public void updateServer(String server) {

		settings.setServerName(server);
		persist();
	}

	void persist() {
		//TODO
	}

	void load() {
		//TODO
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
