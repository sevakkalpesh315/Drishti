
package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class OrderStatus {
	private int sdate;
	private int stime;
	private String username;
	private String dept_code;
	private int status_code;
	private String reject_code;
	private BigDecimal id;
	public int getSdate() {
		return sdate;
	}
	public void setSdate(int sdate) {
		this.sdate = sdate;
	}
	public int getStime() {
		return stime;
	}
	public void setStime(int stime) {
		this.stime = stime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	public String getReject_code() {
		return reject_code;
	}
	public void setReject_code(String reject_code) {
		this.reject_code = reject_code;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	
}
