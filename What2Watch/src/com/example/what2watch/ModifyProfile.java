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
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class ModifyProfile extends Activity {

	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}

	Button changeprofile=null;
	EditText newName =null;
	EditText newFirstname = null;
	EditText newAge = null;
	EditText newPswd = null;
	EditText confirmPswd = null;
	User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.change_profile);
		user = getIntent().getExtras().getParcelable("User");
		changeprofile = (Button) findViewById(R.id.new_user_creationButton);
		newName = (EditText) findViewById(R.id.new_user_entername);
		newFirstname = (EditText) findViewById(R.id.new_user_enterfirstname);
		newAge = (EditText) findViewById(R.id.new_user_enterage);
		newPswd = (EditText) findViewById(R.id.new_user_enterpassword);
		confirmPswd = (EditText) findViewById(R.id.new_user_enterconfirmation);
		changeprofile.setOnClickListener(listenernewprofile);

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

			if(newPswd.getText().toString().compareTo("")!=0){
				if(!user.setPassword(newPswd.getText().toString())){
					toaster("passwords do not match");
				}	
			}
			if(newName.getText().toString().compareTo("")!=0){
				user.setName(newName.getText().toString());
			}
			if(newFirstname.getText().toString().compareTo("")!=0){
				user.setFirstName(newFirstname.getText().toString());
			}
			if(newAge.getText().toString().compareTo("")!=0){
				user.setAge(newAge.getText().toString());
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
