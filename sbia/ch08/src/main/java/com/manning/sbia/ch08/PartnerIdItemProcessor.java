/**
 * 
 */
package com.manning.sbia.ch08;

import org.springframework.batch.item.ItemProcessor;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class PartnerIdItemProcessor implements
		ItemProcessor<Product, Product> {

	private PartnerIdMapper mapper;
	
	@Override
	public Product process(Product item) throws Exception {		
		return mapper.map(item);
	}
	
	public void setMapper(PartnerIdMapper mapper) {
		this.mapper = mapper;
	}
	
}
