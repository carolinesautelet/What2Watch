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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class Cinema_Activity extends Activity {
	Spinner programme=null;
	TextView distance = null;
	TextView distanceM = null;
	Button navigate = null;
	TextView name = null;
	Cinema cinema;
	dbAdapter db=null;
	private LocationManager lManager;
    private Location locationCinema;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cinema_activity);
		cinema = getIntent().getExtras().getParcelable("Cinema");
		name = (TextView)findViewById(R.id.cinema_activity_name);
		name.setText(cinema.getName());
		programme = (Spinner)findViewById(R.id.cinema_activity_programmation);
		distance = (TextView)findViewById(R.id.cinema_activity_distance_to_cinema);
		distanceM = (TextView)findViewById(R.id.cinema_activity_distance_to_cinema_miles);
		db=new dbAdapter(this);
		db.createDatabase();
		db.open();
		Set<String> set = db.getAllDataSingle("SELECT rowid as _id, Title , ID FROM Movie WHERE ID IN (SELECT ID FROM Cinema WHERE Name = ? )",new String[] {cinema.getName()});
		List<String> list = new ArrayList<String>(set);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		programme.setAdapter(adapter);
		if(cinema.getLatitude()!=null && cinema.getLongitude()!=null){
			lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			// Define a listener that responds to location updates
			LocationListener locationListener = new LocationListener() {
			    public void onLocationChanged(Location location) {
			      // Called when a new location is found by the network location provider.
			      locationCinema = new Location(location);
			      locationCinema.setLatitude(Double.parseDouble(cinema.getLatitude()));
			      locationCinema.setLongitude(Double.parseDouble(cinema.getLongitude()));
			      distance.setText(Float.toString(location.distanceTo(locationCinema)/1000) + " km");
			      distanceM.setText(Float.toString((location.distanceTo(locationCinema)/1000 )* (float)0.62137)+" miles");
			      
			    }

			    public void onStatusChanged(String provider, int status, Bundle extras) {}

			    public void onProviderEnabled(String provider) {}

			    public void onProviderDisabled(String provider) {}
			  };

			// Register the listener with the Location Manager to receive location updates
			lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
		
		
		
	}
	
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

	

}
