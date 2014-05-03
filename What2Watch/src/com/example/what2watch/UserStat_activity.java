package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserStat_activity extends Activity {

	public void toaster(String txt){
		Toast.makeText(UserStat_activity.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	private ProgressBar Bar = null;
	private int progressBarStatus;
	
	dbAdapter db;
	User user;
	
	ImageView badge_pirate = null;
	ImageView badge_star = null;
	ImageView badge_ring = null;
	ImageView badge_disney = null;
	
	TextView username = null;
	TextView nbrFilm = null;
	TextView nbrHour = null;
	TextView nbrRate = null;
	TextView MostDir = null;
	TextView MostAct = null;
	TextView MostGenre = null;
	TextView level = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.user_stat);
		
		Bar = (ProgressBar) findViewById(R.id.user_stat_ProgressBar);
		
		badge_pirate = (ImageView) findViewById(R.id.user_stat_imgPirate); 
		badge_star = (ImageView) findViewById(R.id.user_stat_imgStarwars);
		badge_ring = (ImageView) findViewById(R.id.user_stat_imgLord);
		badge_disney = (ImageView) findViewById(R.id.user_stat_imgDisney);
		
		username = (TextView) findViewById(R.id.user_stat_UserName);
		nbrFilm = (TextView) findViewById(R.id.user_stat_nbrFilmV);
		nbrHour = (TextView) findViewById(R.id.user_stat_nbrHourV);
		nbrRate = (TextView) findViewById(R.id.user_stat_nbrRateV);
		MostDir = (TextView) findViewById(R.id.user_stat_MostDirV);
		MostAct = (TextView) findViewById(R.id.user_stat_MostActV);
		MostGenre = (TextView) findViewById(R.id.user_stat_MostGenrV);
		level = (TextView) findViewById(R.id.user_stat_Level);
		
		Intent intent = getIntent();
		user = intent.getParcelableExtra("User");
		
		username.setText(user.getFirstName() + " " +user.getName());
		
		db = new dbAdapter(this);         
    	db.createDatabase();    
    	db.open();
    	
    	String TextToSet;
   	
    	//création de l'objet UsetStat_Result
    	UserStat_Result userStatResult = new UserStat_Result(UserStat_activity.this, user);
    	
    	//nbrFilm
    	progressBarStatus=userStatResult.getNbrFilm();
    	TextToSet = Integer.toString(progressBarStatus);
    	nbrFilm.setText(TextToSet);
    	
    	
    	//nbrHour
    	TextToSet = Float.toString(userStatResult.getNbrHour());
    	nbrHour.setText(TextToSet);
    	
    	//nbrRate
		TextToSet = Integer.toString(userStatResult.getNbrRate());
    	nbrRate.setText(TextToSet);

    	//MostDir
    	
    	//MostAct
    	
    	//MostGenre
    	
    	//Progressbar and level
    	level.setText(Integer.toString((progressBarStatus/10)+1));
    	Bar.setProgress(progressBarStatus%10); //dépend du nombre de films regardé
    	
    	//badge
    	boolean[] badges = userStatResult.getBadgeAquired();
    	
    	if (badges[0])
    		badge_pirate.setImageResource(R.drawable.pirate2);
    	if (badges[1])
    		badge_star.setImageResource(R.drawable.starwars2);
    	if (badges[2])
    		badge_ring.setImageResource(R.drawable.anneau2);
    	if (badges[3])
    		badge_disney.setImageResource(R.drawable.disney2);
    	db.close();
	}//on create

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

}
