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
	
	Button continueb=null;
	EditText password = null;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.ask_pswd);
		user = getIntent().getExtras().getParcelable("User");
		continueb = (Button)findViewById(R.id.continue_button);
		password = (EditText)findViewById(R.id.continue_password);
		continueb.setOnClickListener(continueListener);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	private OnClickListener continueListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(user.getPassword().compareTo(password.getText().toString())==0){
				Intent Activity2 = new Intent(PasswordToContinue.this, ModifyProfile.class);
				Activity2.putExtra("User", user);
				startActivity(Activity2);
				overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
			}
			else{
				Toast.makeText(getBaseContext(), 
						"Incorrect Password", 
						Toast.LENGTH_SHORT).show(); 
			}
		}
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
}
