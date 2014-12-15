package com.gkbhitech.drishti.model;

public class OrderSummary implements JsonDataMarker{

private long order_no;
private long order_date;
private int status_code;
private String cust_refno;
public long getOrder_no() {
	return order_no;
}
public void setOrder_no(long order_no) {
	this.order_no = order_no;
}
public long getOrder_date() {
	return order_date;
}
public void setOrder_date(long order_date) {
	this.order_date = order_date;
}
public int getStatus_code() {
	return status_code;
}
public void setStatus_code(int status_code) {
	this.status_code = status_code;
}
public String getCust_refno() {
	return cust_refno;
}
public void setCust_refno(String cust_refno) {
	this.cust_refno = cust_refno;
}
}
