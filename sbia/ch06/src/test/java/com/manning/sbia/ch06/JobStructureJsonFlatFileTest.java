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
@ContextConfiguration
public class JobStructureJsonFlatFileTest extends AbstractJobStructureTest {

	@Test public void jsonJob() throws Exception {
		jobLauncher.run(job, new JobParameters());
		checkProducts(writer.getProducts(), new String[] { "PR....210","PR....211","PR....212",
						"PR....213","PR....214","PR....215","PR....216","PR....217"});
	}
	
	
}
