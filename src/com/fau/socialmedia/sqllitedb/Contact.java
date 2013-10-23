package com.fau.socialmedia.sqllitedb;

public class Contact {
	 //private variables
    int _id;
    String _name;
    String _phone_number;
    String _email_id;
    String _office_hour;
 
    // Empty constructor
    public Contact(){
 
    }
    // constructor
    public Contact(int id, String name, String _phone_number,String _email_id,String _office_hour){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email_id=_email_id;
        this._office_hour=_office_hour;
    }
 
    // constructor
    public Contact(String name, String _phone_number,String _email_id,String _office_hour){
        this._name = name;
        this._phone_number = _phone_number;
        this._email_id=_email_id;
        this._office_hour=_office_hour;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting name
    public String getName(){
        return this._name;
    }
 
    // setting name
    public void setName(String name){
        this._name = name;
    }
 
    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }
 
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
	public String get_email_id() {
		return this._email_id;
	}
	public void set_email_id(String _email_id) {
		this._email_id = _email_id;
	}
	public String get_office_hour() {
		return this._office_hour;
	}
	public void set_office_hour(String _office_hour) {
		this._office_hour = _office_hour;
	}
    
    
}