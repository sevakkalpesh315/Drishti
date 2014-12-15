package com.gkbhitech.drishti.model;

public class Quote  implements JsonDataMarker{
private double rate ;
private int price_id;
private double stn_price;
private double fitting_rate;
private double tinting_rate;
public double getFitting_rate() {
	return fitting_rate;
}
public void setFitting_rate(double fitting_rate) {
	this.fitting_rate = fitting_rate;
}
public double getTinting_rate() {
	return tinting_rate;
}
public void setTinting_rate(double tinting_rate) {
	this.tinting_rate = tinting_rate;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public int getPrice_id() {
	return price_id;
}
public void setPrice_id(int price_id) {
	this.price_id = price_id;
}
public double getStn_price() {
	return stn_price;
}
public void setStn_price(double stn_price) {
	this.stn_price = stn_price;
}
}
