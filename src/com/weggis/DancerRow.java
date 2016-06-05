package com.weggis;

import java.time.LocalDateTime;

public class DancerRow {

	String ref;
	LocalDateTime orderTime;
	int price;
	int numTicket;

	OrderStatus status;

	int userId;
	String firstName;
	String lastName;
	String fullName;

	public DancerRow(String ref, LocalDateTime orderTime, int price,
			int numTicket, OrderStatus status,
			int userId, String firstName, String lastName) {
		this.ref = ref;
		this.orderTime = orderTime;
		this.price = price;
		this.numTicket = numTicket;
		this.status = status;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		fullName = firstName + " " + lastName;
	}

	int getUserId() {
		return userId;
	}
}
