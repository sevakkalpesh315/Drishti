package com.gkbhitech.drishti.model;



public class Order implements JsonDataMarker {
private int r_plant;
private int s_plant;
private String order_lcl_div;
private int order_date;
private int order_time;
private String order_no;
private String cust_code;
private String order_type;
private String cust_refno;
private String lens_code;
private String coating_code;
private int urgent;
private int status_code;
private int route_id;
private Integer reject_code;
private int passed_from;
private int passed_date;
private int passed_time;
private int id;
private String comments;
private double fitting_rate;
private double tinting_rate;
private double extra_price;
private OrderDetail[] orderDetails;
public int getR_plant() {
	return r_plant;
}
public void setR_plant(int r_plant) {
	this.r_plant = r_plant;
}
public int getS_plant() {
	return s_plant;
}
public void setS_plant(int s_plant) {
	this.s_plant = s_plant;
}
public String getOrder_lcl_div() {
	return order_lcl_div;
}
public void setOrder_lcl_div(String order_lcl_div) {
	this.order_lcl_div = order_lcl_div;
}
public int getOrder_date() {
	return order_date;
}
public void setOrder_date(int order_date) {
	this.order_date = order_date;
}
public int getOrder_time() {
	return order_time;
}
public void setOrder_time(int order_time) {
	this.order_time = order_time;
}
public String getOrder_no() {
	return order_no;
}
public void setOrder_no(String order_no) {
	this.order_no = order_no;
}
public String getCust_code() {
	return cust_code;
}
public void setCust_code(String cust_code) {
	this.cust_code = cust_code;
}
public String getOrder_type() {
	return order_type;
}
public void setOrder_type(String order_type) {
	this.order_type = order_type;
}
public String getCust_refno() {
	return cust_refno;
}
public void setCust_refno(String cust_refno) {
	this.cust_refno = cust_refno;
}
public String getLens_code() {
	return lens_code;
}
public void setLens_code(String lens_code) {
	this.lens_code = lens_code;
}
public String getCoating_code() {
	return coating_code;
}
public void setCoating_code(String coating_code) {
	this.coating_code = coating_code;
}
public int getUrgent() {
	return urgent;
}
public void setUrgent(int urgent) {
	this.urgent = urgent;
}
public int getStatus_code() {
	return status_code;
}
public void setStatus_code(int status_code) {
	this.status_code = status_code;
}
public int getRoute_id() {
	return route_id;
}
public void setRoute_id(int route_id) {
	this.route_id = route_id;
}
public Integer getReject_code() {
	return reject_code;
}
public void setReject_code(Integer reject_code) {
	this.reject_code = reject_code;
}
public int getPassed_from() {
	return passed_from;
}
public void setPassed_from(int passed_from) {
	this.passed_from = passed_from;
}
public int getPassed_date() {
	return passed_date;
}
public void setPassed_date(int passed_date) {
	this.passed_date = passed_date;
}
public int getPassed_time() {
	return passed_time;
}
public void setPassed_time(int passed_time) {
	this.passed_time = passed_time;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public OrderDetail[] getOrderDetails() {
	return orderDetails;
}
public void setOrderDetails(OrderDetail[] orderDetails) {
	this.orderDetails = orderDetails;
}
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
public double getExtra_price() {
	return extra_price;
}
public void setExtra_price(double extra_price) {
	this.extra_price = extra_price;
}
}