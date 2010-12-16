/**
 * 
 */
package com.manning.sbia.ch11.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author acogoluegnes
 *
 */
public class FileExistsDecider implements JobExecutionDecider {
	
	private BatchService batchService;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.job.flow.JobExecutionDecider#decide(org.springframework.batch.core.JobExecution, org.springframework.batch.core.StepExecution)
	 */
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			StepExecution stepExecution) {
		String targetFile = jobExecution.getJobInstance().getJobParameters().getString("archiveFile");
		if(batchService.exists(targetFile)) {
			return new FlowExecutionStatus("FILE EXISTS");
		} else {
			return new FlowExecutionStatus("NO FILE");
		}
	}
	
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	
}
