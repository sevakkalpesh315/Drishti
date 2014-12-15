package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class CustomerReceipt {

	private String authToken;
	private int rplant;
	private String customer;
	private String paymentMode;
	private String narration;
	private Long cDate;
	private String bank;
	private String bankName;
	private Long docDate;
	private String chequeNo;
	private Long tDate;
	private BigDecimal amount;
	private String masterCode;
	private String sold_by;
	
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public int getRplant() {
		return rplant;
	}
	public void setRplant(int rplant) {
		this.rplant = rplant;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getcDate() {
		return cDate;
	}
	public void setcDate(Long cDate) {
		this.cDate = cDate;
	}
	public Long getDocDate() {
		return docDate;
	}
	public void setDocDate(Long docDate) {
		this.docDate = docDate;
	}
	public Long gettDate() {
		return tDate;
	}
	public void settDate(Long tDate) {
		this.tDate = tDate;
	}
	public String getSold_by() {
		return sold_by;
	}
	public void setSold_by(String sold_by) {
		this.sold_by = sold_by;
	}
}
