package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public void toaster(String txt){
		Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	LinearLayout layout=null;
	Animation anim=null; 
	TextView text=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		
		text = (TextView) findViewById(R.id.welcome_layout_text);
		layout = (LinearLayout) findViewById(R.id.welcome_layout);
		
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent Activity2 = new Intent(MainActivity.this, Identification_user.class);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
				
				/*if qqqchose..
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setCancelable(false);
				dialog.setTitle("Do you want to create a new user ?");
				dialog.setMessage("no user detected. Would you like to create a new one ?");
				
				dialog.setPositiveButton("Yes", listenerYes);
				dialog.setNegativeButton("NO", listenerNo);
				
				dialog.show();*/
				
			}
		});
				
		//animation
		anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.twinkle);
		text.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				text.startAnimation(anim);
			}
			public void onAnimationRepeat(Animation animation) {
			// Que faire quand l'animation se répète ?
			}
			public void onAnimationStart(Animation animation) {
			// Que faire au premier lancement de l'animation ?
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//Boite de dialogue
	private OnClickListener listenerYes = new OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			toaster("Yes");
			//faire intent vers create user
		}		
	};
	
	private OnClickListener listenerNo = new OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			onBackPressed();
		}
	};

}
