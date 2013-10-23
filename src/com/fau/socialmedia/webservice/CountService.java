package com.fau.socialmedia.webservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class CountService {

	DefaultHttpClient httpClient=new DefaultHttpClient();
	private String retValue=null;
	HttpGet httpGet = null;
	public String BaseURL=null;
	HttpResponse response;
	
	/*
	 * This Method is used to call the web service 
	 * which returns the count at a perticular place 
	 * URL would be something like this http://localhost:8080/FAURestFul/rest/count/home
	 */
	
	public String getCount()
	{
		httpGet=new HttpGet(BaseURL);
		try
		{
			response=httpClient.execute(httpGet);
			HttpEntity  httpEntity = response.getEntity();
			retValue = EntityUtils.toString(httpEntity);
			Log.d("Result", retValue);
		}
		catch(Exception e)
		{
			Log.d("Exception Called",e.toString());
			
		}
		return retValue;
	}
	
}
