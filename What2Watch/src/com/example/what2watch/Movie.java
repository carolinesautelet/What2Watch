
package com.example.what2watch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class Movie implements Parcelable{
	private Context mContext;
	dbAdapter mDbHelper;
	User user;
	
	String id =null;
	String title = null;
	String director = null;
	String synopsis = null;
	String trailerLink = null;
	String[] actors = null;
	String[] genre = null;
	
	int year = 0;
	int duration = 0;
	int ageLimit = 0;
	int rate;
	int numberofview;
	
	boolean MovieIsView;
	
	List<Cinema> cinemas = null;
	List<Channel> channels = null;
	
	List<String> list;
	public void toaster(String txt){
		Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
	}
	
	public Movie(Context context, String id, User user){	
		this.user = user;
		this.mContext = context;
		
		mDbHelper = new dbAdapter(mContext);  
		mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	
    	String[] args = {id};
    	final Cursor dataMovie = mDbHelper.execSQL("SELECT rowid as _id, Title, Year, Duration, Synopsis, TrailerLink, AgeLimit FROM Movie WHERE ID = ?", args);
    	dataMovie.moveToFirst();
		
		this.id = id;
		this.title=dataMovie.getString(1);
		this.year=dataMovie.getInt(2);
		this.duration=dataMovie.getInt(3);
		this.synopsis=dataMovie.getString(4);
		this.trailerLink=dataMovie.getString(5);
		this.ageLimit=dataMovie.getInt(6);
		
		if(user!=null)
		{
			/*rating*/
			Cursor dataRating = mDbHelper.execSQL("SELECT StarsNumber FROM Rating R, Movie M WHERE M.ID=R.ID and M.ID=?  and R.Login=?", new String[] {id, user.getLogin()});
			if (dataRating.getCount()<1){
				this.rate = 0;
			}
			else{
				dataRating.moveToFirst();
				this.rate = dataRating.getInt(dataRating.getColumnIndex("StarsNumber"));
			}
			
			/*Recherche de director*/
			Cursor dataDirector = mDbHelper.execSQL("SELECT rowid as _id, Name FROM Director WHERE ID = ?",args);
			dataDirector.moveToFirst();
			this.director = dataDirector.getString(1);
			
			/*recherche de tous les actors*/
			Set<String> set = mDbHelper.getAllDataCDT("Name","Actor","ID",id);
			list = new ArrayList<String>(set);
	    	String[] actors = new String[list.size()];
	    	list.toArray(actors);
	    	this.actors = actors;
	    	list.add(0,"Click to see all actors");
			
	    	/*Recherche des genres*/
	        Cursor dataGenre = mDbHelper.execSQL("SELECT rowid as _id,  GenreNAME FROM Genre WHERE ID = ?" , args);
	        dataGenre.moveToFirst();
	        genre = new String[3];
	        genre[0] = dataGenre.getString(1);
	        if(dataGenre.moveToNext()){
	     	   genre[1]  = dataGenre.getString(1);
	     	   if(dataGenre.moveToNext()){
	     		   genre[2] = dataGenre.getString(1);
	     		}
	        }
			
	        /*nombre de fois que user ? vu le film*/  
	        Cursor dataNbrofview = mDbHelper.execSQL("SELECT Number FROM NumberOfView WHERE Login=? and ID=?", new String[] {user.getLogin(),id});
	        if(dataNbrofview.getCount()<1){
	     	   this.numberofview=0;
	     	   this.MovieIsView=false;
	        }
	        else{
	     	   dataNbrofview.moveToFirst();
	     	   this.numberofview=dataNbrofview.getInt(dataNbrofview.getColumnIndex("Number"));
	     	   MovieIsView=true;
	        }
		}
		
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
	
	public List<String> getActors() {
		return list;
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
	
	public int getRating(){
		return rate;
	}
	
	public void setRating(int rate){
		this.rate = rate;
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
	
	public int getNumberOfView() {
		return numberofview;
	}
	
	public void setNumberOfView(int number) {
		this.numberofview = number;
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
			
				
			}
	
}
