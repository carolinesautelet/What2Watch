package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Browse extends Activity {

	Button title=null;
	Button actor=null;
	Button director=null;
	Button action=null;
	Button adventure=null;
	Button animation=null;
	Button comedy=null;
	Button crime=null;
	Button drama=null;
	Button family=null;
	Button horror=null;
	Button musical=null;
	Button romance=null;
	Button scifi=null;
	Button thriller=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
        setContentView(R.layout.browse);
	
        title = (Button) findViewById(R.id.browse_button_title);
        actor =(Button)findViewById(R.id.browse_button_actor);
        director =(Button)findViewById(R.id.browse_button_director);
        action = (Button)findViewById(R.id.browse_button_action);
        adventure =(Button)findViewById(R.id.browse_button_adventure);
        animation =(Button)findViewById(R.id.browse_button_animation);
        comedy = (Button)findViewById(R.id.browse_button_comedy);
        crime = (Button)findViewById(R.id.browse_button_crime);
        drama=(Button)findViewById(R.id.browse_button_drama);
        family=(Button)findViewById(R.id.browse_button_family);
        horror=(Button)findViewById(R.id.browse_button_horror);
        musical =(Button)findViewById(R.id.browse_button_musical);
        romance =(Button)findViewById(R.id.browse_button_romance);
        scifi =(Button)findViewById(R.id.browse_button_scifi);
        thriller =(Button)findViewById(R.id.browse_button_thriller);       
           
               
        title.setOnClickListener(listenertitle);
        actor.setOnClickListener(listeneractor);
        director.setOnClickListener(listenerdirector);
        action.setOnClickListener(listeneraction);
        adventure.setOnClickListener(listeneradventure);
        animation.setOnClickListener(listeneranimation);
        comedy.setOnClickListener(listenercomedy);
        crime.setOnClickListener(listenercrime);
        drama.setOnClickListener(listenerdrama);
        family.setOnClickListener(listenerfamily);
        horror.setOnClickListener(listenerhorror);
        musical.setOnClickListener(listenermusical);
        romance.setOnClickListener(listenerromance);
        scifi.setOnClickListener(listenerscifi);
        thriller.setOnClickListener(listenerthriller);
	}


	private OnClickListener listenertitle = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Title, Year, ID FROM Movie ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Title");

		}
	};
	
	private OnClickListener listeneractor = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Name, ID FROM Actor GROUP BY Name ORDER BY Name ASC" ;
			String[] display = {"Name"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Actor");
		}
	};
	
	private OnClickListener listenerdirector = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT rowid as _id, Name, ID FROM Director GROUP BY Name ORDER BY Name ASC";
			String[] display = {"Name"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Director");
		}
	};
	private OnClickListener listeneraction = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Action\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Action");
		}
	};
	private OnClickListener listeneradventure = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Adventure\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Adventure");
		}
	};
	private OnClickListener listeneranimation = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Animation\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Animation");
		}
	};
	private OnClickListener listenercomedy = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Comedy\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Comedy");
		}
	};
	private OnClickListener listenercrime = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Crime\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Crime");
		}
	};
	private OnClickListener listenerdrama = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Drama\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Drama");
		}
	};
	private OnClickListener listenerfamily = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Family\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Family");
		}
	};
	private OnClickListener listenerhorror = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Horror\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Horror");
		}
	};
	private OnClickListener listenermusical = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Musical\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Musical");
		}
	};
	private OnClickListener listenerromance = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Romance\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Romance");
		}
	};
	private OnClickListener listenerscifi = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Scifi\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Scifi");
		}
	};
	private OnClickListener listenerthriller = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String queryString  = "SELECT M.rowid as _id, Title, Year, M.ID FROM Movie M, Genre G WHERE M.ID=G.ID and G.GenreName=\"Thriller\" ORDER BY Title ASC";
			String[] display = {"Title","Year"};
			String[] whereArgs = null;
			send(queryString, whereArgs, display, "Thriller");
		}
	};
	
	
	public void send(String query, String[] args, String[] WhatToDisplay, String browse_by)
	{
		Intent intent = new Intent(Browse.this, List_of_request.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("requete", query);
		bundle.putStringArray("arguments", args);
		bundle.putStringArray("display", WhatToDisplay);
		bundle.putString("search_by", browse_by);
		intent.putExtras(bundle);
		
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
	}
	
	
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

}