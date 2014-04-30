package com.example.what2watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Search_By extends Activity {

	public void toaster(String txt){
		Toast.makeText(Search_By.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	private Button Go=null;
	private EditText Text=null;
	private RadioGroup Radio=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.search_by);
		
		Go = (Button) findViewById(R.id.search_by_Bouton);
		Text = (EditText) findViewById(R.id.search_by_EditText1);
		Radio = (RadioGroup) findViewById(R.id.search_by_RadioG1);
		
		Go.setOnClickListener(Blistener);
		
	}

	private OnClickListener Blistener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String requete = getSplitEntreeSQL(Text.getText().toString());
			if (Radio.getCheckedRadioButtonId() == R.id.search_by_Acteur) {
				searchActor(requete);
			}
			if (Radio.getCheckedRadioButtonId() == R.id.search_by_Director) {
				searchDirector(requete);
			}
			if (Radio.getCheckedRadioButtonId() == R.id.search_by_Title) {
				searchTitle(requete);
			}
		}
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
	
	public static String getSplitEntreeSQL(String entree) {
		String[] str = entree.split(" ");
		String toReturn = "%";
		for(int i=0; i< str.length; i++) {
			toReturn = toReturn + str[i] + "%";
		}
		return (toReturn);
	}
	public void searchActor(String entree)	
	{
		String queryString  = "SELECT M.rowid as _id, Name,  M.ID FROM Movie M, Actor A WHERE M.ID=A.ID and A.Name LIKE ? GROUP BY Name ORDER BY Name ASC";
		String[] display = {"Name"};
		String[] whereArgs = new String[] {String.valueOf(entree)};
		send(queryString, whereArgs, display, "Actor");
	}
	
	public void searchDirector(String entree)
	{
		String queryString  = "SELECT M.rowid as _id, Name,  M.ID FROM Movie M, Director D WHERE M.ID=D.ID and D.Name LIKE ? GROUP BY Name ORDER BY Name ASC" ;
		String[] display = {"Name"};
		String[] whereArgs = new String[] {String.valueOf(entree)};
		send(queryString, whereArgs, display, "Director");
	}
	
	public void searchTitle(String entree)
	{
		String queryString  = "SELECT rowid as _id, Title, Year, ID FROM Movie WHERE Title LIKE ? ORDER BY Title ASC";				  
		String[] display = {"Title","Year"};
		String[] whereArgs = new String[] {"%"+String.valueOf(entree)+"%"};
		send(queryString, whereArgs, display, "Title");
	}
	
	public void send(String query, String[] args, String[] WhatToDisplay, String search_by)
	{
		Intent intent = new Intent(Search_By.this, List_of_request.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("requete", query);
		bundle.putStringArray("arguments", args);
		bundle.putStringArray("display", WhatToDisplay);
		bundle.putString("search_by", search_by);
		intent.putExtras(bundle);
		
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
	}

}