package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class Channel {
		private Context context;
		private String name = null;
		private List<Movie> movies = null;
		
		
		public Channel(Context context , String Name){
			this.context = context;
			this.name = Name;
			if(context!=null && Name !=null){
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Channel WHERE Name = ?",new String[] {Name});
	    	Movie toadd = null;
	    	this.movies = new ArrayList<Movie>();
	    	if(data!=null){
	    	if(data.moveToFirst()){
	    		toadd = new Movie(context , data.getString(1), null);
	    		this.movies.add(toadd);
	    		while(data.moveToNext()){
	    			toadd = new Movie(context , data.getString(1), null);
		    		this.movies.add(toadd);
	    		}
	    	}
	    	}
	    	mDbHelper.close();
			}
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public List<Movie> getMovies() {
			return this.movies;
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
