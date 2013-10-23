package com.fau.socialmedia;


import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xbill.DNS.Master;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.fau.socialmedia.mapinput.UserLocationPOJO;
import com.fau.socialmedia.master.MasterFile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends Activity{
	
	String latitude;
	String longitude;
	
	List<UserLocationPOJO> locationList;
	 ProgressDialog prog;
	 Context context;
	 GoogleMap googleMap;
	 BitmapDescriptor bitmapDescriptor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mapview);
		MasterFile mf=new MasterFile(this);
		latitude=mf.prefs.getString(MasterFile.KEY_LATITUDE, "0");
		longitude=mf.prefs.getString(MasterFile.KEY_LONGITUDE, "0");
		LatLng USER = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
		
		/*googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment))
		        .getMap();
		bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		context=this;
		prog = new ProgressDialog(context);
		if(googleMap==null){
			Log.d("NUll Pointer","NUll Pointer Found");	
		}else{
			Log.d("Success","Pandaga Chesuko");
		}
		
		googleMap.setMyLocationEnabled(true);
		//Center Map at current location
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER, 10));
		// Calling Async Task
		new getLocation().execute();
		
		//MapInput.addDummydata();
		//ArrayList<HashMap<String, String>> listofuser = MapInput.getList();
		 googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				Log.d("Marker Clicked","User1");
				
				final Intent intent = new Intent(Intent.ACTION_VIEW,
				          *//** Using the web based turn by turn directions url. *//*
				          Uri.parse(
				        		  "http://maps.google.com/maps?" +
				        	                "saddr="+USER.latitude+","+USER.longitude+
				        	                "&daddr="+marker.getPosition().latitude+","+marker.getPosition().longitude));
				                
				                intent.setClassName(
				                 "com.google.android.apps.maps",
				                 "com.google.android.maps.MapsActivity");
				          startActivity(intent);
			}
		});
		 googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
					Log.d("Marker Clicked","User1");
					marker.showInfoWindow();
					return false;
			}
		});
	   */
	}

	/**
	 * @param googleMap
	 * @param bitmapDescriptor
	 * 
	 * Call this method when you receive data from database and to
	 *  point the Markers on Google Map
	 */
	public void pinMap(GoogleMap googleMap, BitmapDescriptor bitmapDescriptor) {
		
		for(UserLocationPOJO userLocation:locationList)
		{
			String Name=userLocation.getUsername();
			Double lat=Double.parseDouble(userLocation.getLatitude());
			Double longi=Double.parseDouble(userLocation.getLongitude());
			String Time=userLocation.getTimeEntered();
			LatLng NewUser=new LatLng(lat,longi); 		
			
			googleMap.addMarker(new MarkerOptions().position(NewUser).title("Last Know Time:"+Time).icon(bitmapDescriptor).visible(true).draggable(true)
					.snippet(Name)).showInfoWindow();
			
		}
		
		/*googleMap.addMarker(new MarkerOptions().position(USER)
		        .title("This is where you are"));*/
	}
	
	 public class getLocation extends AsyncTask<Void, Integer, List<UserLocationPOJO>>{
		  
		  @Override
			protected List<UserLocationPOJO> doInBackground(Void... params) {
			  try{
				   final String NAMESPACE = "http://soap.smartcampus.com/";
				   final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				   final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				   final String METHOD_NAME = "getUserLocation";
				   
				  Log.d("Inside GetLocation","Working");
				  locationList=new ArrayList<UserLocationPOJO>();
			    	
			    	 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);         
			         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			         envelope.setOutputSoapObject(request);
			         HttpTransportSE ht = new HttpTransportSE(URL);
			         ht.call(SOAP_ACTION, envelope);
			         SoapObject response = (SoapObject) envelope.getResponse();
			         Log.d("Response",response.toString()+"null");
			         int locationCount=response.getPropertyCount();
			         for(int i=0;i<locationCount/2;i++){
			        	 
			        	 Object property=response.getProperty(i);
			        	 Log.d("Something",property.toString());
			        	 if(property instanceof SoapObject){
			        		 SoapObject location=(SoapObject) property;
			        		 UserLocationPOJO locationPOJO=new UserLocationPOJO();
			        		 locationPOJO.setUsername(location.getProperty("username").toString());
			        		 Log.d("Username",location.getProperty("username").toString());
			        		 locationPOJO.setLatitude(location.getProperty("latitude").toString());
			        		 locationPOJO.setLongitude(location.getProperty("longitude").toString());
			        		 locationPOJO.setTimeEntered(location.getProperty("timeEntered").toString());
			        		 locationList.add(locationPOJO);
			        		 Log.d("Entry",locationPOJO.toString());
			        	 }
			         }
			         return locationList;
			    }catch(Exception e){
			     e.printStackTrace();
			     Log.d("Exception IN Async of Maps",e.toString());
			     return null;
			    }
			}


		@Override
		protected void onPostExecute(List<UserLocationPOJO> result) {
			super.onPostExecute(result);
			locationList=result;
			prog.dismiss();
			pinMap(googleMap, bitmapDescriptor);
		}


		@Override
		protected void onPreExecute() {
			 prog.setTitle("Updating Map");
			 prog.show();
		}
		  
		
		  
	  }
	
}