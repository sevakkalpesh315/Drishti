package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class CustomerAccountStatement {

	private BigDecimal openingBalance;
	private String customerCode;
	private String customerName;
	private String clearDoc;
	private String fiscalYear;
	private String docNo;
	private String docType;
	private String postingDate;
	private String itemRefNo;
	private String itemShortTxt;
	private String debCreIndicatior;
	private BigDecimal amount;
	private String debAmount;
	private String creAmount;
	private BigDecimal runningAmount;
	private String invoceNo;
	
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getClearDoc() {
		return clearDoc;
	}
	public void setClearDoc(String clearDoc) {
		this.clearDoc = clearDoc;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getItemRefNo() {
		return itemRefNo;
	}
	public void setItemRefNo(String itemRefNo) {
		this.itemRefNo = itemRefNo;
	}
	public String getItemShortTxt() {
		return itemShortTxt;
	}
	public void setItemShortTxt(String itemShortTxt) {
		this.itemShortTxt = itemShortTxt;
	}
	public String getDebCreIndicatior() {
		return debCreIndicatior;
	}
	public void setDebCreIndicatior(String debCreIndicatior) {
		this.debCreIndicatior = debCreIndicatior;
	}
	
	public String getDebAmount() {
		return debAmount;
	}
	public void setDebAmount(String debAmount) {
		this.debAmount = debAmount;
	}
	public String getCreAmount() {
		return creAmount;
	}
	public void setCreAmount(String creAmount) {
		this.creAmount = creAmount;
	}
	
	public String getInvoceNo() {
		return invoceNo;
	}
	public void setInvoceNo(String invoceNo) {
		this.invoceNo = invoceNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRunningAmount() {
		return runningAmount;
	}
	public void setRunningAmount(BigDecimal runningAmount) {
		this.runningAmount = runningAmount;
	}
}
