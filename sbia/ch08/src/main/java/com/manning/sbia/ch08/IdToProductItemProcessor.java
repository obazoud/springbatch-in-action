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
public class IdToProductItemProcessor implements ItemProcessor<String, Product> {

	private ProductDao productDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public Product process(String productId) throws Exception {		
		return productDao.load(productId);
	}
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}
