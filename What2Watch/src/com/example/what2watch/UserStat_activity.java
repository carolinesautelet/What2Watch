package com.example.what2watch;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		badge_ring = (ImageView) findViewById(R.id.user_stat_imgZimmer);
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
    	
    	Cursor cursor;
    	String TextToSet;
   	
    	//nbrFilm
    	cursor = db.execSQL("SELECT rowid as _id, ID FROM NumberOfView WHERE Login=?", new String[] {user.getLogin()});
    	progressBarStatus=cursor.getCount();
    	TextToSet = Integer.toString(progressBarStatus);
    	nbrFilm.setText(TextToSet);
    	
    	
    	//nbrHour
    	cursor = db.execSQL("SELECT SUM(Duration) FROM Movie M, NumberOfView N WHERE Login = ? and N.ID = M.ID", new String[] {user.getLogin()});
		TextToSet = Double.toString(cursor.getCount()/60);
    	nbrHour.setText(TextToSet);
    	
    	//nbrRate
    	cursor = db.execSQL("SELECT ID FROM Rating  WHERE Login = ?", new String[] {user.getLogin()});
		TextToSet = Integer.toString(cursor.getCount());
    	nbrRate.setText(TextToSet);

    	//MostDir
    	
    	//MostAct
    	
    	//MostGenre
    	
    	//Progressbar and level
    	level.setText(Integer.toString(progressBarStatus/20));
    	Bar.setProgress(progressBarStatus%20); //dépend du nombre de films regardé
    	
    	
    	boolean[] badges = checkForBadge(user.getLogin());
    	
    	if (badges[0])
    		badge_pirate.setImageResource(R.drawable.pirate2);
    	if (badges[1])
    		badge_star.setImageResource(R.drawable.starwars2);
    	if (badges[2])
    		badge_ring.setImageResource(R.drawable.anneau2);
    	if (badges[3])
    		badge_disney.setImageResource(R.drawable.disney2);
    	db.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean[] checkForBadge(String UserName)
	{
		boolean[] badges = new boolean[4];
   	 
    	String requete_badge = "SELECT M.rowid as _id, Title, M.ID FROM Movie M, NumberOfView N WHERE Login = ? and N.ID = M.ID and M.Title like ?";
    	String requete_disney = "SELECT M.rowid as _id, Title, M.ID FROM Movie M, Genre G, NumberOfView N WHERE GenreName like \"disney\" and N.Login=? and M.ID=N.ID and M.ID=G.ID GROUP BY Title";
    	ArrayList args = new ArrayList();
    	args.add(new String[] {UserName, "Pirates of the Caribbean%"});
    	args.add(new String[] {UserName, "The Lord of the Rings%"});
    	args.add(new String[] {UserName, "Star Wars: Épisode%"});

    	Cursor cursor;
    	
    	db.open(); 
    	for(int i=0; i<3; i++){
    		cursor = db.execSQL(requete_badge,(String[]) args.get(i));
    		if(cursor.getCount()>=2){
    			badges[i] = true;
    		}
    		else{
    			badges[i] = false;
    		}
    	}
    	cursor = db.execSQL(requete_disney, new String[] {UserName});
    	if(cursor.getCount()>=2){
    		badges[3] = true;
    	}
    	else{
    		badges[3] = false;
    	}	
    	
    	db.close();
    	return badges;
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

}
