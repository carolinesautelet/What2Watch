package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class ChannelSet {

	private List<Channel> all = null;
	private int nbr = 0;
	private Context context =null;
	
	public ChannelSet (Context context){
		this.context = context;
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT rowid as _id, Name FROM Channel GROUP BY Name",null);
		Channel toadd = null;
		this.all = new ArrayList<Channel>();
		if(data.moveToFirst()){
			if(this.context!=null && data.getString(1)!=null){
			toadd = new Channel(this.context,data.getString(1));
			all.add(toadd);
			nbr++;
			}
			while(data.moveToNext()){
				if(this.context!=null && data.getString(1)!=null){
				toadd = new Channel(this.context,data.getString(1));
				all.add(toadd);
				nbr++;
				}
			}
		}
		db.close();
	}
	
	public List<Channel> getAllChannel()
	{
		return all;
	}
	public void findChannel(String id,List<Channel> channels){
		Channel current = null;
		Cursor test = null;
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		for(int i=0;i<this.nbr;i++){
			current = all.get(i);
			if(current !=null){
				test = db.execSQL("SELECT rowid as _id, Time FROM Channel WHERE ID = ? AND Name = ?",new String[] {id , current.getName()});
				if(test!=null){
					if(test.moveToFirst()){
						channels.add(current);
					}
				}
			}
		}
		db.close();
	}
	public void getNamesFromList(List<Channel> channels,List<String> names){
		for(int i =0;i<channels.size();i++){
			names.add(channels.get(i).getName());
		}

	}
	public List<String> getallName(){
		List<String> allName = new ArrayList<String>();
		for(int i=0;i<this.nbr;i++){
			allName.add(this.all.get(i).getName());
		}
		return allName;
	}
	public void playingToday(List<Movie> movies ,List<String> name){
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT rowid as _id, Name, ID FROM Channel WHERE DATE(Time) = CURRENT_DATE", null);
		Movie toadd = null;
		if(data.moveToFirst()){
			toadd = new Movie(this.context,data.getString(2),null);
			movies.add(toadd);
			name.add(data.getString(1));
			while(data.moveToNext()){
				toadd = new Movie(this.context,data.getString(2),null);
				movies.add(toadd);
				name.add(data.getString(1));
			}
		}
		db.close();
		
	}
	
}
