package com.gkbhitech.drishti.order;

import com.gkbhitech.drishti.model.JsonDataMarker;

public class OrderNo implements JsonDataMarker{

	private long orderNo;
	
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
}
