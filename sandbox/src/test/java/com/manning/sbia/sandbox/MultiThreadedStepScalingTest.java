/**
 * 
 */
package com.manning.sbia.sandbox;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MultiThreadedStepScalingTest {
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	@Qualifier("readWriteSimpleJob")
	private Job simpleJob;
	
	@Autowired
	@Qualifier("readWriteMultiThreadedJob")
	private Job multiThreadedJob;

	@Test public void multiThreadingStepScaling() throws Exception {
		StopWatch sw = new StopWatch();
		
		int nbOfExec = 5;
		long count = 100;
		
		for(int i=0;i<nbOfExec;i++) {
			sw.start("simple "+i);
			JobExecution simpleJobExec = launcher.run(
				simpleJob,
				new JobParametersBuilder().addLong("exec", (long)i)
					.addLong("count",count)
					.toJobParameters()
			);
			sw.stop();
			Assert.assertEquals(ExitStatus.COMPLETED,simpleJobExec.getExitStatus());
			
			long simpleExecTime = calculateExecutionTime(simpleJobExec);
			int writtenCount = simpleJobExec.getStepExecutions().iterator().next().getWriteCount();
			Assert.assertEquals(count,writtenCount);
			
			sw.start("multi-threaded "+i);
			JobExecution multiThreadedJobExec = launcher.run(
				multiThreadedJob,
				new JobParametersBuilder().addLong("exec", (long)i)
					.addLong("count",count)
					.toJobParameters()
			);
			sw.stop();
			Assert.assertEquals(ExitStatus.COMPLETED,multiThreadedJobExec.getExitStatus());
			
			writtenCount = simpleJobExec.getStepExecutions().iterator().next().getWriteCount();
			Assert.assertEquals(count,writtenCount);
			
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
