/**
 * 
 */
package com.manning.sbia.sandbox.robustness;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemReader implements ItemReader<String> {
	
	private BusinessService service;
	
	private int count = 0;

	public DummyItemReader(BusinessService service) {
		super();
		this.service = service;
	}



	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public String read() throws Exception, UnexpectedInputException,
			ParseException {
		System.out.println("read");
		return service.reading();
	}

}
