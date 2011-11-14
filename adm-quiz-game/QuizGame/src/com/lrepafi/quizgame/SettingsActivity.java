package com.lrepafi.quizgame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;

import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.utils.RestMethodsHandler;

import android.view.View.OnClickListener;

public class SettingsActivity extends Activity {

	public static String PREFERENCES = "QuizGamePreferences";
	private SharedPreferences preferences = null;
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
		      getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		
		settingCtrl.load(preferences);
		
		final EditText editT = (EditText) findViewById(R.id.editTextEmail);
		
		editT.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	                	
	        	settingCtrl.getSettings().setEmail(editT.getText().toString());
	            //Toast.makeText(SettingsActivity.this, "Cambiato", Toast.LENGTH_SHORT).show();
	        }

	    });
		editT.setText(settingCtrl.getSettings().getEmail());
		
		final EditText editA = (EditText) findViewById(R.id.editTextUsername);
		
		editA.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {}
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	            settingCtrl.getSettings().setUsername(editA.getText().toString());
	            //Toast.makeText(SettingsActivity.this, "Cambiato", Toast.LENGTH_SHORT).show();
	        }

	    });
		editA.setText(settingCtrl.getSettings().getUsername());

		
		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerServer);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.server_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);
		
		//SharedPreferences preferences = getSharedPreferences("oo", Context.MODE_PRIVATE); 
		

		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
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

				//FIXME Delete this
				/* BEGIN
				String x = null;
				x.substring(3);
				//END */

				final CharSequence[] items = settingCtrl.getPrefsKeys();

				final boolean[] values= settingCtrl.getPrefsValues();

				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setTitle("Question preferences");

				builder.setMultiChoiceItems(items, values, new DialogInterface.OnMultiChoiceClickListener() {

					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// TODO Auto-generated method stub
						settingCtrl.updatePreference(which, isChecked);
						updatePreferenceText();
						//Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();

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
	/*@Override
	   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

	   super.onCreateContextMenu(menu, v, menuInfo);
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.prefs_question, menu);

	   }

	   @Override 
	   public boolean onContextItemSelected(MenuItem item) {

	   switch(item.getItemId()) {

	   case R.id.literature :
	   	//TODO
	   	return true;
	   case R.id.science :
	   	//TODO
	   	return true;
	   default:
	   	return super.onContextItemSelected(item);
	   }
	   }*/

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent,
				View view, int pos, long id) {

			settingCtrl.updateServer(parent.getItemAtPosition(pos).toString());

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	public void sendFriendInvitation(String email, String guest) {
		inviteFriendAsyncTask task = new inviteFriendAsyncTask();
		task.execute(email, guest);
	}
	
	
	private class inviteFriendAsyncTask extends AsyncTask<String, Integer, Long> {
		@Override
		protected Long doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			int count = params.length;
			if (count<2) return null;
			
			RestMethodsHandler rmh = new RestMethodsHandler(settingCtrl.getSettings().getServerName());
			
			rmh.invokeFriendInvitation(params[0], params[1]);
			
			//To be deleted
			rmh.invokePutScore(params[1].replaceAll("@", "a"), params[1], 11000);
			
			return null;
		}

	}
}
