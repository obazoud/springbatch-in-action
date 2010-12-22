package com.manning.sbia.ch13;

import org.springframework.batch.core.JobExecution;

public interface BatchMonitoringNotifier {
	void notify(JobExecution jobExecution);
}
