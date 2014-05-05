package com.example.what2watch;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_of_Channel extends Activity {
	Context context = null;
	User user = null;
	List<String> names = null;
	ChannelSet set = null;
	boolean today = false;
	List<Movie> movies =null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.list_of_channels);

		context = this.getApplicationContext();
		user = getIntent().getExtras().getParcelable("User");
		today = getIntent().getExtras().getBoolean("Today");
		if(!today){
			set = new ChannelSet(context);
			names = new ArrayList<String>();
			names = set.getallName();
			ListView list = (ListView)findViewById(R.id.list_of_channels_Listview);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
			list.setAdapter(adapter);
			list.setOnItemClickListener(listenerList);
		}
		else{
			movies = new ArrayList<Movie>();
			names = new ArrayList<String>();
			set = new ChannelSet(context);
			set.playingToday(movies, names);
			List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> element;
			for(int i = 0;i<movies.size();i++){
				element = new HashMap<String, String>();
				element.put("text1", movies.get(i).getTitle());
				element.put("text2", names.get(i));
				liste.add(element);
			}
			ListAdapter adapter = new SimpleAdapter(this,liste,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
			ListView list = (ListView)findViewById(R.id.list_of_channels_Listview);
			list.setAdapter(adapter);
			list.setOnItemClickListener(listenerList);
		}
	}
	private OnItemClickListener listenerList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Intent Activity2 = new Intent(List_of_Channel.this, Channel_Activity.class);
			Activity2.putExtra("Name",names.get(position));
			Activity2.putExtra("User",user);
			startActivity(Activity2);
			overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
		}

	};
}
