package com.example.what2watch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
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
			toaster("Bouton");
		}
	};
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
	

}
