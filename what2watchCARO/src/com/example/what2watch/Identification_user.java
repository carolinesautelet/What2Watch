package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Identification_user extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.choose_user);
	    Spinner spinner = (Spinner) findViewById(R.id.choose_user_selectuser_spinner);
		Button create = (Button)findViewById(R.id.choose_user_create);
		Button connect = (Button)findViewById(R.id.choose_user_connect);
		dbAdapter db = new dbAdapter(this);         
    	db.createDatabase();       
    	db.open(); 
    	
    	//Cursor data = db.execSQL("SELECT rowid as _id, Login  FROM User", null);
    	Set<String> set = db.getAllData("Login","User");
    	List<String> list = new ArrayList<String>(set);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		    android.R.layout.simple_spinner_item, list);
    		       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		       spinner.setAdapter(adapter);
    	db.close();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
	
}
