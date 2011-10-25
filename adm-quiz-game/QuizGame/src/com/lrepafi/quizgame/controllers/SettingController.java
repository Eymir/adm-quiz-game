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
}
