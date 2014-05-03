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
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Channel WHERE Name = ?",new String[] {Name});
	    	int i = 0;
	    	if(data.moveToFirst()){
	    		movies.add(new Movie(context , data.getString(1), null));
	    		i++;
	    		while(data.moveToNext()){
	    			movies.add(new Movie(context , data.getString(1), null));
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
			return this.movies;
		}
		/*ATTENTION INDEX START FROM 1 NOT FROM 0 FRO THE RETURNED LIST!!!!*/
		public List<String> getAllMoviesTitle(){
			List<String> allName = new ArrayList<String>();
			List<Movie> movies = this.getMovies();
			allName.add(0,"Select Movie : ");
			for(int i =0;i<this.movies.size();i++){
				allName.add(i+1,movies.get(i).getTitle());
			}
			return allName;
		}
}
