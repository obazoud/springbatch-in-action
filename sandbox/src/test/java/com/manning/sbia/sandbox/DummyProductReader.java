/**
 * 
 */
package com.manning.sbia.sandbox;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyProductReader implements ItemReader<Product> {
	
	private AtomicInteger count = new AtomicInteger(0);
	
	private Integer max = 100;
	
	

	public DummyProductReader(Integer max) {
		super();
		this.max = max;
	}



	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public Product read() throws Exception, UnexpectedInputException,
			ParseException {
		synchronized(count) {
			if(count.incrementAndGet() <= max) {
				return new Product(String.valueOf(count.get()));
			} else {
				return null;
			}
		}
		
	}

}
