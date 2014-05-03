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

public class List_findChannel extends Activity{
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
	setContentView(R.layout.list_of_channels);
	
	context = this.getApplicationContext();
	user = getIntent().getExtras().getParcelable("User");
	String id = getIntent().getExtras().getString("ID");
	ListView list = (ListView)findViewById(R.id.list_of_channels_Listview);
	ChannelSet myset = new ChannelSet(context);
	List<Channel> findChannelList = new ArrayList<Channel>();
	myset.findChannel(id,findChannelList);
	toaster("Numbe of channels : " + Integer.toString(findChannelList.size()));
	names = new ArrayList<String>();
	myset.getNamesFromList(findChannelList,names);

	if(names.size()>0){
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
	list.setAdapter(adapter);
	list.setOnItemClickListener(listenerListFindChannel);
	}
	else{
		names.add("Sorry , this film is not available ...");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
		list.setAdapter(adapter);
	}
	
	}
	
	private OnItemClickListener listenerListFindChannel = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {


		Intent intent = new Intent(List_findChannel.this, Channel_Activity.class);
		intent.putExtra("User",user);
		intent.putExtra("Name",names.get(position));
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
