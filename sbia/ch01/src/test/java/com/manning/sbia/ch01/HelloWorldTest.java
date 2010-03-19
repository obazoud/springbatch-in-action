/**
 * 
 */
package com.manning.sbia.ch01;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/manning/sbia/ch01/hello-applicationContext.xml"})
public class HelloWorldTest {
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job job;
	
	@Autowired
	private HelloTasklet tasklet;

	@Test public void hello() throws Exception {
		Assert.assertFalse(tasklet.isExecuted());
		JobExecution jobExecution = launcher.run(job, new JobParameters());
		Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		Assert.assertTrue(tasklet.isExecuted());
	}
	
}
