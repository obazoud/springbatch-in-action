/**
 * 
 */
package com.manning.sbia.ch09.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author acogoluegnes
 *
 */
public class Order implements Serializable {

	private String orderId;
	
	private List<OrderItem> items;

	public Order(String orderId, List<OrderItem> items) {
		super();
		this.orderId = orderId;
		this.items = Collections.unmodifiableList(items);
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public List<OrderItem> getItems() {
		return items;
	}
	
}
