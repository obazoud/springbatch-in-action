/**
 * 
 */
package com.manning.sbia.sandbox.robustness;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author acogoluegnes
 * 
 */
public class DummyItemWriter implements ItemWriter<String> {

	private BusinessService service;


	public DummyItemWriter(BusinessService service) {
		super();
		this.service = service;
	}

	@Override
	public void write(List<? extends String> items) throws Exception {
		for(String item : items) {
			System.out.println("writing "+item);
			service.writing(item);
			System.out.println("item written "+item);
		}
	}

}
