/**
 * 
 */
package com.manning.sbia.ch03.structure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.manning.sbia.ch02.domain.Invoice;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemWriter implements ItemWriter<Invoice> {
	
	public List<Invoice> invoices = new ArrayList<Invoice>();

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Invoice> items) throws Exception {
		invoices.addAll(items);
	}

}
