package com.example.what2watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Channel_option extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.channel);
		
		Button playingToday=null;
		Button channel=null;
		
        channel =(Button)findViewById(R.id.channel_button_channel);
		playingToday = (Button) findViewById(R.id.channel_button_playingtoday);
        
        channel.setOnClickListener(listenerchannel);
		playingToday.setOnClickListener(listenerplayingtoday);
        
		}
	
    	private OnClickListener listenerchannel = new OnClickListener() {
    		@Override
      		public void onClick(View v) {
    			String queryString  = "SELECT DISTINCT rowid as _id, Name, ID FROM Channel ORDER BY Name ASC";
    			String[] display = {"Name"};
    			String[] whereArgs = null;
    			send(queryString, whereArgs, display, "Channel");

    		}
    	};
    	
        private OnClickListener listenerplayingtoday = new OnClickListener() {
    		@Override
      		public void onClick(View v) {
    			String queryString  = "SELECT rowid as _id, Name, ID FROM Channel WHERE DATE(Time) = CURRENT_DATE";
    			String[] display = {"Name"};
    			String[] whereArgs = null;
    			send(queryString, whereArgs, display, "Channel");
    		}
    	};
    	
    	public void send(String query, String[] args, String[] WhatToDisplay, String browse_by)
    	{
    		Intent intent = new Intent(Channel_option.this, List_of_request.class);
    		
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