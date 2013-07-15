package com.gravityworks.sensors;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SensorsMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensors_main);
	
		
		/* do this in onCreate */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        
        /* Use the LocationManager class to obtain GPS locations */
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);


	}

	 public void sayCheese(View view) {
		 Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         // request code

         startActivityForResult(cameraIntent, 4242);
	 }
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {
	     if( requestCode == 4242)
	     {
	     //  data.getExtras()
	         Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	     }
	     super.onActivityResult(requestCode, resultCode, data);
	 }
	
	private SensorManager mSensorManager;
    private Float mAccel; // acceleration apart from gravity
    private Float mAccelCurrent; // current acceleration including gravity
    private Float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {
         public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
         @Override
         public void onSensorChanged(SensorEvent se) {
              float x = se.values[0];
              float y = se.values[1];
              float z = se.values[2];

              mAccelLast = mAccelCurrent;
              mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
              
              float delta = mAccelCurrent - mAccelLast;
              mAccel = mAccel * 0.9f + delta; // perform low-cut filter 
              TextView tv = (TextView) findViewById(R.id.accelText);
              tv.setText(mAccel.toString());
         }  
    };

    @Override
    protected void onResume() {
         super.onResume();
         mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onStop() {
         mSensorManager.unregisterListener(mSensorListener);
         super.onStop();
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sensors_main, menu);
		return true;
	}

	public class MyLocationListener implements LocationListener
	{
	     @Override
	     public void onLocationChanged(Location loc) {
	          loc.getLatitude();
	          loc.getLongitude();

	          String Text = "My current location is: " + "\nLatitude = " + loc.getLatitude() + "\nLongitude = " + loc.getLongitude();
	          Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	     }

	     @Override
	     public void onProviderDisabled(String provider)
	     {
	         // Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT).show();
	     }

	     @Override
	     public void onProviderEnabled(String provider) {
	         // Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
	     }

	     @Override
	     public void onStatusChanged(String provider, int status, Bundle extras) {}
	}
}
