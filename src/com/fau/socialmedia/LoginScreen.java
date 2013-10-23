package com.fau.socialmedia;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.fau.socialmedia.backgroundService.LocationService;
import com.fau.socialmedia.master.MasterFile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginScreen extends Activity{

	Thread LoginThread;
	String response,pass,username;
	Intent registerClass;
	TextView loginError,registerScreen;
	EditText userid,password;
	ProgressDialog prog;
	Context context;
	Button login;
	
	
	@Override
	protected void onResume() {

		loginError.setVisibility(View.GONE);
		super.onResume();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		initialize();
		
		//Login
		login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				username=userid.getText().toString();
				new validateLogin().execute();
				Log.d("Login Clicked","started");
				Log.d("Username OnClick",username);
				// Set the username To masterFile
				MasterFile mf=new MasterFile(context);
				mf.prefs.edit().putString(MasterFile.KEY_USER, username).commit();
				pass=password.getText().toString();
				configureAccount();
				/*
				 * Below Intent start the background service
				 */
				 Intent mServiceIntent = new Intent(context, LocationService.class);
				    startService(mServiceIntent);
				    
				    if(startService(mServiceIntent)!=null){
				    	Log.d("Service","Yippe Started");
				    }else{
				    	Log.d("Service","Not Started");
				    }
			}
		});
		
		//Registration
		registerScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			Intent register=new Intent(LoginScreen.this,Register.class);
			startActivity(register);
			}
		});
		
	}
	private void initialize(){
		registerScreen=(TextView) findViewById(R.id.link_to_register);
		login=(Button) findViewById(R.id.btnLogin);
		userid=(EditText) findViewById(R.id.userid);
		password=(EditText) findViewById(R.id.password);
		loginError=(TextView) findViewById(R.id.loginError);
		context=this;
		prog = new ProgressDialog(context);
		loginError.setVisibility(View.GONE);
	}
	
	 private void configureAccount() {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor edit = settings.edit();
			edit.putString(BeemApplication.ACCOUNT_USERNAME_KEY, userid.getText().toString());
			edit.putString(BeemApplication.ACCOUNT_PASSWORD_KEY, password.getText().toString());
			edit.commit();
		    }
	 
	 private class validateLogin extends AsyncTask<Void, Void, String>{

		 
		 
		@Override
		protected void onPostExecute(String result) {
			prog.dismiss();
			if(result.equalsIgnoreCase("success")){
				Intent i=new Intent(LoginScreen.this,GridLayout.class);
				i.putExtra("USERNAME", username);
				i.putExtra("PASSWORD", pass);
				startActivity(i);
			}if(result.equals("username password did not matched")){
				loginError.setVisibility(View.VISIBLE);
				loginError.setText("username password did not matched");
			}if(result.equals("No username exist")){
				loginError.setVisibility(View.VISIBLE);
				loginError.setText("No username exist");
			}
		}

		@Override
		protected void onPreExecute() {
			prog.setTitle("Please Wait");
			prog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			String response;
			try{
				   final String NAMESPACE = "http://soap.smartcampus.com/";
				   final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				   final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				   final String METHOD_NAME = "validateLogin";
				   SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				   request.addProperty("arg0",username);
				   request.addProperty("arg1",password.getText().toString());
				   Log.d("Request",request.toString());
				   	 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			         envelope.setOutputSoapObject(request);
			         HttpTransportSE ht = new HttpTransportSE(URL);
			         ht.call(SOAP_ACTION, envelope);
			          response=	envelope.getResponse().toString();
			         Log.d("Response",response);
				   
			}catch(Exception e){
				Log.d("Exception in Login",e.toString());
				return "failedlogin";
			}
			return response;
		}
		 
	 }

	
}
