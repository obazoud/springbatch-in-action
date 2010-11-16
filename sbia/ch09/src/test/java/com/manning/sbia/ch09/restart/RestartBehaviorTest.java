/**
 * 
 */
package com.manning.sbia.ch09.restart;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RestartBehaviorTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job notRestartableJob;

	@Autowired
	private Tasklet taskletForNotRestartableJob;

	@Test
	public void notRestartable() throws Exception {
		when(taskletForNotRestartableJob.execute(
				any(StepContribution.class), any(ChunkContext.class))
			).thenThrow(new RuntimeException());
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("date", System.currentTimeMillis())
			.toJobParameters();
		JobExecution exec = jobLauncher.run(notRestartableJob, jobParameters);
		Assert.assertEquals(BatchStatus.FAILED, exec.getStatus());
		try {
			exec = jobLauncher.run(notRestartableJob, jobParameters);
			fail("not a restartable job, an exception should have launched");
		} catch (JobRestartException e) {
			// OK
		}
	}
	
	@Autowired
	private Job restartableJob;

	@Autowired
	private Tasklet taskletForRestartableJob;
	
	@Test
	public void restartable() throws Exception {
		when(taskletForRestartableJob.execute(
				any(StepContribution.class), any(ChunkContext.class))
			).thenThrow(new RuntimeException())
			.thenReturn(RepeatStatus.FINISHED);
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("date", System.currentTimeMillis())
			.toJobParameters();
		JobExecution exec = jobLauncher.run(restartableJob, jobParameters);
		Assert.assertEquals(BatchStatus.FAILED, exec.getStatus());		
		exec = jobLauncher.run(restartableJob, jobParameters);
		Assert.assertEquals(BatchStatus.COMPLETED, exec.getStatus());
		try {
			exec = jobLauncher.run(restartableJob, jobParameters);
			fail("restartable and already completed job instance, exception should have been thrown");
		} catch (JobInstanceAlreadyCompleteException e) {
			// OK
		}
	}
}
