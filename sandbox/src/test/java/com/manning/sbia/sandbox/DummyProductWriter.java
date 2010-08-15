/**
 * 
 */
package com.manning.sbia.sandbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.batch.item.ItemWriter;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyProductWriter implements ItemWriter<Product> {
	
	private List<Product> products = new ArrayList<Product>();

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Product> items) throws Exception {
		for(Product product : items) {
			processProduct(product);
		}
	}

	private void processProduct(Product product) throws InterruptedException {
		Thread.sleep(5);
		synchronized(products) {
			products.add(product);
		}
	}
	
	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}
	
	public void clear() {
		products.clear();
	}

}
