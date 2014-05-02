package com.example.what2watch;

import java.util.ArrayList;
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
	boolean findCinema = false;
	Context context = null;
	CinemaSet set = null;
	List<Movie> moviesCS = null;
	List<String> names= null;
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
		findCinema = getIntent().getExtras().getBoolean("findCinema");
		toaster("findCinema : " + Boolean.toString(findCinema));
		if(!comingSoon && !findCinema){
			set = new CinemaSet(context);
			list = (ListView) findViewById(R.id.list_of_Cinema_Listview);
			List<String> display = set.getallName();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,display);
			list.setAdapter(adapter);
			list.setOnItemClickListener(listenerList);
		}
		if(comingSoon ){
			dbAdapter db = new dbAdapter(context);
			db.createDatabase();
			db.open();
			moviesCS = new ArrayList<Movie>();
			List<String> titles = new ArrayList<String>();
			Cursor data = db.execSQL("SELECT rowid as _id, ID FROM Movie WHERE ComingSoon NOT NULL ", null);
			Movie toadd = null;
			if(data.moveToFirst()){
				toadd = new Movie(context,data.getString(1));
				moviesCS.add(toadd);
				titles.add(toadd.getTitle());
				while(data.moveToNext()){
					toadd = new Movie(context,data.getString(1));
					moviesCS.add(toadd);
					titles.add(toadd.getTitle());
				}
				
			}
			list = (ListView) findViewById(R.id.list_of_Cinema_Listview);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
			list.setAdapter(adapter);
			list.setOnItemClickListener(listenerListMovie);
			db.close();
		}
		if(findCinema){
			CinemaSet myset = new CinemaSet(context);
			String id = getIntent().getExtras().getString("ID");
			toaster("id : "+id);

			List<Cinema> findCinemaList = new ArrayList<Cinema>();
			myset.findCinema(id,findCinemaList);
			/*names = new ArrayList<String>();*/
			//myset.getNamesFromList(findCinemaList,names);
			toaster("request done getNamesFromList");

			/*list = (ListView) findViewById(R.id.list_of_Cinema_Listview);
			if(names.size()>0){
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
			list.setAdapter(adapter);
			list.setOnItemClickListener(listenerListFindCinema);
			}
			else{
				names.add("Sorry , this film is not available ...");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
				list.setAdapter(adapter);
			}*/
		}
	}

	private OnItemClickListener listenerList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Cinema cinema = set.getallCinema().get(position);
			Intent Activity2 = new Intent(List_of_Cinema.this, Cinema_Activity.class);
			Activity2.putExtra("Name",cinema.getName());
			Activity2.putExtra("User",user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}

	};
	private OnItemClickListener listenerListMovie = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {

			Movie movie = moviesCS.get(position);
			String iD = movie.getId();
			Intent intent = new Intent(List_of_Cinema.this, Movie_Activity.class);
			intent.putExtra("ID", iD);
			intent.putExtra("User",user);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	/*private OnItemClickListener listenerListFindCinema = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {

			
			Intent intent = new Intent(List_of_Cinema.this, Cinema_Activity.class);
			intent.putExtra("User",user);
			intent.putExtra("Name",names.get(position));
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};*/

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}


}
