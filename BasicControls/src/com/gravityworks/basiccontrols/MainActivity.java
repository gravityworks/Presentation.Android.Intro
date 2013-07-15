package com.gravityworks.basiccontrols;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LoadListView();
		LoadSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void LoadListView()
	{
		
		String[] derbyvixensarray = getResources().getStringArray(R.array.derby_vixens_array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, derbyvixensarray);
		
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
	}
	private void LoadSpinner(){
		String[] derbyvixensarray = getResources().getStringArray(R.array.derby_vixens_array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, derbyvixensarray);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		
	}
}
