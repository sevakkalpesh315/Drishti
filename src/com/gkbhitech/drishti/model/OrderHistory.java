package com.gkbhitech.drishti.model;

import java.math.BigDecimal;

public class OrderHistory implements JsonDataMarker{
private BigDecimal orderNumber;
public BigDecimal getOrderNumber() {
	return orderNumber;
}

public void setOrderNumber(BigDecimal orderNumber) {
	this.orderNumber = orderNumber;
}

private OrderStatus[]orderStatus;

public OrderStatus[] getOrderStatus() {
	return orderStatus;
}

public void setOrderStatus(OrderStatus[] orderStatus) {
	this.orderStatus = orderStatus;
}

}
