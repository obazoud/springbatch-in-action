/**
 * 
 */
package com.manning.sbia.sandbox.parallel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("parallel-import-job.xml")
public class ParallelTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job jobSimple;
	
	@Autowired
	private Job jobParallel;
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private StagingProductItemWriter itemWriter;

	@Test public void parallel() throws Exception {
		// warming up
		executeJob(100, jobSimple);
		executeJob(100, jobParallel);
		int max = 100; // can see some difference starting from 1000 rows
		int nbIterations = 5;
		for(int i=0;i<nbIterations;i++) {
			long simpleTime = executeJob(max, jobSimple);
			long parallelTime = executeJob(max, jobParallel);
//			System.out.println("simple: "+simpleTime+", parallel: "+parallelTime);
		}		
	}
	
	@Test public void restart() throws Exception {
		int count = 100;
		stageProducts(count);
		int initialCount = countProducts();
		itemWriter.cleanPoisonPills();
		itemWriter.addPoisonPills("27","30","56","57","67","78");
		long start = System.currentTimeMillis();
		JobExecution exec = jobLauncher.run(jobSimple, new JobParametersBuilder().addLong("time", start).toJobParameters());
		System.out.println(exec.getStepExecutions().iterator().next().getSkipCount());
		int currentCount = countProducts();
		Assert.assertTrue("not all products should have been written",(currentCount-initialCount)<count);
		itemWriter.cleanPoisonPills();
		exec = jobLauncher.run(jobSimple, new JobParametersBuilder().addLong("time", start).toJobParameters());
		System.out.println(exec.getStepExecutions().iterator().next().getSkipCount());
//		System.out.println(exec);
//		System.out.println(template.queryForList("select id from product", String.class));
		List<String> unprocessed = template.queryForList("select id from staging_product where processed = ?",String.class,false);
//		System.out.println(unprocessed);
		currentCount = countProducts();
		Assert.assertEquals(initialCount+count,currentCount);
	}
	
	private long executeJob(int count,Job job) throws Exception {
		stageProducts(count);
		int initialCount = countProducts();
		long start = System.currentTimeMillis();
		jobLauncher.run(job, new JobParametersBuilder().addLong("time", start).toJobParameters());
		long end = System.currentTimeMillis();
		int currentCount = countProducts();
		Assert.assertEquals(initialCount+count,currentCount);
		return end - start;
	}
	
	private int countProducts() {
		return template.queryForInt("select count(1) from product");
	}
	
	private void stageProducts(final int count) {
		template.update("delete from staging_product");
		template.update("delete from product");
		
		template.batchUpdate(
			"insert into staging_product (id,processed) values (?,?)", 
			new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, String.valueOf(i));
					ps.setBoolean(2, false);
				}
				
				@Override
				public int getBatchSize() {
					return count;
				}
				
			});
	}
}
