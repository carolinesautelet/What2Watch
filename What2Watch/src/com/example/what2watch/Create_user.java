package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Create_user extends Activity {
	EditText name = null;
	EditText firstName = null;
	EditText login = null;
	EditText password = null;
	EditText confirmation = null;
	EditText age = null;
	Spinner questionSelect = null;
	EditText answer = null;
	Button create = null;
	User user = null;
	dbAdapter db = null;
	String question = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.create_user);
		db = new dbAdapter(this);         
		db.createDatabase();       
		name = (EditText)findViewById(R.id.create_user_entername);
		firstName = (EditText)findViewById(R.id.create_user_enterfirstname);
		login = (EditText)findViewById(R.id.create_user_enterlogin);
		password = (EditText)findViewById(R.id.create_user_enterpassword);
		confirmation = (EditText)findViewById(R.id.create_user_enterconfirmation);
		answer = (EditText)findViewById(R.id.create_user_question);
		age = (EditText)findViewById(R.id.create_user_enterage);
		questionSelect = (Spinner)findViewById(R.id.create_user_recovery_spinner);
		create = (Button)findViewById(R.id.create_user_creationButton);
		create.setOnClickListener(createListener);
		
		questionSelect.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> a, View v, int position, long id) 
			{

				int index = questionSelect.getSelectedItemPosition();

				question = (String) questionSelect.getAdapter().getItem(position);
				Toast.makeText(getBaseContext(), 
						"You have selected question : " + question, 
						Toast.LENGTH_SHORT).show(); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
	}
	private OnClickListener createListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent Activity2 = new Intent(Create_user.this, Accueil.class);
			db.open();
			Cursor cursor = db.execSQL("SELECT Login FROM User WHERE Login = ?", new String[] {login.getText().toString()});
			if(cursor.moveToFirst()){
				Toast.makeText(getBaseContext(), 
						"Sorry login does already exit", 
						Toast.LENGTH_SHORT).show();
			}
			else{
				if(password.getText().toString().compareTo(confirmation.getText().toString())!=0){
					Toast.makeText(getBaseContext(), 
							"Passwords do not match", 
							Toast.LENGTH_SHORT).show();
				}
				else{
					user = new User(login.getText().toString(),name.getText().toString(),firstName.getText().toString(),Integer.parseInt(age.getText().toString()),password.getText().toString(),question,answer.getText().toString());
					db.addToDatabase("User", new String[] {"Login" , "FirstName" , "Name" ,"Password","Age"}, new String[] {user.getLogin(),user.getFirstName(),user.getName(),user.getPassword(),Integer.toString(user.getAge())});
					Toast.makeText(getBaseContext(), 
							"Inserted", 
							Toast.LENGTH_SHORT).show();
					Activity2.putExtra("User", user);
					startActivity(Activity2);
					overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
				}
			}
			db.close();
		}

	};


}