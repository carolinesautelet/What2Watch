package com.example.what2watch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

public class Movie_Activity extends Activity {

	public void toaster(String txt){
		Toast.makeText(Movie_Activity.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	private ProgressDialog progressDialog;
	private boolean resumeHasRun = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movie);
		
		Intent intent = getIntent();
		
		TextView title = (TextView)findViewById(R.id.movie_title);
		TextView year = (TextView)findViewById(R.id.movie_year);
		TextView duration = (TextView)findViewById(R.id.movie_duration);
		TextView genre1 = (TextView)findViewById(R.id.movie_genre1);
		TextView genre2 = (TextView)findViewById(R.id.movie_genre2);
		TextView genre3 = (TextView)findViewById(R.id.movie_genre3);
		TextView director = (TextView)findViewById(R.id.movie_director);
		TextView synopsis= (TextView)findViewById(R.id.movie_synopsis);
		TextView viewed = (TextView)findViewById(R.id.movie_viewed);
		
		Spinner spinnerActor = (Spinner) findViewById(R.id.movie_actors_spinner);
		
		Button trailer =(Button)findViewById(R.id.movie_trailer);
		Button channel =(Button)findViewById(R.id.movie_findchannel);
		Button cinema =(Button)findViewById(R.id.movie_findcinema);
		
		String id = intent.getStringExtra("ID");
		
		dbAdapter mDbHelper = new dbAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	
    	String[] args = {id};
    	final Cursor data = mDbHelper.execSQL("SELECT rowid as _id, Title, Year, Duration, Synopsis, TrailerLink, AgeLimit FROM Movie WHERE ID = ?", args);
		data.moveToFirst();
		
		/*Creation de l'objet Movie*/
		Movie movie  = new Movie(id,data.getString(1),data.getInt(2),data.getInt(3),data.getString(4),data.getString(5),data.getInt(6));
		title.setText(movie.getTitle());
		year.setText(Integer.toString(movie.getYear()));
		duration.setText(Integer.toString(movie.getDuration()));
		synopsis.setText(movie.getSynopsis());
		
		/*Recherche de director*/
		Cursor dataDirector = mDbHelper.execSQL("SELECT rowid as _id, Name FROM Director WHERE ID = ?",args);
		dataDirector.moveToFirst();
		movie.setDirector(dataDirector.getString(1));
		director.setText(movie.getDirector());
		
		/*recherche de tous les actors*/
		Set<String> set = mDbHelper.getAllDataCDT("Name","Actor","ID",id);
    	List<String> list = new ArrayList<String>(set);
    	String[] actors = new String[list.size()];
    	list.toArray(actors);
    	movie.setActors(actors);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		    android.R.layout.simple_spinner_item, list);
    		       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		       spinnerActor.setAdapter(adapter);
    		       
    /*Recherche des genres*/
       Cursor dataGenre = mDbHelper.execSQL("SELECT rowid as _id,  GenreNAME FROM Genre WHERE ID = ?" , args);
       dataGenre.moveToFirst();
       String[] genres = new String[3];
       genres[0] = dataGenre.getString(1);
       genre1.setText(genres[0]);
       if(dataGenre.moveToNext()){
    	   genres[1]  = dataGenre.getString(1);
    	   genre2.setText("     |     " + genres[1]);
    	   if(dataGenre.moveToNext()){
    		   genres[2] = dataGenre.getString(1);
    		   genre3.setText("     |     " + genres[2]);
    		}
       }
       movie.setGenre(genres);
       // AJOUTER LE NOMBRE DE FOIS QUE L'UTILISATEUR A REGARDE LE FILM !!!!!!
       /*
        * database puis cast nbrfois dans numberofview
        * viewed.setText(getResources().getString(R.string.movie_viewed, numberofview));
        */

       mDbHelper.close();
		
		trailer.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View vue) {
				setProgressBarIndeterminate(true);
				progressDialog = ProgressDialog.show(Movie_Activity.this, "Wait", "Retreiving information from Youtute", true);
				progressDialog.setCancelable(true);
				showVideo(data.getString(5));
			}
		});
		



	} //OnCreate
	
	@Override
	protected void onResume() {
	    super.onResume();
	    if (!resumeHasRun) {
	        resumeHasRun = true;
	        return;
	    }
	    progressDialog.dismiss();
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
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
	
}