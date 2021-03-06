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

		set = new CinemaSet(context);
		list = (ListView) findViewById(R.id.list_of_Cinema_Listview);
		List<String> display = set.getallName();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,display);
		list.setAdapter(adapter);
		list.setOnItemClickListener(listenerList);



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

	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}


}
