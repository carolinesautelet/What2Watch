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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class List_findCinema extends Activity{
	
	Context context = null;
	User user = null;
	List<String> names = null;
	public void toaster(String txt){
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.list_of_cinema);
	
	context = this.getApplicationContext();
	user = getIntent().getExtras().getParcelable("User");
	String id = getIntent().getExtras().getString("ID");

	CinemaSet myset = new CinemaSet(context);
	List<Cinema> findCinemaList = new ArrayList<Cinema>();
	myset.findCinema(id,findCinemaList);
	toaster("Numbe rof cinema : " + Integer.toString(findCinemaList.size()));
	names = new ArrayList<String>();
	myset.getNamesFromList(findCinemaList,names);

	ListView list = (ListView) findViewById(R.id.list_of_Cinema_Listview);
	if(names.size()>0){
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
	list.setAdapter(adapter);
	list.setOnItemClickListener(listenerListFindCinema);
	}
	else{
		names.add("Sorry , this film is not available ...");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
		list.setAdapter(adapter);
	}
	}
	
	private OnItemClickListener listenerListFindCinema = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {


		Intent intent = new Intent(List_findCinema.this, Cinema_Activity.class);
		intent.putExtra("User",user);
		intent.putExtra("Name",names.get(position));
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in1,R.anim.slide_out1);
	}

	
};

}
