package com.fau.socialmedia.maps;

public class LatLonPair {

	private double mLatitude;
	private double mLongitude;
	private String mName;
	private String time;

	public LatLonPair(double latitude, double longitude,String name) {
		mLatitude = latitude;
		mLongitude = longitude;
		mName=name;
	}

	public static boolean isValid(double lat, double lon) {

		if ((lon >= -180 && lon <= 180) && (lat >= -90 && lat <= 90)) {
			return true;
		}

		return false;
	}

	public double getmLatitude() {
		return mLatitude;
	}
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setmLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}
	

}
