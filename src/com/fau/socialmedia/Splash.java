package com.fau.socialmedia;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Splash extends Activity{
		@Override
    	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);        
        Thread t=new Thread(){
        	public void run()
        	{
        		try{
        		sleep(3000);
        		Intent i=new Intent(getApplicationContext(),LoginScreen.class);
    			startActivity(i);
        		}
        		catch(InterruptedException e)
        		{
        			e.printStackTrace();
        		}
        		finally{
        			finish();
        		}
        	}
        };
        t.start();
    }    
}
