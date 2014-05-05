package com.example.what2watch;

import java.util.ArrayList;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.HashMap;
public class Channel_Activity extends Activity{
	Context context = null;
	User user = null;
	Channel channel = null;
	List<Movie> allMovies = null;
	ListView list = null;
	String selectedId = null;
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
		List<String> times = channel.getAllTime();
		List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> element;
		for(int i = 0;i<allTitle.size();i++){
			element = new HashMap<String, String>();
			element.put("text1", allTitle.get(i));
			element.put("text2", times.get(i));
			liste.add(element);
		}
		ListAdapter adapter = new SimpleAdapter(this,liste,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
		list.setAdapter(adapter);
		list.setOnItemClickListener(listenerListFindChannel);
		
		
		
	}
	private OnItemClickListener listenerListFindChannel = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			selectedId = allMovies.get(position).getId();
			Intent intent = new Intent(Channel_Activity.this, Movie_Activity.class);
			intent.putExtra("ID",selectedId);
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
