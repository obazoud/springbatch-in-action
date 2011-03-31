/**
 * 
 */
package com.manning.sbia.ch01;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:/job-context.xml",
	"classpath:/test-context.xml"
})
public class ExportJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test public void export() throws Exception {
		JobExecution exec = jobLauncher.run(job, new JobParameters());
		Assert.assertEquals(BatchStatus.COMPLETED, exec.getStatus());
		Assert.assertEquals(
			jdbcTemplate.queryForInt("select count(1) from product"),
			FileUtils.readLines(new File("./target/output.txt")).size()
		);
	}
	
}
