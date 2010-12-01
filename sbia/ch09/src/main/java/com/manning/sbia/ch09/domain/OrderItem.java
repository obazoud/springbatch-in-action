/**
 * 
 */
package com.manning.sbia.ch09.domain;

import java.io.Serializable;

/**
 * @author acogoluegnes
 *
 */
public class OrderItem implements Serializable {

	private String productId;
	
	private short quantity;

	public OrderItem(String productId, short quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public short getQuantity() {
		return quantity;
	}
	
}
