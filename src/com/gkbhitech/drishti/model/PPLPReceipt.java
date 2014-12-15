package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class PPLPReceipt implements JsonDataMarker{
	private BigDecimal id;
	private String cust_code;
	private String pplp_pk;
	private String sap_receipt_no;
	private int entered_date;
	private String payment_mode;
	private BigDecimal cheque_no;
	private int cheque_dt;
	private String bank_name;
	private String sold_by;
	private int pplp_reversed;
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getCust_code() {
		return cust_code;
	}
	public void setCust_code(String cust_code) {
		this.cust_code = cust_code;
	}
	public String getPplp_pk() {
		return pplp_pk;
	}
	public void setPplp_pk(String pplp_pk) {
		this.pplp_pk = pplp_pk;
	}
	public String getSap_receipt_no() {
		return sap_receipt_no;
	}
	public void setSap_receipt_no(String sap_receipt_no) {
		this.sap_receipt_no = sap_receipt_no;
	}
	public int getEntered_date() {
		return entered_date;
	}
	public void setEntered_date(int entered_date) {
		this.entered_date = entered_date;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public BigDecimal getCheque_no() {
		return cheque_no;
	}
	public void setCheque_no(BigDecimal cheque_no) {
		this.cheque_no = cheque_no;
	}
	public int getCheque_dt() {
		return cheque_dt;
	}
	public void setCheque_dt(int cheque_dt) {
		this.cheque_dt = cheque_dt;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getSold_by() {
		return sold_by;
	}
	public void setSold_by(String sold_by) {
		this.sold_by = sold_by;
	}
	public int getPplp_reversed() {
		return pplp_reversed;
	}
	public void setPplp_reversed(int pplp_reversed) {
		this.pplp_reversed = pplp_reversed;
	}
	public void Sap_receipt_no(String string) {
		// TODO Auto-generated method stub
		
	}
	
}