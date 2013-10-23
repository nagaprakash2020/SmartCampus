package com.fau.socialmedia.master;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MasterFile {
	
	public SharedPreferences prefs;
	public Editor editor;
	Context context;
	String username,latitude,longitude;
	//Shared Pref Mode
	int PRIVATE_MODE=0;
	
	//Shared Pref File Name
	String PREF_NAME="FauSocialMedia";
	
	// All Shared Preferences Keys
	public static final String IS_LOGIN = "IsLoggedIn";
	public static final String KEY_USER = "username";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_TIME="time";
	public static final String KEY_LONGITUDE= "longitude";
	public static final String KEY_PLACE="place";
	
	// Constructor
    public MasterFile(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor   editor = prefs.edit();
        editor.putString(KEY_TIME, getTime());
        editor.commit();
        //Delete this method Calling
        //This is only for Testing
        
       // insertValues();
        
    }
    
    public void insertValues(){
    	editor.putString(KEY_USER, "prakash12");
    	
    }
    
    private String getTime() {
		Calendar c = Calendar.getInstance();

		int Hr24 = c.get(Calendar.HOUR_OF_DAY);
		int Min = c.get(Calendar.MINUTE);
		int Sec = c.get(Calendar.SECOND);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);

		String time = Hr24 + ":" + Min + ":" + Sec +"["+ month + "-" + day + "-"
				+ year+"]";
		return time;
	}
    
}
