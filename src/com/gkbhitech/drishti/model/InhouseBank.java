package com.gkbhitech.drishti.model;

public class InhouseBank {
	private String bank_desc;
	private String sap_gl;
	private int profit_center;
	private int id;
	
	public String getBank_desc() {
		return bank_desc;
	}
	public void setBank_desc(String bank_desc) {
		this.bank_desc = bank_desc;
	}
	public String getSap_gl() {
		return sap_gl;
	}
	public void setSap_gl(String sap_gl) {
		this.sap_gl = sap_gl;
	}
	public int getProfit_center() {
		return profit_center;
	}
	public void setProfit_center(int profit_center) {
		this.profit_center = profit_center;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
