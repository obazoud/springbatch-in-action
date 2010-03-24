/**
 * 
 */
package com.manning.sbia.ch02.batch;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/batch-application-context.xml","/test-batch-infrastructure.xml"})
public class ImportInvoicesIntegrationTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Test public void importInvoices() throws Exception {
		System.out.println(jdbcTemplate.queryForList("select * from invoice"));
		int initial = jdbcTemplate.queryForInt("select count(1) from invoice");
		jobLauncher.run(job, new JobParameters());
		
		System.out.println(jdbcTemplate.queryForList("select * from invoice"));
		
		Assert.assertEquals(initial+7,jdbcTemplate.queryForInt("select count(1) from invoice"));
	}
	
}
