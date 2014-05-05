package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class CinemaSet extends ChannelSet{
	private List<Cinema> all = null;
	private int nbr = 0;
	private Context context = null;
	public void toaster(String txt){
		Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
	}
	public CinemaSet(Context context){
		super(context);
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
	public int findClosestInt(Location location){
		Cinema bestForNow = all.get(0);
		int ret = 0;
		Float distance = bestForNow.getDistance(location);
		for(int i = 1;i<this.nbr;i++){
			if(all.get(i).getDistance(location)<distance){
				bestForNow = all.get(i);
				ret = i;
			}
		}
		return ret;
	}
	
	public List<String> getallName(){
		List<String> allName = new ArrayList<String>();
		for(int i=0;i<this.nbr;i++){
			allName.add(this.all.get(i).getName());
		}
		return allName;
	}
	
}
