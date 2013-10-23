package com.fau.socialmedia.maps;

import java.util.ArrayList;
import java.util.Calendar;

import com.fau.socialmedia.R;
import com.fau.socialmedia.master.MasterFile;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

public class UserCount extends Activity{

	private static final String PROXIMITY_INTENT_ACTION = new String(
			"com.freelancer.bars.action.PROXIMITY_ALERT");

	private IntentFilter mIntentFilter;
	private ArrayList<LatLonPair> mPositions;
	LocationManager locManager;
	public String provider;
	public Context mContext;
	LocationListener locationListener;
	BroadcastReceiver receiver;
	
	TextView fau_Male,fau_Female;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar);

		fau_Male=(TextView) findViewById(R.id.Fau_Male);
		fau_Female=(TextView) findViewById(R.id.Fau_Female);
		
		receiver=new ProximityAlert();
		mIntentFilter = new IntentFilter(PROXIMITY_INTENT_ACTION);
		Criteria criteria = new Criteria();
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    provider = locManager.getBestProvider(criteria, false);
		mContext=getApplicationContext();
	    
		locationListener=new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				registerIntents();
				Calendar c = Calendar.getInstance(); 
				int seconds = c.get(Calendar.SECOND);
				Log.d("Location Updated",Integer.toString(seconds));
				
			}
		};
		createPostions();
	
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		locManager.requestLocationUpdates(provider, 40, 0, locationListener);
		registerReceiver(receiver, mIntentFilter);
	}
	
	@Override
	protected void onStop() {
		unregisterReceiver(receiver);
		super.onStop();
		
	}

	@Override
	protected void onPause() {		
		super.onPause();
		locManager.removeUpdates(locationListener);
	}

	private void registerIntents() {
		Log.d("Register Intent","Called");
		for (int i = 0; i < mPositions.size(); i++) {
			LatLonPair latlon = mPositions.get(i);
			setProximityAlert(latlon.getmLatitude(),latlon.getmLongitude(),
					i+1,i,latlon.getmName());			
		}
	}
	
	//This method is fired when user enters any of the locations which were saved in mPositions lat longs

	private void setProximityAlert(double lat, double lon, final long eventID,
			int requestCode, String name) {
		// 10 Meter Radius
		float radius = 10000f;
		Log.d("Set proximity","Called");

		// Expiration in 10 Minutes(10mins * 60secs * 1000milliSecs)
		long expiration = 600000;

		Intent intent = new Intent(PROXIMITY_INTENT_ACTION);
		intent.putExtra(ProximityAlert.EVENT_ID_INTENT_EXTRA, eventID);
		intent.putExtra(MasterFile.KEY_PLACE,name );
    	PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode,intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	
    	locManager.addProximityAlert(lat, lon, radius, expiration, pendingIntent);
    	
	}

	
	// mPositions have all the latlongs of the places.
	private void createPostions() {
		mPositions = new ArrayList<LatLonPair>();
		Log.d("Create positions","Called");
		//mPositions.add(new LatLonPair(26.376237, -80.101647, "Stadium"));
		//Dummy Position
		mPositions.add(new LatLonPair(25.777252,-80.350598,"Home"));
		mPositions.add(new LatLonPair(26.372318, -80.109306, "Arena"));
		
		mPositions.add(new LatLonPair(26.371896, -80.104314, "Library"));
		mPositions.add(new LatLonPair(26.372857, -80.098022, "Green Building"));
		mPositions.add(new LatLonPair(33.210786, -87.564387, "Some Thing"));
	}
	
	
}
