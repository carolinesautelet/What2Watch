package com.example.what2watch;

import android.content.Context;
import android.database.Cursor;

public class Channel {
		private Context context;
		private String name = null;
		private Movie[] movies = null;
		public Channel(Context context , String Name){
			this.context = context;
			this.name = Name;
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Channel WHERE Name = ?",new String[] {Name});
	    	int i = 0;
	    	if(data.moveToFirst()){
	    		movies[i] = new Movie(context , data.getString(1), null);
	    		i++;
	    		while(data.moveToNext()){
	    			movies[i] = new Movie(context , data.getString(1), null);
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
		public Movie[] getMovies() {
			return movies;
		}
		public void setMovies(Movie[] movies) {
			this.movies = movies;
		}
		
}
