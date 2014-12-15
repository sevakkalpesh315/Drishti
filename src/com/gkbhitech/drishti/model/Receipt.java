package com.gkbhitech.drishti.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Receipt implements JsonDataMarker{

private int r_plant;
private BigDecimal receipt_no;
private int receipt_dt;
private String cust_code;
private String payment_mode;
private BigDecimal amount;
private BigDecimal order_no;
private Date posting_dt;
private String narration;
private String cheque_no;
private Date cheque_dt;
private String inHouse_bank;
private String bank;
public int getR_plant() {
	return r_plant;
}
public void setR_plant(int r_plant) {
	this.r_plant = r_plant;
}
public BigDecimal getReceipt_no() {
	return receipt_no;
}
public void setReceipt_no(BigDecimal receipt_no) {
	this.receipt_no = receipt_no;
}
public int getReceipt_dt() {
	return receipt_dt;
}
public void setReceipt_dt(int receipt_dt) {
	this.receipt_dt = receipt_dt;
}
public String getCust_code() {
	return cust_code;
}
public void setCust_code(String cust_code) {
	this.cust_code = cust_code;
}
public String getPayment_mode() {
	return payment_mode;
}
public void setPayment_mode(String payment_mode) {
	this.payment_mode = payment_mode;
}
public BigDecimal getAmount() {
	return amount;
}
public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
public BigDecimal getOrder_no() {
	return order_no;
}
public void setOrder_no(BigDecimal order_no) {
	this.order_no = order_no;
}
public Date getPosting_dt() {
	return posting_dt;
}
public void setPosting_dt(Date posting_dt) {
	this.posting_dt = posting_dt;
}
public String getNarration() {
	return narration;
}
public void setNarration(String narration) {
	this.narration = narration;
}
public String getCheque_no() {
	return cheque_no;
}
public void setCheque_no(String cheque_no) {
	this.cheque_no = cheque_no;
}
public Date getCheque_dt() {
	return cheque_dt;
}
public void setCheque_dt(Date cheque_dt) {
	this.cheque_dt = cheque_dt;
}
public String getInHouse_bank() {
	return inHouse_bank;
}
public void setInHouse_bank(String inHouse_bank) {
	this.inHouse_bank = inHouse_bank;
}
public String getBank() {
	return bank;
}
public void setBank(String bank) {
	this.bank = bank;
}


}
