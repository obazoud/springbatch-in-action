/**
 * 
 */
package com.manning.sbia.sandbox;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class DummyPartitionProductReader implements ItemReader<Product> {
	
	private int max ;
	
	private String prefix;
	
	private int count = 0;



	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public Product read() throws Exception, UnexpectedInputException,
			ParseException {
		if(count < max) {
			String id = prefix+count;
			count++;
			return new Product(id);
		} else {
			return null;
		}
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setMax(int max) {
		this.max = max;
	}

}
