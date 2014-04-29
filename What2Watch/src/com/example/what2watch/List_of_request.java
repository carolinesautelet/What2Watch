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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_of_request extends Activity {
	
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	ListView List=null;
	TextView View_search_by=null;
	Cursor data=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list_of_result);
	
		List = (ListView) findViewById(R.id.list_of_result_Listview);
		View_search_by = (TextView) findViewById(R.id.list_of_result_search_by);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		String requete = bundle.getString("requete");
		String[] arguments = bundle.getStringArray("arguments");
		String[] display = bundle.getStringArray("display");
		String text_search_by = bundle.getString("search_by");
		
		View_search_by.setText(text_search_by);
		
		dbAdapter mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	 
    	data = mDbHelper.execSQL(requete, arguments);
    	SimpleCursorAdapter cursorAd;
    	
    	if(display.length==2)
    		cursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, data, display, new int[] {android.R.id.text1,android.R.id.text2});
    	else
    		cursorAd = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, data, display, new int[] {android.R.id.text1});
    	
    	List.setAdapter(cursorAd);
    	
    	mDbHelper.close();
		
    	List.setOnItemClickListener(listenerList);
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
			
			Intent Activity2 = new Intent(List_of_request.this, Movie_Activity.class);
			Activity2.putExtra("ID", ids);
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
