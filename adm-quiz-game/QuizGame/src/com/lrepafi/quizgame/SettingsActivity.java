package com.lrepafi.quizgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;

import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.utils.Globals;
import com.lrepafi.quizgame.utils.RestMethodsHandler;

import android.view.View.OnClickListener;

public class SettingsActivity extends Activity {

	private SharedPreferences preferences = null;
	
	@Override
	protected void onPause() {

		settingCtrl.persist(preferences);
		super.onPause();
	}

	Spinner server;
	SettingController settingCtrl = new SettingController();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
		
		preferences = 
		      getSharedPreferences(Globals.PREFERENCES, Context.MODE_PRIVATE);
		
		settingCtrl.load(preferences);
		
		final EditText editT = (EditText) findViewById(R.id.editTextEmail);
		
		editT.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	                	
	        	settingCtrl.getSettings().setEmail(editT.getText().toString());
	            
	        }

	    });
		editT.setText(settingCtrl.getSettings().getEmail());
		
		final EditText editA = (EditText) findViewById(R.id.editTextUsername);
		
		editA.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            settingCtrl.getSettings().setUsername(editA.getText().toString());
	            
	        }

	    });
		editA.setText(settingCtrl.getSettings().getUsername());

		
		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerServer);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.server_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);
	
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
		
				settingCtrl.updateServer( (String)parent.getItemAtPosition(pos));
				super.onItemSelected(parent, view, pos, id);
			}

		});
		
		int index = -1;
		//begin search item
		try {
			int k=0;
			String value;
			
			while(true) {
				value = (String)spinner.getItemAtPosition(k);
				if (value.equals(settingCtrl.getSettings().getServerName())) {
					index=k;
					break;
				}
				k++;
			}

			
		} 
		catch (Exception e) {
			;
		}
		
		if (index!=-1) spinner.setSelection(index);
		
		//end search item
	

		Button btnPrefs = (Button) findViewById(R.id.buttonSelectPreferences);
		btnPrefs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				final CharSequence[] items = settingCtrl.getPrefsKeys();

				final boolean[] values= settingCtrl.getPrefsValues();

				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setTitle("Question preferences");

				builder.setMultiChoiceItems(items, values, new DialogInterface.OnMultiChoiceClickListener() {

					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						
						settingCtrl.updatePreference(which, isChecked);
						updatePreferenceText();
					
					}
				});
				AlertDialog alert = builder.create();
				alert.show();


			}
		});
		
		final EditText editEmailAddr = (EditText) findViewById(R.id.editTextFriendEmail);
		Button btnAddFriend = (Button) findViewById(R.id.buttonAddFriend);
		btnAddFriend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Toast.makeText(SettingsActivity.this, "Sending invitation", Toast.LENGTH_SHORT).show();
				sendFriendInvitation(settingCtrl.getSettings().getEmail(),editEmailAddr.getText().toString());
				editEmailAddr.setText("");

			}
		});
		
		
		if (settingCtrl.isPreferencesSetted()) this.updatePreferenceText();
		
	}

	private void updatePreferenceText() {

		TextView text = (TextView) findViewById(R.id.textViewPreferences);
		text.setText("Question preferenced setted");
	}


	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {

			settingCtrl.updateServer(parent.getItemAtPosition(pos).toString());

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			

		}

	}
	
	public void sendFriendInvitation(String email, String guest) {
		inviteFriendAsyncTask task = new inviteFriendAsyncTask();
		task.execute(email, guest);
	}
	
	
	private class inviteFriendAsyncTask extends AsyncTask<String, Integer, Long> {
		@Override
		protected Long doInBackground(String... params) {
			
			
			int count = params.length;
			if (count<2) return null;
			
			RestMethodsHandler rmh = new RestMethodsHandler(settingCtrl.getSettings().getServerName());
			
			rmh.invokeFriendInvitation(params[0], params[1]);
			
			
			return null;
		}

	}
}
