package com.lrepafi.quizgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;
import com.lrepafi.quizgame.controllers.*;
import android.view.View.OnClickListener;

public class SettingsActivity extends Activity {
	
	Spinner server;
	SettingController settingCtrl = new SettingController();
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.settings);
	        
	        Spinner spinner = (Spinner) findViewById(R.id.spinnerServer);
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                this, R.array.server_array, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);

	        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	        
	        Button btnPrefs = (Button) findViewById(R.id.buttonSelectPreferences);
	        btnPrefs.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					
				}
	        });
	   }
	   
       public class MyOnItemSelectedListener implements OnItemSelectedListener {

    	    public void onItemSelected(AdapterView<?> parent,
    	            View view, int pos, long id) {
    	        
    	    	settingCtrl.updateServer(parent.getItemAtPosition(pos).toString());
    	    	
   	    }

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

       }
}
