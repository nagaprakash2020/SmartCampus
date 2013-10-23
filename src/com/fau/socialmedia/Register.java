package com.fau.socialmedia;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {

	EditText phoneNumber, Email, password, first_name, last_name, address,
			userid;
	Button register;
	Spinner gender, user_type;
	ProgressDialog prog;
	Context context;
	public static final String HOST = "ec2-54-234-138-249.compute-1.amazonaws.com";
	public static final int PORT = 5222;
	public static final String SERVICE = "amazona-2r9gfi9";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		intializeFields();
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (save()) {
					Log.d("After Click", "Validated Successfully");
					new registerinDB().execute();
					
				} else {
					Log.d("After Click", "Not Validated");
				}
			}
		});
	}

	/*Register User in OpenFire Server
	 * directly via android without any servlets
	 * 
	 */
	public Boolean registerUserXMPP(String username, String password) {
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			ConnectionConfiguration connConfig = new ConnectionConfiguration(
					HOST, PORT, SERVICE);
			XMPPConnection connection = new XMPPConnection(connConfig);
			connection.connect();
			Log.d("Connection", "Connect");
			AccountManager mAccount = new AccountManager(connection);
			if (mAccount.supportsAccountCreation()) {
				mAccount.createAccount(username, password);
				Log.d("Account registration","Success");
				return true;
			}else{
				Log.d("Account","Don't Support Account Creation");
			}
		} catch (Exception e) {
			Log.d("Exception while Creating Account", e.toString());
		}
		return false;
	}

	private void intializeFields() {

		phoneNumber = (EditText) findViewById(R.id.phone_no);
		Email = (EditText) findViewById(R.id.mail_id);
		register = (Button) findViewById(R.id.btnRegister);
		gender = (Spinner) findViewById(R.id.gender);
		password = (EditText) findViewById(R.id.password);
		first_name = (EditText) findViewById(R.id.first_name);
		last_name = (EditText) findViewById(R.id.last_name);
		userid = (EditText) findViewById(R.id.user_id);
		user_type = (Spinner) findViewById(R.id.user_type);
		context = this;
		prog = new ProgressDialog(context);
	}

	protected boolean save() {
		Log.d("TAG", "save()");

		boolean saved = true;

		if (validated()) {
			Log.d("Validated", "Success");

		} else {
			saved = false;
		}

		return saved;
	}

	protected boolean validated() {
		Log.d("TAG", "validated()");

		boolean validated = true;
		if (!Validate.hasText(first_name)) {
			first_name.setError("First Name Required");
			validated = false;
		}

		if (!Validate.hasText(last_name)) {
			last_name.setError("Last Name Required");
			validated = false;
		}

		if (!Validate.hasText(userid)) {
			userid.setError("UserID Required");
			validated = false;
		}
		if (!Validate.isPhoneNumber(phoneNumber, true)) {
			phoneNumber.setError("xxxxxxxxxx");
			validated = false;
		}
		if (!Validate.isEmailAddress(Email, true)) {
			Email.setError("Email Format XXX@XX.com");
			validated = false;
		}
		if (!Validate.isPassword(password, true)) {
			password.setError("Must Be Minimum 6 Characters"
					+ "No Special Characters Allowed");
			validated = false;
		}
		return validated;
	}

	private class deleteUser extends AsyncTask<Void, Void, String>{

		String response=null;
		
		@Override
		protected void onPostExecute(String result) {
			prog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			prog.setTitle("Please Wait");
			prog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			try{
				final String NAMESPACE = "http://soap.smartcampus.com/";
				final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				final String METHOD_NAME = "deleteUser";
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0", userid.getText().toString());
				Log.d("Request", request.toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				ht.call(SOAP_ACTION, envelope);
				response = envelope.getResponse().toString();
				Log.d("Result", response);
			} catch (Exception e) {
				Log.d("Exception Caught", e.toString());
			}
			return "Failed";
		}
		
	}
	private class registerinDB extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String response = null;
			try {
				final String NAMESPACE = "http://soap.smartcampus.com/";
				final String URL = "http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/SmartSoapServices/LocationCatalogService?wsdl";
				final String SOAP_ACTION = "http://soap.smartcampus.com/LocationCatalogService";
				final String METHOD_NAME = "registerUser";

				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0", userid.getText().toString());
				request.addProperty("arg1", password.getText().toString());
				request.addProperty("arg2", phoneNumber.getText().toString());
				request.addProperty("arg3", Email.getText().toString());
				request.addProperty("arg4", first_name.getText().toString());
				request.addProperty("arg5", last_name.getText().toString());
				request.addProperty("arg6", "student");
				request.addProperty("arg7", "m");
				Log.d("Request", request.toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				ht.call(SOAP_ACTION, envelope);
				response = envelope.getResponse().toString();
				Log.d("Result", response);
			} catch (Exception e) {
				Log.d("Exception Caught", e.toString());
				return "Failed";
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			prog.dismiss();
			if (result.equalsIgnoreCase("Success")) {
				if(registerUserXMPP(userid.getText().toString(), password.getText().toString())){
				Intent loginScreen = new Intent(context, LoginScreen.class);
				startActivity(loginScreen);
				}else{
					new deleteUser().execute();
				}
				
			}
			if (result.equalsIgnoreCase("Please Choose Different Username")) {
				Toast.makeText(context, result, Toast.LENGTH_LONG).show();
				userid.setText("");
				password.setText("");
			}

		}

		@Override
		protected void onPreExecute() {
			prog.setTitle("Please Wait");
			prog.show();
		}
	}
}