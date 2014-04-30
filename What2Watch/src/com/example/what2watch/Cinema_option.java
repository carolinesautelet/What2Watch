package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Cinema_option extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cinema);
		
		Button comingsoon=null;
		Button closest=null;
		Button all=null;
	
		comingsoon =(Button)findViewById(R.id.cinema_button_coming_soon);
		closest = (Button) findViewById(R.id.cinema_button_closest_cinema);
		all = (Button) findViewById(R.id.cinema_button_all_cinemas);
		
		comingsoon.setOnClickListener(listenercomingsoon);
		closest.setOnClickListener(listenerclosest);
		all.setOnClickListener(listenerall);
    
		}

	private OnClickListener listenercomingsoon = new OnClickListener() {
		@Override
  		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Name, ID FROM Cinema WHERE DATE(Time) >= CURRENT_DATE";
			String[] display = {"Name"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Cinema");

		}
	};
	
    private OnClickListener listenerclosest = new OnClickListener() {
		@Override
  		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Name, ID FROM Cinema WHERE DATE(Time) >= CURRENT_DATE";
			String[] display = {"Name"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Cinema");
		}
	};
	
    private OnClickListener listenerall = new OnClickListener() {
		@Override
  		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Name, ID FROM Cinema GROUP BY Name";
			String[] display = {"Name"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Cinema");
		}
	};
	
	public void send(String query, String[] args, String[] WhatToDisplay, String browse_by)
	{
		Intent intent = new Intent(Cinema_option.this, List_of_request.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("requete", query);
		bundle.putStringArray("arguments", args);
		bundle.putStringArray("display", WhatToDisplay);
		bundle.putString("search_by", browse_by);
		intent.putExtras(bundle);
		
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
}