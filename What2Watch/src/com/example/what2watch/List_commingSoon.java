package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_commingSoon extends Activity {
	Context context = null;
	User user = null;
	List<Movie> movies = null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list_of_cinema);
		context = this.getApplicationContext();
		user = getIntent().getExtras().getParcelable("User");
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		movies = new ArrayList<Movie>();
		List<String> titles = new ArrayList<String>();
		Cursor data = db.execSQL("SELECT rowid as _id, ID FROM Movie WHERE ComingSoon NOT NULL ", null);
		Movie toadd = null;
		if(data.moveToFirst()){
			toadd = new Movie(context,data.getString(1),user);
			movies.add(toadd);
			titles.add(toadd.getTitle());
			while(data.moveToNext()){
				toadd = new Movie(context,data.getString(1),user);
				movies.add(toadd);
				titles.add(toadd.getTitle());
			}
			
		}
		ListView list = (ListView) findViewById(R.id.list_of_Cinema_Listview);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
		list.setAdapter(adapter);
		list.setOnItemClickListener(listenerListMovie);
		db.close();
	}
	private OnItemClickListener listenerListMovie = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {

			Movie movie = movies.get(position);
			String iD = movie.getId();
			Intent intent = new Intent(List_commingSoon.this, Movie_Activity.class);
			intent.putExtra("ID", iD);
			intent.putExtra("User",user);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
}
