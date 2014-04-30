package com.example.what2watch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

public class PasswordToContinue extends Activity {

	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	Button askpswd=null;
	String dbpswd=null;
	boolean checkpswd = false;
	//ListView List=null;
	EditText pswd = null;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.ask_pswd);
		user = getIntent().getExtras().getParcelable("User");
		dbpswd = user.getPassword();
		askpswd = (Button) findViewById(R.id.continue_button);
		askpswd.setOnClickListener(listeneraskpswd);     
		pswd = (EditText) findViewById(R.id.continue_password);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	private OnClickListener listeneraskpswd = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(pswd.getText().toString().compareTo(dbpswd)==0){checkpswd=true;}//le mot de passe est valide
			
	    	if(checkpswd){//check if entered pswd is correct before the next class
				Intent Activity2 = new Intent(PasswordToContinue.this, ModifyProfile.class);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			if(!checkpswd){
				Toast.makeText(getBaseContext(), 
						"Incorrect Password should be  an is " + dbpswd +pswd.getText().toString(), 
						Toast.LENGTH_SHORT).show(); 
			}
			
			//toaster("test");
		}
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
}
