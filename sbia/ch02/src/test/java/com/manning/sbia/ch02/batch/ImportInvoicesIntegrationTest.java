/**
 * 
 */
package com.manning.sbia.ch02.batch;

import java.util.Calendar;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
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
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate.update("drop table if exists invoice");
		jdbcTemplate.update("CREATE TABLE invoice (" +
				"id character(9) NOT NULL,"+
				"customer_id integer NOT NULL,"+
				"description character varying(50),"+
				"issue_date date,"+
				"amount float,"+
				"CONSTRAINT invoice_pkey PRIMARY KEY (id))"
		);
		jdbcTemplate.update("delete from invoice");
		jdbcTemplate.update(
			"insert into invoice (id,customer_id,description,issue_date,amount) values(?,?,?,?,?)",
			"PR....214",9737,"",Calendar.getInstance().getTime(),102.23	
		);
	}

	@Test public void importInvoices() throws Exception {
		int initial = jdbcTemplate.queryForInt("select count(1) from invoice");
		
		jobLauncher.run(job, new JobParametersBuilder()
			.addString("inputResource", "classpath:/input/invoices.zip")
			.addString("targetDirectory", "./target/importinvoicesbatch/")
			.addString("targetFile","invoices.txt")
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters()
		);
		
		Assert.assertEquals(initial+7,jdbcTemplate.queryForInt("select count(1) from invoice"));
	}
	
	@Test public void importInvoicesWithErrors() throws Exception {
		int initial = jdbcTemplate.queryForInt("select count(1) from invoice");
		
		jobLauncher.run(job, new JobParametersBuilder()
			.addString("inputResource", "classpath:/input/invoices_with_errors.zip")
			.addString("targetDirectory", "./target/importinvoicesbatch/")
			.addString("targetFile","invoices.txt")
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters()
		);
		
		Assert.assertEquals(initial+6,jdbcTemplate.queryForInt("select count(1) from invoice"));
	}
	
	@Test public void missingParameters() throws Exception {		
		try {
			jobLauncher.run(job, new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/invoices_with_errors.zip")
				.toJobParameters()
			);
			Assert.fail("missing parameters, the job should not have been launched");
		} catch (JobParametersInvalidException e) {
			// OK
		}		
	}
	
	@Test public void inputDoesNotExist() throws Exception {	
		try {
			jobLauncher.run(job, new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/bad_invoices_input.zip")
				.addString("targetDirectory", "./target/importinvoicesbatch/")
				.addString("targetFile","invoices.txt")
				.toJobParameters()
			);
			Assert.fail("the input does not exist, the job should not have been launched");
		} catch (JobParametersInvalidException e) {
			// OK	
		}		
	}
	
}
