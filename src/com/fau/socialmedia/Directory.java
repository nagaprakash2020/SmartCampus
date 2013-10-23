package com.fau.socialmedia;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Directory extends ListActivity{

	private String KEY_NAME="name";
	private String KEY_NUMBER="number";
	
	ArrayList<HashMap<String, String>> contactNames=new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.directory);
		
	    ListView contactsList=(ListView) findViewById(R.id.contactsList);
		
		
		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		while (phones.moveToNext())
		{
		  HashMap<String,String> contacts=new HashMap<String, String>();
		  String name  =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		  String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		  contacts.put(KEY_NAME, name);
		  contacts.put(KEY_NUMBER, number);
		  contactNames.add(contacts);
		  Log.d(name,number);

		}
		phones.close();
		
		String[] keys={KEY_NAME,KEY_NUMBER};
    	int[] ids={android.R.id.text1,android.R.id.text2};
    	
    	SimpleAdapter adapter=new SimpleAdapter(this, contactNames	, android.R.layout.simple_list_item_2	, keys, ids);
    	setListAdapter(adapter);
    	ListView list=getListView();
    	list.setOnItemClickListener(new OnItemClickListener()
    	{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				HashMap<String,String> map=contactNames.get(position);
				
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:"+map.get(KEY_NUMBER)));
				startActivity(callIntent);
				
			}
    		
    	});
    	
    	
	}

	
}
