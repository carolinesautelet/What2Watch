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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserStat_activity extends Activity {

	public void toaster(String txt){
		Toast.makeText(UserStat_activity.this, txt, Toast.LENGTH_SHORT).show();
	}
	
	private ProgressBar Bar = null;
	private int progressBarStatus = 80;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.user_stat);
		
		Bar = (ProgressBar) findViewById(R.id.user_stat_ProgressBar);
		Bar.setProgress(progressBarStatus); //d�pend du nombre de films regard�
	
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}

}
