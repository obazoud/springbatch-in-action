/**
 * 
 */
package com.manning.sbia.ch01;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author acogoluegnes
 *
 */
public class HelloTasklet implements Tasklet {
	
	private boolean executed = false;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		System.out.println("Hello Spring Batch World!");
		executed = true;
		return RepeatStatus.FINISHED;
	}

	public boolean isExecuted() {
		return executed;
	}
	
	

}
