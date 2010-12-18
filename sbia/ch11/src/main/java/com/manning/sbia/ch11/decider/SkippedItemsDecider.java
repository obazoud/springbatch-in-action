/**
 * 
 */
package com.manning.sbia.ch11.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author acogoluegnes
 *
 */
public class SkippedItemsDecider implements JobExecutionDecider {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.job.flow.JobExecutionDecider#decide(org.springframework.batch.core.JobExecution, org.springframework.batch.core.StepExecution)
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		if(stepExecution.getSkipCount() > 0) {
			return new FlowExecutionStatus("SKIPPED ITEMS");
		} else {
			return new FlowExecutionStatus("NO SKIPPED");
		}
	}

}
