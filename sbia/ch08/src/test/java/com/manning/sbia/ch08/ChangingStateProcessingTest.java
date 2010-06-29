/**
 * 
 */
package com.manning.sbia.ch08;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ChangingStateProcessingTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("readWriteJob")
	private Job jobWithItemProcessor;
	
	@Autowired
	@Qualifier("readWriteJobPojo")
	private Job jobWithAdapter;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test public void changingState() throws Exception {
		jdbcTemplate.update("delete from product");
		JobExecution exec = jobLauncher.run(jobWithItemProcessor, new JobParametersBuilder()
			.addString("inputFile", "classpath:/products.txt")
			.toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED, exec.getExitStatus());
		List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from product");
		for(Map<String,Object> row : rows) {
			Assert.assertTrue(row.get("ID").toString().startsWith("PR"));
			Assert.assertTrue(row.get("NAME").toString().endsWith("(P1)"));
		}
	}
	
	@Test public void changingStateWithAdapter() throws Exception {
		jdbcTemplate.update("delete from product");
		JobExecution exec = jobLauncher.run(jobWithAdapter, new JobParametersBuilder()
			.addString("inputFile", "classpath:/products.txt")
			.toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED, exec.getExitStatus());
		List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from product");
		for(Map<String,Object> row : rows) {
			Assert.assertTrue(row.get("ID").toString().startsWith("PR"));
			Assert.assertTrue(row.get("NAME").toString().endsWith("(P1)"));
		}
	}
	
}
