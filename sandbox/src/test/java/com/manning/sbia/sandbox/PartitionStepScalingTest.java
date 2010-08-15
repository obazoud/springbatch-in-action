/**
 * 
 */
package com.manning.sbia.sandbox;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PartitionStepScalingTest {
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	@Qualifier("readWriteSimpleJob")
	private Job simpleJob;
	
	@Autowired
	@Qualifier("readWritePartitionJob")
	private Job partitionJob;
	
	@Autowired
	@Qualifier("writerForPartitionJob")
	private DummyProductWriter writerForPartitionJob;

	@Test public void partitionStepScaling() throws Exception {
		StopWatch sw = new StopWatch();
		
		int nbOfExec = 5;
		int nbOfPartition = 6;
		long maxPerPartition = 20;
		long count = nbOfPartition * maxPerPartition;
		
		for(int i=0;i<nbOfExec;i++) {
			sw.start("simple "+i);
			JobExecution simpleJobExec = launcher.run(
				simpleJob,
				new JobParametersBuilder().addLong("exec", (long) i)
					.addLong("max",count)
					.toJobParameters()
			);
			sw.stop();
			Assert.assertEquals(ExitStatus.COMPLETED,simpleJobExec.getExitStatus());
			
			long simpleExecTime = calculateExecutionTime(simpleJobExec);
			int writtenCount = simpleJobExec.getStepExecutions().iterator().next().getWriteCount();
			Assert.assertEquals(count,writtenCount);
			
			sw.start("multi-threaded "+i);
			JobExecution multiThreadedJobExec = launcher.run(
				partitionJob,
				new JobParametersBuilder().addLong("exec", (long) i)
					.addLong("maxPerPartition", maxPerPartition)
					.toJobParameters()
			);
			sw.stop();
			Assert.assertEquals(ExitStatus.COMPLETED,multiThreadedJobExec.getExitStatus());
			
			writtenCount = multiThreadedJobExec.getStepExecutions().iterator().next().getWriteCount();
			Assert.assertEquals(count,writtenCount);			
			
			// checks partitionning is ok
			List<Product> writtenProducts = writerForPartitionJob.getProducts();
			Map<String, Long> counts = new HashMap<String, Long>();
			for(Product product : writtenProducts) {
				String prefix = product.getId().substring(0, 1);
				Long currentCount = counts.get(prefix);
				if(currentCount == null) {
					currentCount = 0L;
				}
				currentCount++;
				counts.put(prefix, currentCount);
			}
			for(Long currentCount : counts.values()) {
				Assert.assertEquals(maxPerPartition,currentCount.longValue());
			}
			
			writerForPartitionJob.clear();
			
			long multiThreadedExecTime = calculateExecutionTime(multiThreadedJobExec);
			Assert.assertTrue("multi-threaded should be faster than simple",multiThreadedExecTime<simpleExecTime);
			
		}
		
		System.out.println(sw.prettyPrint());
	}

	private long calculateExecutionTime(JobExecution simpleJobExec) {
		Date start = simpleJobExec.getStartTime();
		Date end = simpleJobExec.getEndTime();
		return end.getTime() - start.getTime();
	}
	
}
