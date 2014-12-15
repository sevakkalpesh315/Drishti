package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class CustomerVisit implements JsonDataMarker{

	private TalkAbout[] talkAbout;
	private BigDecimal closingBalance;
	private BigDecimal salesOfPreviousMonthOne;
	private BigDecimal salesOfPreviousMonthTwo;
	private BigDecimal salesOfPreviousMonthThree;
	private BigDecimal averageSale;
	private BigDecimal receiptOne;
	private BigDecimal receiptTwo;
	private BigDecimal receiptThree;
	private String outStandingDay;
	private int currentDate;
	
	public TalkAbout[] getTalkAbout() {
		return talkAbout;
	}
	public void setTalkAbout(TalkAbout[] talkAbout) {
		this.talkAbout = talkAbout;
	}
	public BigDecimal getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}
	public BigDecimal getSalesOfPreviousMonthOne() {
		return salesOfPreviousMonthOne;
	}
	public void setSalesOfPreviousMonthOne(BigDecimal salesOfPreviousMonthOne) {
		this.salesOfPreviousMonthOne = salesOfPreviousMonthOne;
	}
	public BigDecimal getSalesOfPreviousMonthTwo() {
		return salesOfPreviousMonthTwo;
	}
	public void setSalesOfPreviousMonthTwo(BigDecimal salesOfPreviousMonthTwo) {
		this.salesOfPreviousMonthTwo = salesOfPreviousMonthTwo;
	}
	public BigDecimal getSalesOfPreviousMonthThree() {
		return salesOfPreviousMonthThree;
	}
	public void setSalesOfPreviousMonthThree(BigDecimal salesOfPreviousMonthThree) {
		this.salesOfPreviousMonthThree = salesOfPreviousMonthThree;
	}
	public BigDecimal getAverageSale() {
		return averageSale;
	}
	public void setAverageSale(BigDecimal averageSale) {
		this.averageSale = averageSale;
	}
	public BigDecimal getReceiptOne() {
		return receiptOne;
	}
	public void setReceiptOne(BigDecimal receiptOne) {
		this.receiptOne = receiptOne;
	}
	public BigDecimal getReceiptTwo() {
		return receiptTwo;
	}
	public void setReceiptTwo(BigDecimal receiptTwo) {
		this.receiptTwo = receiptTwo;
	}
	public BigDecimal getReceiptThree() {
		return receiptThree;
	}
	public void setReceiptThree(BigDecimal receiptThree) {
		this.receiptThree = receiptThree;
	}
	public String getOutStandingDay() {
		return outStandingDay;
	}
	public void setOutStandingDay(String outStandingDay) {
		this.outStandingDay = outStandingDay;
	}
	public int getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(int currentDate) {
		this.currentDate = currentDate;
	}
}
