package com.example.what2watch;

import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Movie_Activity extends Activity {

	public void toaster(String txt){
		Toast.makeText(Movie_Activity.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	private ProgressDialog progressDialog;
	RatingBar ratingbar;
	private boolean TrailerHasRun = false;
	
	dbAdapter mDbHelper;
	Context context;
	Intent intent;
	User user;
	Movie movie;
	
	String trailerLink;
	String movie_title;
	String id;
	int age;
	
	Button save;
	Button plus1;
	Button trailer;
	Button channel;
	Button cinema;
	
	TextView viewed;
	TextView director;
	TextView title;
	TextView year;
	TextView duration;
	TextView genre1;
	TextView genre2;
	TextView genre3;
	TextView synopsis;
	
	Spinner spinnerActor;
	CheckBox checkbox;
	ImageView img ;
	
	boolean itemSelectedbyOnCreate;
	boolean MovieIsView=false;
	List<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movie);

		title = (TextView)findViewById(R.id.movie_title);
		year = (TextView)findViewById(R.id.movie_year);
		duration = (TextView)findViewById(R.id.movie_duration);
		genre1 = (TextView)findViewById(R.id.movie_genre1);
		genre2 = (TextView)findViewById(R.id.movie_genre2);
		genre3 = (TextView)findViewById(R.id.movie_genre3);
		synopsis= (TextView)findViewById(R.id.movie_synopsis);
		director = (TextView)findViewById(R.id.movie_director);
		viewed = (TextView)findViewById(R.id.movie_viewed);
		
		checkbox = (CheckBox) findViewById(R.id.movie_already_watched);
		
		spinnerActor = (Spinner) findViewById(R.id.movie_actors_spinner);
		
		img = (ImageView) findViewById(R.id.movie_img); 
		
		trailer =(Button)findViewById(R.id.movie_trailer);
		channel =(Button)findViewById(R.id.movie_findchannel);
		cinema =(Button)findViewById(R.id.movie_findcinema);
		plus1 = (Button) findViewById(R.id.movie_watched_once_more);
		save = (Button) findViewById(R.id.movie_button_save);
		
		spinnerActor = (Spinner) findViewById(R.id.movie_actors_spinner);
		
		ratingbar = (RatingBar) findViewById(R.id.movie_rating);
		
		save.setOnClickListener(listenerSave);
		plus1.setOnClickListener(listenerPlus1);
		checkbox.setOnClickListener(listenerCheck);
		spinnerActor.setOnItemSelectedListener(listenerActorList);
		director.setOnClickListener(listenerDirector);
		
		mDbHelper = new dbAdapter(this);         
		mDbHelper.createDatabase();     
       
		makeTheView();
		
       trailer.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View vue) {
				setProgressBarIndeterminate(true);
				progressDialog = ProgressDialog.show(Movie_Activity.this, "Wait", "Retreiving information from Youtute", true);
				progressDialog.setCancelable(true);
				showVideo(trailerLink);
			}
		});
		

	} //OnCreate
	
	
	@Override
	protected void onResume() {
	    super.onResume();
	    if (!TrailerHasRun) {  
	    	itemSelectedbyOnCreate=false;
	    	makeTheView();
	    	return;
	    }
	    progressDialog.dismiss();
	    TrailerHasRun = false;
	}
	
	protected void makeTheView(){
		
		/*r�cup�ration de l'intent*/
		intent = this.getIntent();
		id = intent.getStringExtra("ID");
		user = intent.getParcelableExtra("User");
		
		/*Creation de l'objet Movie*/
		movie  = new Movie(Movie_Activity.this, id, user);
		
		/*Affichage du text */
		title.setText(movie.getTitle());
		movie_title=movie.getTitle();
		year.setText(Integer.toString(movie.getYear()));
		duration.setText(Integer.toString(movie.getDuration()));
		synopsis.setText(movie.getSynopsis());
		trailerLink=movie.getTrailerLink();
		age = movie.getAgeLimit();
		
		/*affiche du bouton trailer*/
		if(trailerLink==null){
			trailer.setVisibility(View.GONE);
			trailer.setClickable(false);
		}
		
		
		/*affichage Rating*/
		if(movie.getRating() != 0){
			save.setVisibility(View.GONE);
			ratingbar.setIsIndicator(true);
		}
		ratingbar.setRating(movie.getRating());
					
		/*Recherche de director*/
		director.setText(movie.getDirector());
		
		/*affichage de tous les actors*/
		list = movie.getActors();
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinnerActor.setAdapter(adapter);
    		       
    	/*affichage des genres*/
    	String[] genres = movie.getGenre();
    	genre1.setText(genres[0]);
    	if(genres[1] != null){
    		genre2.setText("     |     " + genres[1]);
    		if(genres[2] != null ){
    			genre3.setText("     |     " + genres[2]);
    		}
    	}

    	/*nombre de fois que user � vu le film*/  
    	if(movie.getNumberOfView()>0)
    	{
    		checkbox.setChecked(true);
    		MovieIsView=true;
    	}
    	viewed.setText(getResources().getString(R.string.movie_viewed, movie.getNumberOfView()));
		
       /*affichage de l'image -12/-16/All*/
       if(age >= 16){
			img.setImageResource(R.drawable.tag16);
		}
		else if(age < 16 && age >=12 ){
			img.setImageResource(R.drawable.tag12);
		}	
		else{ 
			img.setImageResource(R.drawable.tagall);
		}
	}//makeTheView
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showVideo(String URL){
		TrailerHasRun = true;
		Uri url = Uri.parse(URL);
		startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"  + url.getQueryParameter("v"))));
	}
	
	private OnClickListener listenerSave = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(ratingbar.getRating()==0)
				toaster("A zero rate cannot be added to a movie");
			else{
			mDbHelper.open();
			mDbHelper.addRatingToDatabase(user.getLogin(), id, ratingbar.getRating());
			mDbHelper.close();
			save.setVisibility(View.GONE);
			ratingbar.setIsIndicator(true);
			}
		}
	};
	
	private OnClickListener listenerCheck = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(!MovieIsView){
				mDbHelper.open();
				mDbHelper.addNumberToDatabase(user.getLogin(), id);
				mDbHelper.close();
			}	
		}
	};
	
	private OnClickListener listenerPlus1 = new OnClickListener(){
		@Override
		public void onClick(View v) {
			mDbHelper.open();
			mDbHelper.execSQLInsert("UPDATE NumberOfView SET Number = \"" +Integer.toString((movie.getNumberOfView()+1))+ "\"  WHERE Login = ? and ID = ?",new String[] {user.getLogin(), id});
			if(movie.getNumberOfView()==0)
				mDbHelper.addNumberToDatabase(user.getLogin(), id);
			mDbHelper.close();
			movie.setNumberOfView(movie.getNumberOfView()+1);
			viewed.setText(getResources().getString(R.string.movie_viewed, movie.getNumberOfView()));
			checkbox.setChecked(true);
		}
	};
	
	private OnItemSelectedListener listenerActorList = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> adpater, View view, int position,	long id) {
			if(itemSelectedbyOnCreate){
				String actorName = list.get(position);
				String query = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Actor A WHERE M.ID=A.ID and Name like ?";
				String[] args = {actorName};
				String[]  WhatToDisplay = {"Title","Year"};
				String search_by = actorName;
				
				Intent Activity2 = new Intent(Movie_Activity.this, List_of_request.class);
				Bundle bundle = new Bundle();
				bundle.putString("requete", query);
				bundle.putStringArray("arguments", args);
				bundle.putStringArray("display", WhatToDisplay);
				bundle.putString("search_by", search_by);
				Activity2.putExtras(bundle);
				Activity2.putExtra("User", user);
				
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			else
				itemSelectedbyOnCreate=!itemSelectedbyOnCreate;
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener listenerDirector = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String query = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Director D WHERE M.ID=D.ID and Name like ?";
			String[] args = {movie.getDirector()};
			String[]  WhatToDisplay = {"Title","Year"};
			String search_by = movie.getDirector();
			
			Intent Activity2 = new Intent(Movie_Activity.this, List_of_request.class);
			Bundle bundle = new Bundle();
			bundle.putString("requete", query);
			bundle.putStringArray("arguments", args);
			bundle.putStringArray("display", WhatToDisplay);
			bundle.putString("search_by", search_by);
			Activity2.putExtras(bundle);
			Activity2.putExtra("User", user);
			
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