package com.fau.socialmedia.backgroundService;

import com.fau.socialmedia.master.MasterFile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

public class LocationService extends Service {

	LocationManager lm;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		addLocationListener();
	}

	private void addLocationListener() {

		Thread triggerService = new Thread(new Runnable(){
	        public void run(){
	            try{
	                Looper.prepare();//Initialise the current thread as a looper.
	                lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

	                Criteria c = new Criteria();

	                final String PROVIDER = lm.getBestProvider(c, true);

	                MyLocationListener MyLocationListener = new MyLocationListener();
	                lm.requestLocationUpdates(PROVIDER, 1000, 0, MyLocationListener);
	                Log.d("LOC_SERVICE-Trigger Service", "Service RUNNING!");
	                Looper.loop();
	            }catch(Exception ex){
	                ex.printStackTrace();
	            }
	        }
	    }, "LocationThread");
	    triggerService.start();
		
	}
	class MyLocationListener implements LocationListener
	{

		@Override
		public void onLocationChanged(Location location) {

			Log.d("Latitude-On Location Changed",Double.toString(location.getLatitude()));
			updateLocation(location);
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

			
		}
		
	}
	public void updateLocation(Location location) {

	    double latitude, longitude;

	    latitude = location.getLatitude();
	    longitude = location.getLongitude();

	    Intent filterRes = new Intent();
	    filterRes.setAction("prakash.example.locationbackground.intent.action.LOCATION");
	    filterRes.putExtra(MasterFile.KEY_LATITUDE, latitude);
	    filterRes.putExtra(MasterFile.KEY_LONGITUDE, longitude);
	    getApplicationContext().sendBroadcast(filterRes);
		
	}
	
}


