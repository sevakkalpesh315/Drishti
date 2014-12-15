package com.gkbhitech.drishti.model;

public class GeoLocation implements JsonDataMarker {
private double latitude;
private double longitude;
public double getLatitude(){
	return this.latitude;
}
public double getLongitude(){
	return this.longitude;
}
public void setLatitude(double latitude){
	this.latitude=latitude;
}
public void setLongitude(double longitude){
	this.longitude=longitude;
}
public GeoLocation(){}
public GeoLocation(double latitude,double longitude){
	this.latitude=latitude;
	this.longitude=longitude;
}
}
