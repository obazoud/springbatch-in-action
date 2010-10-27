/**
 * 
 */
package com.manning.sbia.sandbox.robustness;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author acogoluegnes
 *
 */
public class DummyItemProcessor implements ItemProcessor<String, String> {
	
	private BusinessService service;
	
	public DummyItemProcessor(BusinessService service) {
		super();
		this.service = service;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public String process(String item) throws Exception {
		System.out.println("processing "+item);
		service.processing(item);
		System.out.println("after processing "+item);
		return item;
	}

}
