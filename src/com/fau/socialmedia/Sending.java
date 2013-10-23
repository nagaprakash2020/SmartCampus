package com.fau.socialmedia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Sending extends Activity implements OnClickListener{

	
	/*
	 * This Class is used for sending messages,EMail 
	 * and for Calling from list
	 * 
	 */
	private final String KEY_PHONE_NO="phone_no";
	private final String KEY_EMAIL="email_id";
	private String Phone_Number;
	private String Email_Id;
	
	ImageButton EMAIL,CALL,SMS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sending_layout);
		
		Phone_Number=getIntent().getStringExtra(KEY_PHONE_NO);
		Email_Id=getIntent().getStringExtra(KEY_EMAIL);
		
		EMAIL=(ImageButton) findViewById(R.id.btn_email);
		CALL=(ImageButton) findViewById(R.id.btn_call);
		SMS=(ImageButton) findViewById(R.id.btn_sms);
		
		EMAIL.setOnClickListener(this);
		CALL.setOnClickListener(this);
		SMS.setOnClickListener(this);
		
	}
	
	private void Make_Phone_call()
	{
		String uri = "tel:" + Phone_Number.trim() ;
		 Intent intent = new Intent(Intent.ACTION_DIAL);
		 intent.setData(Uri.parse(uri));
		 startActivity(intent);
	}

	private void Send_Message()
	{
		
		Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:"+Phone_Number) );
		  startActivity( intent );
		
	}
	
	private void Send_Email()
	{
		  Intent email = new Intent(Intent.ACTION_SEND);
		  email.putExtra(Intent.EXTRA_EMAIL, new String[]{Email_Id});
		  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
		  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
		 // email.putExtra(Intent.EXTRA_SUBJECT, subject);
		  //email.putExtra(Intent.EXTRA_TEXT, message);

		  //need this to prompts email client only
		  email.setType("message/rfc822");

		  startActivity(Intent.createChooser(email, "Choose an Email client :"));
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.btn_call:
			Log.d("Clicked","Call");
			Make_Phone_call();
			break;
			
		case R.id.btn_email:
			Log.d("Clicked","EMAIL");
			Send_Email();
			break;
			
		case R.id.btn_sms:
			Log.d("Clicked","SMS");
			Send_Message();
			break;
		}
		
	}
	
}
