package com.example.what2watch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class ChannelSet {

	private Channel[] all = null;
	private String type = null;
	private int nbr = 0;
	private Context context =null;

	public ChannelSet (Context context){
		this.context = context;
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		if(this instanceof CinemaSet){
			type = "Cinema";
		}
		else{
			type = "Channel";
		}
		Cursor data = db.execSQL("SELECT rowid as _id, Name FROM " +type+" GROUP BY Name",null);
		Channel toadd = null;
		this.all = new Channel[data.getCount()];//ArrayList<Channel>();
		if(data.moveToFirst()){
			if(this.context!=null && data.getString(1)!=null){
				if(type.compareTo("Channel")==0){
					toadd = new Channel(this.context,data.getString(1));
				}
				else{
					toadd = new Cinema(this.context,data.getString(1));
				}
				//all.add(toadd);
				all[0] = toadd;
				nbr++;
			}
			int i = 1;
			while(data.moveToNext()){
				if(this.context!=null && data.getString(1)!=null){
					if(type.compareTo("Channel")==0){
						toadd = new Channel(this.context,data.getString(1));
					}
					else{
						toadd = new Cinema(this.context,data.getString(1));
					}
					//all.add(toadd);
					all[i] = toadd;
					nbr++;
					i++;
				}
			}
		}
		db.close();
	}

	public List<Channel> getAllChannel()
	{
		return Arrays.asList(all);
	}
	public int findChannel(String id,Channel[] channels){//List<Channel> channels){
		Channel current = null;
		Cursor test = null;
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT rowid as _id,Name FROM "+type + " WHERE ID = ? GROUP BY Name",new String[] {id});
		int i = 0;
		if(data.moveToFirst()){
			if(type.compareTo("Channel")==0){
				current = new Channel(context,data.getString(1));
				
			}
			else{
				current = new Cinema(context,data.getString(1));
			}
			channels[i] = current;
			i++;
			while(data.moveToNext()){
				if(type.compareTo("Channel")==0){
					current = new Channel(context,data.getString(1));
					
				}
				else{
					current = new Cinema(context,data.getString(1));
				}
				channels[i]=current;
				i++;
			}
		}
		db.close();
		return i;
	}
	public void getNamesFromList(Channel[] channels,List<String> names){
		for(int i =0;i<channels.length;i++){
			if(channels[i]!=null){
				names.add(channels[i].getName());
			}
		}

	}
	public int nbrChannels(String Id){
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT rowid as _id, ID , Name FROM "+ type+" WHERE ID = ?",new String[] {Id});
		int res = data.getCount();
		db.close();
		return res;
	}
	public List<String> getallName(){
		List<String> allName = new ArrayList<String>();
		for(int i=0;i<this.nbr;i++){
			allName.add(this.all[i].getName());
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

	public int getNbr() {
		return nbr;
	}

	public void setNbr(int nbr) {
		this.nbr = nbr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
