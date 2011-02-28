package com.manning.sbia.ch13.batch;

import org.springframework.batch.core.JobExecution;

public interface BatchMonitoringNotifier {
	void notify(JobExecution jobExecution);
}
