package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class Movietest extends Activity {

	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	ListView List=null;
	Cursor data=null;
	User user = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movietest);
		user = getIntent().getExtras().getParcelable("User");
		List = (ListView) findViewById(R.id.movietest_L);
		
		dbAdapter mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	data = mDbHelper.execSQL("SELECT rowid as _id, ID, Title, Year FROM Movie ", null);
    		
    	SimpleCursorAdapter cursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, data, new String[] {"Title","Year"}, new int[] {android.R.id.text1,android.R.id.text2});
    	List.setAdapter(cursorAd);
    	
    	mDbHelper.close();
		
    	List.setOnItemClickListener(listenerList);
		
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

	private OnItemClickListener listenerList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			data.moveToPosition(position);
			String ids;
			try {
	    		 ids =  data.getString(data.getColumnIndex("ID"));
	    	 } catch (Exception ex) {
	    		 ids = "";
	    	 }
			
			Intent Activity2 = new Intent(Movietest.this, Movie_Activity.class);
			Activity2.putExtra("ID", ids);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		
		}
	};
	
}