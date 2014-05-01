package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Cinema_Activity extends Activity {
	Spinner programme=null;
	TextView timeToGo = null;
	Button navigate = null;
	TextView name = null;
	Cinema cinema;
	dbAdapter db=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list_of_cinema);
		cinema = getIntent().getExtras().getParcelable("Cinema");
		name = (TextView)findViewById(R.id.cinema_activity_name);
		name.setText(cinema.getName());
		programme = (Spinner)findViewById(R.id.cinema_activity_programmation);
		db=new dbAdapter(this);
		db.createDatabase();
		db.open();
		Set<String> set = db.getAllDataSingle("SELECT rowid as _id,Title FROM Movie WHERE ID IN (SELECT ID FROM Cinema WHERE Name = ? )",new String[] {cinema.getName()});
		List<String> list = new ArrayList<String>(set);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		programme.setAdapter(adapter);
		
		
		
	}
	
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

	

}
