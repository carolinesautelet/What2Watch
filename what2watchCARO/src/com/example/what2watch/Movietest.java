package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

public class Movietest extends Activity{

	ListView List=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movietest);
	
		ListView list = (ListView) findViewById(R.id.movietest_L);
		
		dbAdapter mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	Cursor data = mDbHelper.execSQL("SELECT rowid as _id, Title, Year FROM Movie", null);
    		
    	SimpleCursorAdapter cursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, data, new String[] {"Title","Year"}, new int[] {android.R.id.text1,android.R.id.text2});
    	list.setAdapter(cursorAd);
    	mDbHelper.close();
		
		
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

}