
package com.example.what2watch;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Movie implements Parcelable{
	private Context mContext;
	String id =null;
	String title = null;
	int year = 0;
	String[] actors = null;
	String director = null;
	int duration = 0;
	int ageLimit = 0;
	String[] genre = null;
	String synopsis = null;
	String trailerLink = null;
	List<Cinema> cinemas = null;
	List<Channel> channels = null;
	
	public void toaster(String txt){
		Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
	}
	
	public Movie(Context context, String id){	
		this.mContext = context;
		
		dbAdapter mDbHelper = new dbAdapter(mContext);  
		mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	
    	String[] args = {id};
    	final Cursor data = mDbHelper.execSQL("SELECT rowid as _id, Title, Year, Duration, Synopsis, TrailerLink, AgeLimit FROM Movie WHERE ID = ?", args);
		data.moveToFirst();
		
		this.id = id;
		this.title=data.getString(1);
		this.year=data.getInt(2);
		this.duration=data.getInt(3);
		this.synopsis=data.getString(4);
		this.trailerLink=data.getString(5);
		this.ageLimit=data.getInt(6);
		
       mDbHelper.close();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String[] getActors() {
		return actors;
	}
	
	public void setActors(String[] actors) {
		this.actors = actors;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getAgeLimit() {
		return ageLimit;
	}
	
	public void setAgeLimit(int ageLimit) {
		this.ageLimit = ageLimit;
	}
	
	public String getSynopsis() {
		return synopsis;
	}
	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public String getTrailerLink() {
		return trailerLink;
	}
	
	public void setTrailerLink(String trailerLink) {
		this.trailerLink = trailerLink;
	}
	
	
	
	public String[] getGenre() {
		return genre;
	}
	
	public void setGenre(String[] genre) {
		this.genre = genre;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeInt(year);
		dest.writeStringArray(actors);
		dest.writeStringArray(genre);
		dest.writeString(director);
		dest.writeInt(duration);
		dest.writeInt(ageLimit);
		dest.writeString(synopsis);
		dest.writeString(trailerLink);
		dest.writeTypedList(cinemas);
		dest.writeTypedList(channels);
		
	}
	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
			{
		@Override
		public Movie createFromParcel(Parcel source)
		{
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size)
		{
			return new Movie[size];
		}
			};

			public Movie(Parcel in) {
				this.id = in.readString();
				this.title=in.readString();
				this.year=in.readInt();
				in.readStringArray(actors);
				in.readStringArray(genre);
				this.director = in.readString();
				this.duration=in.readInt();
				this.ageLimit=in.readInt();
				this.synopsis=in.readString();
				this.trailerLink=in.readString();
				in.readTypedList(cinemas,Cinema.CREATOR);
				in.readTypedList(channels,Channel.CREATOR);
				
			}
	
}
