package com.example.what2watch;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Channel implements Parcelable{
		private Context context;
		private String name = null;
		private List<Movie> movies = null;
		public Channel(Context context , String Name){
			this.context = context;
			this.name = Name;
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Channel WHERE Name = ?",new String[] {Name});
	    	int i = 0;
	    	if(data.moveToFirst()){
	    		movies.add(new Movie(context , data.getString(1)));
	    		i++;
	    		while(data.moveToNext()){
	    			movies.add(new Movie(context , data.getString(1)));
	    			i++;
	    		}
	    	}
	    	mDbHelper.close();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Movie> getMovies() {
			return movies;
		}
		public void setMovies(List<Movie> movies) {
			this.movies = movies;
		}
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(name);
			dest.writeTypedList(movies);
			
		}
		public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>()
				{
			@Override
			public Channel createFromParcel(Parcel source)
			{
				return new Channel(source);
			}

			@Override
			public Channel[] newArray(int size)
			{
				return new Channel[size];
			}
				};

				public Channel(Parcel in) {
					this.name = in.readString();
					in.readTypedList(movies, Movie.CREATOR);
					
				}
		
		
}
