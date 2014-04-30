package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.ContentValues;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class ModifyProfile extends Activity {

	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	Button changeprofile=null;
	
	String newName = null;
	String newFirstname = null;
	String newAge = null;
	String newPswd = null;
	String confirmPswd = null;
	ContentValues newvalue = new ContentValues();
	EditText newNameET =null;
	EditText newFirstnameET = null;
	EditText newAgeET = null;
	EditText newPswdET = null;
	EditText confirmPswdET = null;
	dbAdapter mDbHelper = null;
	SQLiteDatabase md = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.change_profile);
		
		changeprofile = (Button) findViewById(R.id.new_user_creationButton);
	
		changeprofile.setOnClickListener(listenernewprofile);
		
		//création des strings contenant les nouvelles données
		newNameET = (EditText) findViewById(R.id.new_user_entername);
		newFirstnameET = (EditText) findViewById(R.id.new_user_enterfirstname);
		newAgeET = (EditText) findViewById(R.id.new_user_enterage);
		newPswdET = (EditText) findViewById(R.id.new_user_enterpassword);
		confirmPswdET = (EditText) findViewById(R.id.new_user_enterconfirmation);
		
		mDbHelper = new dbAdapter(this);  

		mDbHelper.createDatabase();       
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	
	private OnClickListener listenernewprofile = new OnClickListener() {
		
		@Override
		
		public void onClick(View v) {
			newName = newNameET.getText().toString();
			newFirstname = newFirstnameET.getText().toString();
			toaster(newFirstname);
			newAge = newAgeET.getText().toString();
			newPswd = newPswdET.getText().toString();
			confirmPswd = confirmPswdET.getText().toString();

			
			if(confirmPswd.compareTo(newPswd)==0){
	    
		    	mDbHelper.open(); 
		    	//rowid as _id,		
		    	//mDbHelper.execSQL("UPDATE \"main\".User SET \"FirstName\" = ?, \"Name\" = ?, \"Password\" = ?, \"Age\" = ? WHERE \"Login\" =?", new String[] {newFirstname, newName, newPswd, newAge, "Girl1"});
		    	//mDbHelper.execSQL("UPDATE main.User SET Password = ? WHERE  Login = ?", new String[] {"kkkj", "Girl1"});
		    	
		    	newvalue.put("FirstName" , newFirstname);
			    newvalue.put("Name" , newName);
			    newvalue.put("Age" , newAge);
			    newvalue.put("Password" , newPswd);
			    
			    //md.update("User", newvalue,  "login + = ?", new String[] {"Girl1"});
			    mDbHelper.update("User", newvalue,  "Login = ?", new String[] {"Girl1"});			    	
		    	
		    	mDbHelper.close();
		    	toaster("succes!!!");
			}
			else{
				toaster("password confirmation not the same");
				Intent Activity2 = new Intent(ModifyProfile.this, ModifyProfile.class);
				startActivity(Activity2);
			}
			
			Intent Activity2 = new Intent(ModifyProfile.this, Accueil.class);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
		}
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);	
	}
	
}
