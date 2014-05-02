package com.example.what2watch;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_of_Cinema extends Activity {
	ListView list=null;
	Cursor data=null;
	dbAdapter mDbHelper = null;
	User user = null;
	boolean comingSoon = false;
	Context context = null;
	CinemaSet set = null;
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
		comingSoon = getIntent().getExtras().getBoolean("ComingSoon");
		set = new CinemaSet(context);
		
		list = (ListView) findViewById(R.id.list_of_Cinema_Listview);
		List<String> display = set.getallName();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,display);
		list.setAdapter(adapter);
		list.setOnItemClickListener(listenerList);
		if(comingSoon){
			dbAdapter db = new dbAdapter(context);
			db.createDatabase();
			db.open();
			Cursor data = db.execSQL("SELECT rowid as _id, ID FROM Movie WHERE ComingSoon NOT NULL ", null);
			db.close();
		}
	}

	private OnItemClickListener listenerList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
				Cinema cinema = set.getallCinema().get(position);
				String test = cinema.getName();
				List<Movie> movies = cinema.getMovies();
				if(movies!=null){
					toaster("cinema getMovies 0 : " + movies.get(0).toString());
				}
				else{
					toaster("cinema movie null");
				}
				Intent Activity2 = new Intent(List_of_Cinema.this, Cinema_Activity.class);
				Activity2.putExtra("int",position);
				Activity2.putExtra("User",user);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}

	};
	private OnItemClickListener listenerListMovie = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			
				data.moveToFirst();
				int i = 0;
				while(i<position){
					data.moveToNext();
					i++;
				}
				String iD = data.getString(2);
				Intent intent = new Intent(List_of_Cinema.this, Movie_Activity.class);
				intent.putExtra("ID", iD);
				intent.putExtra("User",user);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}


}
