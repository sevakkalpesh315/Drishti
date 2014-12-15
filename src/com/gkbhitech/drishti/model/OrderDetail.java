package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class OrderDetail implements JsonDataMarker{
private String eye;
private double sph;
private double cyl;
private int axis;
private BigDecimal order_no;

public BigDecimal getOrder_no() {
	return order_no;
}
public void setOrder_no(BigDecimal order_no) {
	this.order_no = order_no;
}
public String getEye() {
	return eye;
}
public void setEye(String eye) {
	this.eye = eye;
}
public double getSph() {
	return sph;
}
public void setSph(double sph) {
	this.sph = sph;
}
public double getCyl() {
	return cyl;
}
public void setCyl(double cyl) {
	this.cyl = cyl;
}
public int getAxis() {
	return axis;
}
public void setAxis(int axis) {
	this.axis = axis;
}

}
