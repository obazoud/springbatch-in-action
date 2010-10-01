/**
 * 
 */
package com.manning.sbia.ch06;

import java.util.List;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author templth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractJobStructureTest {

	@Autowired
	protected Job job;
	
	@Autowired
	protected JobLauncher jobLauncher;

	@Autowired
	protected DummyProductItemWriter writer;
	
	protected void hasProduct(List<Product> products, String productId) {
		for (Product product : products) {
			if (product.getId().equals(productId)) {
				return;
			}
		}

		Assert.fail("Product with id "+productId+" is expected.");
	}

	protected void checkProducts(List<Product> products, String[] productIds) {
		Assert.assertEquals(8, products.size());
		for (String productId : productIds) {
			hasProduct(products, productId);
		}
	}

}
