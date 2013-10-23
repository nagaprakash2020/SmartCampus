package com.fau.socialmedia.mapinput;

import java.util.ArrayList;
import java.util.HashMap;

public class MapInput {
	
	private static 	ArrayList<HashMap<String,String>> listofuser=new ArrayList<HashMap<String,String>>();
	
	
	
	public static void addDummydata()
	{
		fillMap(26.887233,-80.115341,"user1");
		fillMap(26.885779, -80.116478, "user2");
		fillMap(26.887042, -80.113860, "user3");
		fillMap(26.885377, -80.114697, "user4");
		
	}
	/*Get the object in array of json as 
	 * key values pairs of Username,Lat and Long 
	 * values and fill map with those values
	 */
	public static void fillMap(double lat,double longi,String uname)
	{
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("Username", uname);
		map.put("Latitude", Double.toString(lat));
		map.put("Longitude", Double.toString(longi));
		
		listofuser.add(map);
		
	}
	public static ArrayList<HashMap<String, String>> getList()
	{
		return listofuser;
		
	}
	
}
