package com.example.what2watch;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Cinema_option extends Activity {
	LocationManager lManager = null;
	Location locationthis = null;
	dbAdapter db = null;
	Context context = null;
	User user = null;
	//The minimum distance to change updates in metters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 metters

    //The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cinema);
		user = getIntent().getExtras().getParcelable("User");
		context = this.getApplicationContext();
		Button comingsoon=null;
		Button closest=null;
		Button all=null;
	
		comingsoon =(Button)findViewById(R.id.cinema_button_coming_soon);
		closest = (Button) findViewById(R.id.cinema_button_closest_cinema);
		all = (Button) findViewById(R.id.cinema_button_all_cinemas);
		
		lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		
		
		LocationListener locationListener = new LocationListener(){
			public void onLocationChanged(Location location) {
				locationthis = new Location( location); 
		
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}

		};
		
		lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		comingsoon.setOnClickListener(listenercomingsoon);
		closest.setOnClickListener(listenerclosest);
		all.setOnClickListener(listenerall);
    
		}

	private OnClickListener listenercomingsoon = new OnClickListener() {
		@Override
  		public void onClick(View v) {
			Intent intent = new Intent(Cinema_option.this, List_of_Cinema.class);
			intent.putExtra("User", user);
			intent.putExtra("ComingSoon", true);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);

		}
	};
	
	private OnClickListener listenerclosest = new OnClickListener() {
		@Override
		public void onClick(View v) {
			CinemaSet set = new CinemaSet(context);
			Cinema closest = set.findClosest(locationthis);
			Intent intent = new Intent(Cinema_option.this, Cinema_Activity.class);
			intent.putExtra("Cinema",closest);
			intent.putExtra("User", user);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}

	};
	
    private OnClickListener listenerall = new OnClickListener() {
		@Override
  		public void onClick(View v) {
			
			Intent intent = new Intent(Cinema_option.this, List_of_Cinema.class);
			intent.putExtra("User", user);
			intent.putExtra("ComingSoon", false);

			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
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