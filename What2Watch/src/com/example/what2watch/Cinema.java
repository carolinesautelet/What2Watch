package com.example.what2watch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
public class Cinema extends Channel {
	private double longitude =  0.0;
	private double latitude= 0.0;
	
	public Cinema(Context context , String Name){
		super(context,Name);
		dbAdapter mDbHelper = new dbAdapter(context);  
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		Cursor loc = mDbHelper.execSQL("SELECT rowid as _id , Latitude , Longitude FROM Location WHERE Name = ?", new String[] {super.getName()});
		if(loc.moveToFirst()){
			this.longitude = Double.parseDouble(loc.getString(2));
			this.latitude = Double.parseDouble(loc.getString(1));
		}
		mDbHelper.close();
	}
	public List<String> getAllTime(){
		List<String> time = new ArrayList<String>();
		List<Date> dates = this.getDates();
		for(int i =0;i<dates.size();i++){
			time.add(i,dates.get(i).toString());
		}
		return time;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public float getDistance(Location location){
		Location cinema = new Location(location);
		cinema.setLatitude(this.getLatitude());
		cinema.setLongitude(this.getLongitude());
		return cinema.distanceTo(location);
	}


			

}
