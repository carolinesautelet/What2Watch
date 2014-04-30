package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Identification_user extends Activity{
	
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	Button letsGO=null;
	Spinner spinner;
	dbAdapter db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.choose_user);
	   
		db = new dbAdapter(this);         
    	db.createDatabase();       
    	db.open(); 
    	
    	spinner = (Spinner) findViewById(R.id.choose_user_selectuser_spinner);
    	Button create = (Button)findViewById(R.id.choose_user_create);
    	letsGO = (Button) findViewById(R.id.choose_user_connect);
		
    	letsGO.setOnClickListener(Listenerconnect);
		create.setOnClickListener(Listenercreate);
		
    	//Cursor data = db.execSQL("SELECT rowid as _id, Login  FROM User", null);
    	Set<String> set = db.getAllData("Login","User");
    	List<String> list = new ArrayList<String>(set);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		    android.R.layout.simple_spinner_item, list);
    		       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		       spinner.setAdapter(adapter);
    	db.close();
	}


	private OnClickListener Listenerconnect = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Identification_user.this, Accueil.class);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	private OnClickListener Listenercreate = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Identification_user.this, Create_user.class);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
	
}
