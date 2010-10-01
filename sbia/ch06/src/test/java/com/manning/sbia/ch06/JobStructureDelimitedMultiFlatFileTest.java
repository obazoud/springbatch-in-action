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
public class JobStructureDelimitedMultiFlatFileTest extends AbstractJobStructureTest {

	@Test public void delimitedJob() throws Exception {
		jobLauncher.run(job, new JobParameters());
		checkProducts(writer.getProducts(), new String[] { "PRM....210", "PRM....211", "PRB....734",
						 "PRM....212", "PRB....735", "PRM....213", "PRB....736", "PRM....214" });
	}
	
	
}
