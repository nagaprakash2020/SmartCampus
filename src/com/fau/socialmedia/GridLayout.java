package com.fau.socialmedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.fau.socialmedia.maps.UserCount;

public class GridLayout extends Activity{

	String username,password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		Log.d("Started","GridLayout");
		setContentView(R.layout.grid_layout);
		
		GridView grid=(GridView) findViewById(R.id.grid_view);
		grid.setAdapter(new ImageAdapter(this));		
		
		grid.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
				switch(position)
				{
				
				case 0:	Intent chat=new Intent(getApplicationContext(),com.beem.project.beem.ui.Login.class);
						chat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    					startActivity(chat);
    					break;
				case 1:	Intent directory=new Intent(getApplicationContext(),Add_Read_Contact.class);
						startActivity(directory);
						break;
				case 2:	Intent events=new Intent(getApplicationContext(),Events.class);
						startActivity(events);
						break;
				case 3:	Intent announcements=new Intent(getApplicationContext(),Announcements.class);
						announcements.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(announcements);
						break;
				case 4: Intent maps=new Intent(getApplicationContext(),Maps.class);
						startActivity(maps);
						break;
				case 5:Intent locateUserOnMap=new Intent(getApplicationContext(),UserCount.class);
						startActivity(locateUserOnMap);
						break;
				case 6:Intent studentLife=new Intent(getApplicationContext(),StudentLife.class);
						startActivity(studentLife);
				}
				
			}
			
		});
		
	}
	
}
