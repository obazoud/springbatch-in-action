/**
 * 
 */
package com.manning.sbia.ch05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepContribution;
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
public class SpringSchedulingTest {
	
	@Autowired
	private CountDownLatch xmlCountDownLatch;
	
	@Autowired
	private CountDownLatch annotationCountDownLatch;
	
	@Test public void xmlSpringScheduling() throws Exception {		
		Assert.assertTrue("job should have been launched several times already",xmlCountDownLatch.await(10, TimeUnit.SECONDS));
	}
	
	@Test public void annotationSpringScheduling() throws Exception {		
		Assert.assertTrue("job should have been launched several times already",annotationCountDownLatch.await(10, TimeUnit.SECONDS));
	}
		
	public static class CountDownTasklet implements Tasklet {

		private CountDownLatch countDownLatch;
		
		@Override
		public RepeatStatus execute(StepContribution contribution,
				ChunkContext chunkContext) throws Exception {
			countDownLatch.countDown();
			return RepeatStatus.FINISHED;
		}
		
		public void setCountDownLatch(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}
	}
	
}
