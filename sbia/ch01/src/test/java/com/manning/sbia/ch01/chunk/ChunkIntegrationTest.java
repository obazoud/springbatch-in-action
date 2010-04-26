/**
 * 
 */
package com.manning.sbia.ch01.chunk;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
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
@ContextConfiguration
public class ChunkIntegrationTest {
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job job;

	@Test public void readAndWrite() throws Exception {
		File output = new File("./target/output.txt");
		FileUtils.deleteQuietly(output);
		JobExecution jobExecution = launcher.run(job, new JobParameters());
		Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		Assert.assertEquals(9, FileUtils.readLines(output).size());
	}
	
}
