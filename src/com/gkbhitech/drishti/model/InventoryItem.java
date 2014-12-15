package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class InventoryItem {
private BigDecimal id;
private int plant;
private String mat_code;
private String sloc;
private int qty;
private int min_level;
private int ro_level;
private int movement_dt;
private int mrp;
public BigDecimal getId() {
	return id;
}
public void setId(BigDecimal id) {
	this.id = id;
}
public int getPlant() {
	return plant;
}
public void setPlant(int plant) {
	this.plant = plant;
}
public String getMat_code() {
	return mat_code;
}
public void setMat_code(String mat_code) {
	this.mat_code = mat_code;
}
public String getSloc() {
	return sloc;
}
public void setSloc(String sloc) {
	this.sloc = sloc;
}
public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}
public int getMin_level() {
	return min_level;
}
public void setMin_level(int min_level) {
	this.min_level = min_level;
}
public int getRo_level() {
	return ro_level;
}
public void setRo_level(int ro_level) {
	this.ro_level = ro_level;
}
public int getMovement_dt() {
	return movement_dt;
}
public void setMovement_dt(int movement_dt) {
	this.movement_dt = movement_dt;
}
public int getMrp() {
	return mrp;
}
public void setMrp(int mrp) {
	this.mrp = mrp;
}

}
