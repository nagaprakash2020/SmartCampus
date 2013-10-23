package com.fau.socialmedia.backgroundService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.fau.socialmedia.master.MasterFile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {

	double latitude, longitude;
	String mUsername, mTime;
	MasterFile mf;

	@Override
	public void onReceive(final Context context, final Intent calledIntent) {
		
		latitude = calledIntent.getDoubleExtra(MasterFile.KEY_LATITUDE, -1);
		longitude = calledIntent.getDoubleExtra(MasterFile.KEY_LONGITUDE, -1);
		// Sending Latitude and Longitude to Master File
		mf=new MasterFile(context);
		mf.prefs.edit().putString(MasterFile.KEY_LATITUDE, String.valueOf(latitude)).commit();
		mf.prefs.edit().putString(MasterFile.KEY_LONGITUDE, String.valueOf(longitude)).commit();
		getMasterFileDetails(latitude, longitude);
		new SubmitLocation().execute();

	}

	private void getMasterFileDetails(final double latitude, final double longitude) {

		// Get Username and time from master File
		mUsername = mf.prefs.getString(MasterFile.KEY_USER, "default");
		mTime = mf.prefs.getString(MasterFile.KEY_TIME, "0:0:0");

	}

	private class SubmitLocation extends AsyncTask<Void, Integer, Void> {

		String response;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				final String NAMESPACE = "http://soap.smartcampus.com/";
				final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				final String METHOD_NAME = "insertLocation";

				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0", mUsername);
				request.addProperty("arg1", String.valueOf(latitude));
				request.addProperty("arg2", String.valueOf(longitude));
				request.addProperty("arg3", mTime);
				Log.d("Request", request.toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				ht.call(SOAP_ACTION, envelope);
				response = envelope.getResponse().toString();
				Log.d("Response", response);

			} catch (Exception e) {
				Log.d("Exception in Login", e.toString());
			}
			return null;
		}

	}

}
