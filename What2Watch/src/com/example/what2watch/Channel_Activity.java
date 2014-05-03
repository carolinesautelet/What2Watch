package com.example.what2watch;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Channel_Activity extends Activity{
	Context context = null;
	User user = null;
	Channel channel = null;
	List<Movie> allMovies = null;
	ListView list = null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.channel_activity);
		context=this.getApplicationContext();
		user = getIntent().getExtras().getParcelable("User");
		String nameChannel = getIntent().getExtras().getString("Name");
		channel = new Channel(this,nameChannel);
		list = (ListView)findViewById(R.id.channel_activity_programmation);
		allMovies = channel.getMovies();
		List<String> allTitle = channel.getAllMoviesTitle();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, allTitle);
		list.setAdapter(adapter);
		
	}

}
