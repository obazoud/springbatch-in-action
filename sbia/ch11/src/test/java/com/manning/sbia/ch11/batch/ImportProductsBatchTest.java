/**
 * 
 */
package com.manning.sbia.ch11.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ImportProductsBatchTest {

	@Autowired
	private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Test public void importProducts() throws Exception {
		String importId = "products-partner1-1";
		JobExecution exec = jobLauncher.run(job, new JobParametersBuilder()
			.addString("importId", importId)
			.addString("inputFile", productsFilePath(importId)).toJobParameters());
		Assert.assertEquals(BatchStatus.COMPLETED,exec.getStatus());
	}
	
	private String productsFilePath(String importId) {
		return "./src/test/resources/com/manning/sbia/ch11/product-import-samples"+importId+".xml";
	}
	
}
