package com.fau.socialmedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fau.socialmedia.sqllitedb.Contact;
import com.fau.socialmedia.sqllitedb.DatabaseHandler;

public class Add_Read_Contact extends ListActivity {

	private final String KEY_NAME="name";
	private final String KEY_OFFICE_HOUR="office_hour";
	private final String KEY_PHONE_NO="phone_no";
	private final String KEY_EMAIL="email_id";
	
	//get all contacts in list<hash>
	//This is just for simple adapter
	public ArrayList<HashMap<String,String>> all_Contacts =new ArrayList<HashMap<String,String>>();
	
	List<Contact> contacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add__read__contact);
		
		DatabaseHandler db = new DatabaseHandler(this);
		
		/**
         * CRUD Operations
         * */
        // Inserting Contacts
		
		
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000","ravi@gmail.om","9Am to 4 PM"));
        db.addContact(new Contact("Srinivas", "9199999999","ravi@gmail.om","9Am to 4 PM"));
        db.addContact(new Contact("Tommy", "9522222222","ravi@gmail.om","9Am to 4 PM"));
        db.addContact(new Contact("Karthik", "9533333333","ravi@gmail.om","9Am to 4 PM"));
 
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
       contacts = db.getAllContacts();      
 
        
        for (Contact cn : contacts) {
            HashMap<String,String> map=new HashMap<String, String>();
            map.put(KEY_NAME, cn.getName());
            map.put(KEY_PHONE_NO, cn.getPhoneNumber());
            map.put(KEY_OFFICE_HOUR, cn.get_office_hour());
            map.put(KEY_EMAIL, cn.get_email_id());
            all_Contacts.add(map);
            
    }
        String[] keys={KEY_NAME,KEY_OFFICE_HOUR};
        int[] ids={android.R.id.text1,android.R.id.text2};
        
        SimpleAdapter adapter=new SimpleAdapter(this,all_Contacts,android.R.layout.simple_list_item_2,keys,ids);
        setListAdapter(adapter);
        
        ListView list=getListView();
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Contact contact=contacts.get(position);
				Intent sending=new Intent(Add_Read_Contact.this,Sending.class);
				sending.putExtra(KEY_PHONE_NO, contact.getPhoneNumber());
				sending.putExtra(KEY_EMAIL, contact.get_email_id());
				startActivity(sending);
			}
        	
        	
        	
		});
        
    }
}
