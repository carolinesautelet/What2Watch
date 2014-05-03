package com.example.what2watch;

import java.util.ArrayList;
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
public class Cinema implements Parcelable{
	private String name = null;
	private double longitude =  0.0;
	private double latitude= 0.0;
	private List<Movie> movies = null;
	private Context context = null;
	public void toaster(String txt){
		Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
	}
	public Cinema(Context context , String Name){
		this.context = context;
		this.name = Name;
		dbAdapter mDbHelper = new dbAdapter(context);  
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Cinema WHERE Name = ? GROUP BY ID",new String[] {Name});
		this.movies = new ArrayList<Movie>();
		Movie toadd = null;
		if(data.moveToFirst()){
			toadd = new Movie(context,data.getString(1),null);
			this.movies.add(toadd );
			
			while(data.moveToNext()){
				toadd = new Movie(context , data.getString(1),null);
				this.movies.add(toadd );
				
			}
			
		}
		Cursor loc = mDbHelper.execSQL("SELECT rowid as _id , Latitude , Longitude FROM Location WHERE Name = ?", new String[] {this.name});
		if(loc.moveToFirst()){
			this.longitude = Double.parseDouble(loc.getString(2));
			this.latitude = Double.parseDouble(loc.getString(1));
		}
		mDbHelper.close();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(name);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeTypedList(movies);
	}
	public static final Parcelable.Creator<Cinema> CREATOR = new Parcelable.Creator<Cinema>()
			{

		@Override
		public Cinema createFromParcel(Parcel source)
		{
			return new Cinema(source);
		}

		@Override
		public Cinema[] newArray(int size)
		{
			return new Cinema[size];
		}
			};

			public Cinema(Parcel in) {
				this.name = in.readString();
				this.latitude = in.readDouble();
				this.longitude = in.readDouble();
				in.readTypedList(movies, Movie.CREATOR);
			}
			public int getNbrMovie() {
				return movies.size();
			}
			
			public List<Movie> getMovies() {
				return this.movies;
			}
			public void setMovies(List<Movie> movies) {
				this.movies = movies;
			}
			public List<String> getAllMoviesTitle(){
				List<String> allName = new ArrayList<String>();
				List<Movie> movies = this.getMovies();
				for(int i =0;i<this.movies.size();i++){
					allName.add(i,movies.get(i).getTitle());
				}
				return allName;
			}

}
