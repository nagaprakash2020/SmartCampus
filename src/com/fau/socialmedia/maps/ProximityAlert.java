package com.fau.socialmedia.maps;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fau.socialmedia.master.*;

public class ProximityAlert extends BroadcastReceiver {

	public static final String EVENT_ID_INTENT_EXTRA = "EventIDIntentExtraKey";
	public String place;
	public static final String PREF_NAME = "FauSocialMedia";
	int PRIVATE_MODE = 0;
	public Editor editor;
	MasterFile mf;

	@Override
	public void onReceive(Context context, Intent intent) {

		mf = new MasterFile(context);

		long eventId = intent.getLongExtra(EVENT_ID_INTENT_EXTRA, -1);
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		place = intent.getStringExtra(MasterFile.KEY_PLACE);

		Log.d("Proximity Altert Intent Received", eventId + "");
		Boolean entering = intent.getBooleanExtra(key, false);

		Log.d("On Receive", "Started");

		if (entering) {
			Toast.makeText(context, "You are in" + place, Toast.LENGTH_SHORT)
					.show();
			Log.d("Entered", place);

			/*
			 * This method will call insertTimeIN method of Insert class in
			 * webservices
			 */
			new SubmitTimeIN().execute();
		} else {
			Toast.makeText(context, "You are leaving " + place,
					Toast.LENGTH_SHORT).show();
			Log.d("Leaving", place);
			/*
			 * This method will call insertTimeOUT method of Insert class in
			 * webservices
			 */
			new SubmitTimeOut().execute();
		}
	}

	private class SubmitTimeIN extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String response;
			try {
				final String NAMESPACE = "http://soap.smartcampus.com/";
				final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				final String METHOD_NAME = "userEnter";
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0",
						mf.prefs.getString(MasterFile.KEY_USER, "NoUser"));
				request.addProperty("arg1", place);
				request.addProperty("arg2",
						mf.prefs.getString(MasterFile.KEY_TIME, "0:0:0"));
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
				return null;
			}
			return null;
		}

	}

	private class SubmitTimeOut extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {
			String response;
			try {
				final String NAMESPACE = "http://soap.smartcampus.com/";
				final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				final String METHOD_NAME = "userExit";
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0",
						mf.prefs.getString(MasterFile.KEY_USER, "NoUser"));
				request.addProperty("arg1", place);
				request.addProperty("arg2",
						mf.prefs.getString(MasterFile.KEY_TIME, "0:0:0"));
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
				return null;
			}
			return null;
		}

	}
}
