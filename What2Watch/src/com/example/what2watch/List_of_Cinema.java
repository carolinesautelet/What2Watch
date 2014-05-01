package com.example.what2watch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_of_Cinema extends Activity {
	ListView List=null;
	Cursor data=null;
	dbAdapter mDbHelper = null;
	User user = null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list_of_cinema);
		user = getIntent().getExtras().getParcelable("User");
		List = (ListView) findViewById(R.id.list_of_Cinema_Listview);
		
		mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	data = mDbHelper.execSQL("SELECT rowid as _id, Name FROM Cinema GROUP BY Name", null);
    	SimpleCursorAdapter cursorAd;
    	cursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, data, new String[] {"Name"}, new int[] {android.R.id.text1});
    	List.setAdapter(cursorAd);
    	mDbHelper.close();
    	List.setOnItemClickListener(listenerList);
    	mDbHelper.close();
	}
	
	private OnItemClickListener listenerList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			toaster("onclick ");
			data.moveToPosition(position);
			String name = data.getString(1);
			toaster("name : "+ name);
			mDbHelper.open();
			Cursor loc  = mDbHelper.execSQL("SELECT rowid as _id,Latitude , Longitude FROM Location WHERE Name = ?",new String[] {name});
			if(loc.moveToFirst()){
				Cinema cinema = new Cinema(name , loc.getString(2),loc.getString(1));
				Intent Activity2 = new Intent(List_of_Cinema.this, Cinema_Activity.class);
				Activity2.putExtra("Cinema",cinema);
				Activity2.putExtra("User",user);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			else{
				Cinema cinema = new Cinema(name,null,null);
				Intent Activity2 = new Intent(List_of_Cinema.this, Cinema_Activity.class);
				Activity2.putExtra("Cinema",cinema);
				Activity2.putExtra("User", user);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			mDbHelper.close();
		
		}
		
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}


}
