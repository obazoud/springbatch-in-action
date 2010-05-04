/**
 * 
 */
package com.manning.sbia.ch03.structure;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.manning.sbia.ch02.domain.Invoice;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemReader implements ItemReader<Invoice> {
	
	Queue<Invoice> _invoices = new LinkedBlockingDeque<Invoice>() {{
		add(new Invoice("1"));
		add(new Invoice("2"));
		add(new Invoice("3"));
		add(new Invoice("4"));
	}};

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public Invoice read() throws Exception, UnexpectedInputException,
			ParseException {
		return _invoices.poll();
	}

}
