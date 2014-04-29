package com.example.what2watch;

import com.example.what2watch.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Create_user extends Activity implements OnItemSelectedListener{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_user);
		Spinner spinner = (Spinner) findViewById(R.id.create_user_recovery_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		 ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.recovery_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		EditText name = (EditText)findViewById(R.id.create_user_entername);
		EditText firstname = (EditText)findViewById(R.id.create_user_enterfirstname);
		EditText age = (EditText)findViewById(R.id.create_user_enterage);
		

	}
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
