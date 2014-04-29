package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Movie extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movie);
		TextView title = (TextView)findViewById(R.id.movie_title);
		TextView year = (TextView)findViewById(R.id.movie_year);
		TextView duration = (TextView)findViewById(R.id.movie_duration);
		TextView genre1 = (TextView)findViewById(R.id.movie_genre1);
		TextView genre2 = (TextView)findViewById(R.id.movie_genre2);
		TextView genre3 = (TextView)findViewById(R.id.movie_genre3);
		TextView director = (TextView)findViewById(R.id.movie_director);
	    Spinner spinnerActor = (Spinner) findViewById(R.id.movie_actors_spinner);
		TextView synopsis= (TextView)findViewById(R.id.movie_synopsis);
		Button trailer =(Button)findViewById(R.id.movie_trailer);
		Button channel =(Button)findViewById(R.id.movie_findchannel);
		Button cinema =(Button)findViewById(R.id.movie_findcinema);
		String id = "tt0499549";
		dbAdapter mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	String[] args = {id};
    	final Cursor data = mDbHelper.execSQL("SELECT rowid as _id, Title, Year, Duration, Synopsis, TrailerLink, AgeLimit FROM Movie WHERE ID = ?", args);
		data.moveToFirst();
		title.setText(data.getString(1));
		year.setText(data.getString(2));
		duration.setText(data.getString(3));
		synopsis.setText(data.getString(4));
		Cursor dataDirector = mDbHelper.execSQL("SELECT rowid as _id, Name FROM Director WHERE ID = ?",args);
		dataDirector.moveToFirst();
		director.setText(dataDirector.getString(1));
		Set<String> set = mDbHelper.getAllDataCDT("Name","Actor","ID",id);
    	List<String> list = new ArrayList<String>(set);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		    android.R.layout.simple_spinner_item, list);
    		       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		       spinnerActor.setAdapter(adapter);
    
    Cursor dataGenre = mDbHelper.execSQL("SELECT rowid as _id,  GenreNAME FROM Genre WHERE ID = ?" , args);
    dataGenre.moveToFirst();
    genre1.setText(dataGenre.getString(1));
    if(dataGenre.moveToNext()){
    	genre2.setText("     |     " + dataGenre.getString(1));
    	if(dataGenre.moveToNext()){
    		genre3.setText("     |     " + dataGenre.getString(1));
    	}
    }
    
		mDbHelper.close();
		trailer.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View vue) {
				showVideo(data.getString(5));
			}
		});
		



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showVideo(String URL){
		Uri url = Uri.parse(URL);
		startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"  + url.getQueryParameter("v"))));
	}
	
}