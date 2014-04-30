package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Accueil extends Activity {
	
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	Button browse=null;
	Button search=null;
	Button discover=null;
	Button stats=null;
	Button user_param=null;
	Button disconnect=null;
	Button channel=null;
	Button cinema=null;
	User user = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.accueil);
		user = getIntent().getExtras().getParcelable("User");
		browse = (Button) findViewById(R.id.accueil_button_browse);
		search = (Button) findViewById(R.id.accueil_button_search);
		discover = (Button) findViewById(R.id.accueil_button_discover);
		stats = (Button) findViewById(R.id.accueil_button_stats);
		user_param = (Button) findViewById(R.id.accueil_button_parametre_user);
		disconnect = (Button) findViewById(R.id.accueil_button_disconnect);
		channel = (Button) findViewById(R.id.accueil_button_channel);
		cinema = (Button) findViewById(R.id.accueil_button_cinema);
		
		browse.setOnClickListener(listenerbrowse);
		search.setOnClickListener(listenersearch);
		discover.setOnClickListener(listenerdiscover);
		stats.setOnClickListener(listenerstats);
		user_param.setOnClickListener(listeneruser_param);
		disconnect.setOnClickListener(listeneruser_disconnect);
		channel.setOnClickListener(listenerchannel);
		cinema.setOnClickListener(listenercinema);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener listenerbrowse = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, Browse.class);
			/*Activity2.putExtra("User", user);*/
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	private OnClickListener listenersearch = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, Search_By.class);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			
		}
	};
	
	private OnClickListener listenerdiscover = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, Movietest.class);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	private OnClickListener listenerstats = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, UserStat_activity.class);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	private OnClickListener listeneruser_param = new OnClickListener() {
		@Override
		public void onClick(View v) {
			toaster("Bouton");
			Intent Activity2 = new Intent(Accueil.this, User_param.class);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	
	
	private OnClickListener listeneruser_disconnect = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			finish();
		}
	};
	
	private OnClickListener listenerchannel = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, Channel_option.class);
			Activity2.putExtra("User", user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
		
	private OnClickListener listenercinema = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Accueil.this, Cinema_option.class);
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