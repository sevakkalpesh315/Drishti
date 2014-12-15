package com.gkbhitech.drishti.model;

public class OrderAndPowerDetail implements JsonDataMarker{

	private int status_code;
	private int r_plant;
	private int s_plant;
	private String order_lcl_div;
	private int order_date;
	private int order_time;
	private String order_no;
	private String cust_code;
	private String cust_name;
	private String order_type;
	private String cust_refno;
	private String lens_code;
	private String description;
	private String coating_code;
	
	private String eye;
	private double sph;
	private double cyl;
	private int axis;
	private double addi;
	private double min_ct;
	private double min_et;
	private double crib_dia;
	
	private int frm_b;
	private int frm_a;
	private int frm_dbl;
	private Integer frm_shape;
	private double fitting_rate;
	private double tinting_rate;
	private String comments;
	private double extra_price;
	private String indiv_engr;
	
	private double total_amnt;
	private int currentDate;
	
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
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getExtra_price() {
		return extra_price;
	}
	public void setExtra_price(double extra_price) {
		this.extra_price = extra_price;
	}
	public double getAddi() {
		return addi;
	}
	public void setAddi(double addi) {
		this.addi = addi;
	}
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	public double getMin_ct() {
		return min_ct;
	}
	public void setMin_ct(double min_ct) {
		this.min_ct = min_ct;
	}
	public double getMin_et() {
		return min_et;
	}
	public void setMin_et(double min_et) {
		this.min_et = min_et;
	}
	public double getCrib_dia() {
		return crib_dia;
	}
	public void setCrib_dia(double crib_dia) {
		this.crib_dia = crib_dia;
	}
	public String getIndiv_engr() {
		return indiv_engr;
	}
	public void setIndiv_engr(String indiv_engr) {
		this.indiv_engr = indiv_engr;
	}
	public int getFrm_b() {
		return frm_b;
	}
	public void setFrm_b(int frm_b) {
		this.frm_b = frm_b;
	}
	public int getFrm_a() {
		return frm_a;
	}
	public void setFrm_a(int frm_a) {
		this.frm_a = frm_a;
	}
	public int getFrm_dbl() {
		return frm_dbl;
	}
	public void setFrm_dbl(int frm_dbl) {
		this.frm_dbl = frm_dbl;
	}
	public Integer getFrm_shape() {
		return frm_shape;
	}
	public void setFrm_shape(Integer frm_shape) {
		this.frm_shape = frm_shape;
	}
	public double getTotal_amnt() {
		return total_amnt;
	}
	public void setTotal_amnt(double total_amnt) {
		this.total_amnt = total_amnt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(int currentDate) {
		this.currentDate = currentDate;
	}
	
	/*
	 * private int urgent; private int status_code; private int route_id;
	 * private Integer reject_code; private int passed_from; private int
	 * passed_date; private int passed_time; private int id;
	 */
	
	

}
