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
	String pswdstring=null;
	boolean checkpswd = false;
	//ListView List=null;
	EditText pswd = null;
	Cursor data = null;
	dbAdapter mDbHelper = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.ask_pswd);
		
		askpswd = (Button) findViewById(R.id.continue_button);
	
		askpswd.setOnClickListener(listeneraskpswd);
		
		mDbHelper = new dbAdapter(this);   
		mDbHelper.createDatabase();       
    	
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
			
			toaster(pswdstring);
				
			//List = (ListView) findViewById(R.id.movietest_L);
			
	    	
	    	//int i = 0;
	    	/*for(data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
	    		dbpswd = getString(i);
	    		i++;
				if(pswdstring==dbpswd){checkpswd=true;}//le mot de passe est valide
	    		}
	    	*/
	    	mDbHelper.open();     	

	    	dbpswd = mDbHelper.getStringFromRequest("SELECT rowid as _id, Password FROM User WHERE Login = ?", new String[] {"Girl1"}, "Password");
	    	mDbHelper.close();			//end db access
	    		
	    	if (dbpswd == null){
	    		toaster("null");
	    	}
	    	if (dbpswd != null){
	    		toaster(dbpswd);
	    	}
	    	
			pswdstring = pswd.getText().toString();

	    		
	    	if(pswdstring.compareTo(dbpswd)==0){checkpswd=true;}//le mot de passe est valide
			
	    	if(checkpswd){//check if entered pswd is correct before the next class
				Intent Activity2 = new Intent(PasswordToContinue.this, ModifyProfile.class);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			if(!checkpswd){
				Intent Activity2 = new Intent(PasswordToContinue.this, PasswordToContinue.class);
				toaster("passwordfailure");
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
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
