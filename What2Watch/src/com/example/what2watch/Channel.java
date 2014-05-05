package com.example.what2watch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.ParseException;
import android.text.format.DateFormat;
import android.util.Log;

public class Channel {
		private Context context;
		private String name = null;
		private String type = null;
		private List<Movie> movies = null;
		private List<Date> dates = null;
		
		public Channel(Context context , String Name){
			this.context = context;
			this.name = Name;
			if(context!=null && Name !=null){
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open();
	    	if(this instanceof Cinema){
	    		type = "Cinema";
	    	}else{
	    		type = "Channel";
	    	}
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID, Time FROM "+ type + " WHERE Name = ?",new String[] {Name});
	    	Movie toadd = null;
	    	Date newdate = null;
	    	String dateTime  = null;
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    	this.dates = new ArrayList<Date>();
	    	this.movies = new ArrayList<Movie>();
	    	if(data!=null){
	    	if(data.moveToFirst()){
	    		newdate = new Date();
	    		dateTime = data.getString(2);
	    		try{
	    			newdate = format.parse(dateTime);
	    		}catch(java.text.ParseException e){
	    			e.printStackTrace();
	    		}
	    		this.dates.add(newdate);
	    		toadd = new Movie(context , data.getString(1), null);
	    		this.movies.add(toadd);
	    		while(data.moveToNext()){
	    			newdate = new Date();
		    		dateTime = data.getString(2);
		    		try{
		    			newdate = format.parse(dateTime);
		    		}catch(java.text.ParseException e){
		    			e.printStackTrace();
		    		}
		    		this.dates.add(newdate);
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
		public List<String> getAllTime(){
			List<String> time = new ArrayList<String>();
			List<Date> dates = this.getDates();
			for(int i =0;i<dates.size();i++){
				time.add(i,dates.get(i).toString());
			}
			return time;
		}
		public List<Date> getDates() {
			return dates;
		}
		public void setDates(List<Date> dates) {
			this.dates = dates;
		}
		public Context getContext() {
			return context;
		}
		public void setContext(Context context) {
			this.context = context;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
}
