package com.fau.socialmedia.webservice;

public class GetCount {

	/*
	 * This Class will Give you the count
	 * of all the locations with genders
	 * 
	 */
	
	String LIBRARY="library";
	String ARENA="Arena";
	String GREENBUILDING="Green Building";
	String SOMETHING="Some Thing";
	 
	CountService CS =new CountService();
	
	public String HomeCount()
	{
		
		CS.BaseURL="http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/FAURestFul/rest/count/home";
		String result=CS.getCount();
		CS.BaseURL="http://ec2-54-234-138-249.compute-1.amazonaws.com:8080/FAURestFul/rest/count/home";
		return result;
		
	}
	
	public String LibraryCount()
	{
		
		CS.BaseURL+=LIBRARY;
		String result=CS.getCount();
		CS.BaseURL="http://ec2-54-234-138-249.compute-1.amazonaws.com/:8080/FAURestFul/rest/count/";
		return result;
	}
	
	public String ArenaCount()
	{
		CS.BaseURL+=ARENA;
		String result=CS.getCount();
		CS.BaseURL="http://localhost:8080/FAURestFul/rest/count/";
		return result;
	}
	
	//TODO  complete the rest of methods for greenbuilding and something
	
	
}
