package com.gravityworks.derbydata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DerbyDataActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        outputDerbyVixens();
        outputDerbyVixensGSON();
    }
    
    public String getRawJSON(){
    	String requestURL = "http://derbynames.gravityworksdesign.com/DerbyNamesService.svc/DerbyNames?$filter=League%20eq%20'Lansing%20Derby%20Vixens'";
    	StringBuilder surveyJSON = new StringBuilder();
    	
    	try {
			URL webRequest = new URL(requestURL);
			URLConnection tc = webRequest.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
 		
			Log.i("DerbyData", "getSurvey-before loading JSON");
	    	
			String currentLine = "";
			
			while ((currentLine = in.readLine()) != null) {
				surveyJSON.append(currentLine);
			}
		}
    	catch(Exception e) {
			Log.e("DerbyData", "Error getting data" + e.getMessage());
		}
    	
    	// return
    	return surveyJSON.toString();
    }
    
    public  void outputDerbyVixens() {
		
		Log.i("DerbyData", "getSurvey-Starting");
    		String surveyJSON = getRawJSON();
    	
		if (surveyJSON != "") {
			Log.i("DerbyData", "getSurvey-Have Data");
			ArrayList<DerbyName> derbyNames = getDerbyDataFromJSON(surveyJSON);
			
			for(DerbyName item : derbyNames ){
				Log.i("DerbyData", String.format("Name=%s: Number=%s: League=%s", item.Name, 
						item.Number, item.League));
			}
		}
    	
    	Log.i("DerbyData", "getSurvey-finished");
	}
    
    public void outputDerbyVixensGSON(){
    	Gson gson = new Gson();
    	String surveyJSON = getRawJSON(); //"{\"Results\": [{ \"DerbyNameId\":\"1\" , \"DerbyName\":\"Addie Mortem\", \"Number\":\"93\",\"League\":\"Lansing Derby Vixens\" }, { \"DerbyNameId\":\"2\" , \"DerbyName\":\"Ali Always\",\"Number\":\"69\",\"League\":\"Lansing Derby Vixens\" }, { \"DerbyNameId\":\"3\" , \"DerbyName\":\"Audrey Floorburn\",\"Number\":\"61\",\"League\":\"Lansing Derby Vixens\" }]}"; 	
    	TeamResults derbyNames =  gson.fromJson(surveyJSON, TeamResults.class);
    	
    	if (derbyNames != null){
			for(DerbyName item : derbyNames.Results ){
				Log.i("DerbyData", String.format("Name=%s: Number=%s: League=%s", item.Name, 
						item.Number, item.League));
			}	
    	}
    }
    
    public static ArrayList<DerbyName> getDerbyDataFromJSON(String surveyJSON) {
    	ArrayList<DerbyName> tmpRtn = new ArrayList<DerbyName>();
		
		Log.i("DerbyData", "getDerbyDataFromJSON-Starting");
		
		try {
			JSONObject fullJsonObject = new JSONObject(surveyJSON); 
			JSONArray jsonNames = fullJsonObject.getJSONArray("d");
			
			// loop through each json derby name
			for (int i = 0; i < jsonNames.length(); i++) {
				DerbyName derbyName = new DerbyName();
				
				JSONObject result = jsonNames.getJSONObject(i);
		
				derbyName.DerbyNameId = result.getString("DerbyNameId");
				derbyName.Name = result.getString("Name");
				derbyName.Number = result.getString("Number");
				derbyName.League = result.getString("League");
				
				tmpRtn.add(derbyName);
			}
		
		} catch (JSONException e) {
			Log.e("DerbyData", "getDerbyDataFromJSON-Error converting JSON to survey" + e.getMessage());
		} 
		
		Log.i("DerbyData", "getDerbyDataFromJSON-Finished");
		
		// return
		return tmpRtn;
	}

}