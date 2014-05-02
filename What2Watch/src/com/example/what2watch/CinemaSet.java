package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class CinemaSet implements Parcelable {
	private List<Cinema> all = null;
	private int nbr = 0;
	private Context context = null;
	public CinemaSet(Context context){
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		all = new ArrayList<Cinema>();
		Cursor data = db.execSQL("SELECT rowid as _id, Name FROM Cinema GROUP BY Name",null);
		Cinema toadd = null;
		if(data.moveToFirst()){
			toadd = new Cinema(context , data.getString(1));
			all.add(toadd);
			nbr++;
			while(data.moveToNext()){
				toadd = new Cinema(context , data.getString(1));
				all.add(toadd);
				nbr++;
			}
		}
		db.close();
	}
	public List<Cinema> getallCinema(){
		return all;
	}
	public Cinema findClosest(Location location){
		Cinema bestForNow = all.get(0);
		Float distance = bestForNow.getDistance(location);
		for(int i = 1;i<this.nbr;i++){
			if(all.get(i).getDistance(location)<distance){
				bestForNow = all.get(i);
			}
		}
		return bestForNow;
	}
	public List<String> getallName(){
		List<String> allName = new ArrayList<String>();
		for(int i=0;i<this.nbr;i++){
			allName.add(this.all.get(i).getName());
		}
		return allName;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeTypedList(all);
		dest.writeInt(nbr);
		
	}
	public static final Parcelable.Creator<CinemaSet> CREATOR = new Parcelable.Creator<CinemaSet>()
			{
		@Override
		public CinemaSet createFromParcel(Parcel source)
		{
			return new CinemaSet(source);
		}

		@Override
		public CinemaSet[] newArray(int size)
		{
			return new CinemaSet[size];
		}
			};

			public CinemaSet(Parcel in) {
				in.readTypedList(all,Cinema.CREATOR);
				this.nbr = in.readInt();
				
			}
}
