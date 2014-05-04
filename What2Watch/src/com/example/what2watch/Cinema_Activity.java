package com.example.what2watch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class Cinema_Activity extends Activity {
	Context context = null;
	boolean firstStep = true;
	ListView programme=null;
	TextView distance = null;
	TextView distanceM = null;
	Button navigate = null;
	TextView name = null;
	Cinema cinema;
	Set<String> other = null;
	dbAdapter db=null;
	List<Movie> moviesHere = null;
	private LocationManager lManager;
	private Location locationCinema;
	User user = null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cinema_activity);
		context=this.getApplicationContext();
		user = getIntent().getExtras().getParcelable("User");
		String nameCinema = getIntent().getExtras().getString("Name");
		cinema = new Cinema(this,nameCinema);
		name = (TextView)findViewById(R.id.cinema_activity_name);
		name.setText(cinema.getName());
		programme = (ListView)findViewById(R.id.cinema_activity_programmation);
		distance = (TextView)findViewById(R.id.cinema_activity_distance_to_cinema);
		distanceM = (TextView)findViewById(R.id.cinema_activity_distance_to_cinema_miles);
		final List<Movie> allMovies = cinema.getMovies();
		List<String> allTitle = cinema.getAllMoviesTitle();
		List<String> times = cinema.getAllTime();
		List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> element;
		for(int i = 0;i<allTitle.size();i++){
			element = new HashMap<String, String>();
			element.put("text1", allTitle.get(i));
			element.put("text2", times.get(i));
			liste.add(element);
		}
		ListAdapter adapter = new SimpleAdapter(this,liste,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
		programme.setAdapter(adapter);
			if(cinema.getLatitude()!=0.0 && cinema.getLongitude()!=0.0){
				lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				// Define a listener that responds to location updates
				LocationListener locationListener = new LocationListener() {
					public void onLocationChanged(Location location) {
						// Called when a new location is found by the network location provider.
						distance.setText(Float.toString(cinema.getDistance(location)/1000) + " km");
						distanceM.setText(Float.toString((cinema.getDistance(location)/1000 )* (float)0.62137)+" miles");

					}

					public void onStatusChanged(String provider, int status, Bundle extras) {}

					public void onProviderEnabled(String provider) {}

					public void onProviderDisabled(String provider) {}
				};

				// Register the listener with the Location Manager to receive location updates
				lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			}
			else{
				distance.setText("No localisation information for this cinema");
			}

			programme.setOnItemClickListener(new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> adapter, View view, int position,
						long id) {	
					
					Movie movie = allMovies.get(position);
					Intent Activity2 = new Intent(Cinema_Activity.this, Movie_Activity.class);
					Activity2.putExtra("ID", movie.getId());
					Activity2.putExtra("User" , user);
					startActivity(Activity2);
					overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);


				}

			});
		
		

	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}



}
