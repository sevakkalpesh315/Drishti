package com.gkbhitech.drishti.model;

public class LensSpecification implements JsonDataMarker {
	double sph;
	double cyl;
	int axis;
	double prism;
	
	int brand;
	String otyp;
	double pwr;
	String grp;
	String tp;
	String ct;
	public LensSpecification(){
		
	}
	public LensSpecification(double sph,double cyl, int axis, double prism,int brand,String otyp){
		this.sph=sph;
		this.cyl=cyl;
		this.axis=axis;
		this.prism=prism;
		this.brand=brand;
		this.otyp=otyp;
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
	public double getPrism() {
		return prism;
	}
	public void setPrism(double prism) {
		this.prism = prism;
	}
	public int getBrand() {
		return brand;
	}
	public void setBrand(int brand) {
		this.brand = brand;
	}
	public String getOtyp() {
		return otyp;
	}
	public void setOtyp(String otyp) {
		this.otyp = otyp;
	}
	public double getPwr() {
		return pwr;
	}
	public void setPwr(double pwr) {
		this.pwr = pwr;
	}
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}	
}
