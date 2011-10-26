package com.lrepafi.quizgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;
import com.lrepafi.quizgame.controllers.*;
import com.lrepafi.quizgame.entities.Settings.Category;

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
					
					final CharSequence[] items = {Category.LITERATURE.toString(), "Green", "Blue"};
					final boolean values[] = {false, false, false};

					AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
					builder.setTitle("Pick a color");
					/*builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
					    }
					});*/
					
					builder.setMultiChoiceItems(items, values, new DialogInterface.OnMultiChoiceClickListener() {
						
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
							   
						}
					});
					AlertDialog alert = builder.create();
					alert.show();

					   
				}
	        });
	        
	        //registerForContextMenu(btnPrefs);

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
}
