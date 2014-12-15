package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class LastPayment implements JsonDataMarker{

	private BigDecimal closingBalance;
	private long receiptNo;
	private String receiptDate;
	private BigDecimal receiptAmt;
	private int currentDate;

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}
	public long getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(long receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public BigDecimal getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(BigDecimal receiptAmt) {
		this.receiptAmt = receiptAmt;
	}
	public int getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(int currentDate) {
		this.currentDate = currentDate;
	}
}
