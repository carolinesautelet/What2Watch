package com.example.what2watch;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
		TextView name = (TextView)findViewById(R.id.channel_activity_name);
		name.setText(nameChannel);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, allTitle);
		list.setAdapter(adapter);
		list.setOnItemClickListener(listenerListFindChannel);
		
	}
	private OnItemClickListener listenerListFindChannel = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Intent intent = new Intent(Channel_Activity.this, Movie_Activity.class);
			intent.putExtra("ID",allMovies.get(position).getId());
			intent.putExtra("User",user);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}
	};
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in2,R.anim.slide_out2);
	}
}
