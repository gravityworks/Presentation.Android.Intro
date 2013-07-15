package com.gravityworks.persistingdata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sharedPreference();
		
		try {
			internalStorage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void sharedPreference(){
		String pref_userName = "UserName";
		
		savePreference(pref_userName);
		getPreference(pref_userName);
	}
	
	private void savePreference(String preferenceName) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		Editor editor = settings.edit();
		editor.putString(preferenceName, "jmcwheter");
		editor.commit();
	}
	
	private void getPreference(String preferenceName) {
		SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	
		String userName = settings.getString(preferenceName, "Default");
	}

	private void internalStorage() throws IOException {
		String fileName = String.valueOf(System.currentTimeMillis());
		String fileContents = "hello world!";		// data/com.gravityworks.perstingdata/

		// write the file
		FileOutputStream fos;
		fos = openFileOutput(fileName, Context.MODE_PRIVATE);
		fos.write(fileContents.getBytes());
		fos.close();
		
		// read the file
		FileInputStream fis;
		fis = openFileInput(fileName);
		StringBuffer fileContent = new StringBuffer("");

		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer)) != -1) {
		    fileContent.append(new String(buffer));
		}
	}
}
