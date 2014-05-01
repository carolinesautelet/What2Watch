package com.example.what2watch;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Cinema implements Parcelable{
	private String name = null;
	private String longitude = null;
	private String latitude= null;
	private Movie[] movies = null;
	private Context context = null;
	TimeTable[] table = null;
		public Cinema(String name, String longitude, String latitude) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
		public Cinema(Context context , String Name){
			this.context = context;
			this.name = Name;
			dbAdapter mDbHelper = new dbAdapter(context);  
			mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, ID FROM Cinema WHERE Name = ?",new String[] {Name});
	    	int i = 0;
	    	if(data.moveToFirst()){
	    		movies[i] = new Movie(context , data.getString(1));
	    		i++;
	    		while(data.moveToNext()){
	    			movies[i] = new Movie(context , data.getString(1));
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
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public TimeTable[] getTable() {
			return table;
		}
		public void setTable(TimeTable[] table) {
			this.table = table;
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
			dest.writeString(latitude);
			dest.writeString(longitude);
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
					this.latitude = in.readString();
					this.longitude = in.readString();
				}
		
}
