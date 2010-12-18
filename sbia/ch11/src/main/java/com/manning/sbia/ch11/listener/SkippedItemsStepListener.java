/**
 * 
 */
package com.manning.sbia.ch11.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author acogoluegnes
 *
 */
public class SkippedItemsStepListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) { }
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if(stepExecution.getSkipCount() > 0) {
			return new ExitStatus("SKIPPED ITEMS");
		} else {
			return new ExitStatus("NO SKIPPED");
		}
	}
	
}
