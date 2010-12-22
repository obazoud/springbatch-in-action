/**
 * 
 */
package com.manning.sbia.ch13;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author templth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/manning/sbia/ch13/batch-operator-context.xml")
public class JobOperatorTest {
	@Autowired
	private JobOperator jobOperator;
	
	/*@Test public void jobExplorer() throws Exception {
		List<Long> jobInstances = jobOperator.getJobInstances("importProductsJob", 0, 30);
		for (JobInstance jobInstance : jobInstances) {
			System.out.println(jobInstance.getId() + " - "+jobInstance.getJobName());
			List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
			for (JobExecution jobExecution : jobExecutions) {
				System.out.println("JobExecutions:");
				System.out.println(" - "+jobExecution.getId()+" - "+jobExecution.getExitStatus());
				
				Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
				System.out.println("    StepExecutions:");
				for (StepExecution stepExecution : stepExecutions) {
					System.out.println("     - "+stepExecution.getStepName()+" - "+stepExecution.getSummary());
				}
			}
		}
	}
	
	private List<JobExecution> getFailedJobExecutions(String jobName) {
		List<JobExecution> failedJobExecutions = new ArrayList<JobExecution>();

		int pageSize = 10;
		int currentPageSize = 10;
		int currentPage = 0;

		while (currentPageSize==pageSize) {
			List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, currentPage*pageSize, pageSize);
			currentPageSize = jobInstances.size();
			for (JobInstance jobInstance : jobInstances) {
				List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
				for (JobExecution jobExecution : jobExecutions) {
					System.out.println("tests on object = "+jobExecution.getExitStatus().equals(ExitStatus.FAILED));
					System.out.println("tests on code = "+jobExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()));
					if (jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
						List<Throwable> errors = jobExecution.getFailureExceptions();
						System.out.println("[jobExecution] errors = "+errors.size());
						for (Throwable error : errors) {
							error.printStackTrace();
						}
						//jobExecution.get
						
						Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
						for (StepExecution stepExecution : stepExecutions) {
							errors = stepExecution.getFailureExceptions();
							System.out.println("[stepExecution] errors = "+errors.size());
							for (Throwable error : errors) {
								error.printStackTrace();
							}
							System.out.println("exit description = "+stepExecution.getExitStatus().getExitDescription());
						}
						
						failedJobExecutions.add(jobExecution);
					}
				}
			}
		}
		return failedJobExecutions;
	}
	
	private List<Throwable> getFailureExceptions(JobExecution jobExecution) {
		List<Throwable> failureExceptions = new ArrayList<Throwable>();
		  
		if (!jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
			return failureExceptions;
		}

		List<Throwable> jobFailureExceptions
				= jobExecution.getFailureExceptions();
		if (jobFailureExceptions.size()>0) {
			failureExceptions.addAll(jobFailureExceptions);
		}

		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			List<Throwable> stepFailureExceptions
					= stepExecution.getFailureExceptions();
			if (stepFailureExceptions.size()>0) {
				failureExceptions.addAll(stepFailureExceptions);
			}
		}
		
		return failureExceptions;
	}

	private List<String> getFailureExitDescriptions(JobExecution jobExecution) {
		List<String> exitDescriptions = new ArrayList<String>();
		  
		if (!jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
			return exitDescriptions;
		}

		String exitDescription = jobExecution.getExitStatus().getExitDescription();
		if (!"".equals(exitDescription)) {
			exitDescriptions.add(exitDescription);
		}

		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			ExitStatus exitStatus = stepExecution.getExitStatus();
			if (exitStatus.equals(ExitStatus.FAILED) && !"".equals(exitStatus.getExitDescription())) {
				exitDescriptions.add(exitStatus.getExitDescription());
			}
		}
		
		return exitDescriptions;
	}
	
	@Test public void findFailedJobExecutions() throws Exception {
		System.out.println("#######################");
		List<String> jobNames = jobExplorer.getJobNames();
		for (String jobName : jobNames) {
			System.out.println(jobName+" - "+getFailedJobExecutions(jobName).size());
			List<JobExecution> failedJobExecutions = getFailedJobExecutions(jobName);
			for (JobExecution jobExecution : failedJobExecutions) {
				List<String> details = getFailureExitDescriptions(jobExecution);
				for (String detail : details) {
					System.out.println(" - "+detail);
				}
			}
		}
		System.out.println("#######################");
	}*/
	
	@Test public void findJobNames() throws Exception {
		Set<String> names = jobOperator.getJobNames();
		for (String name : names) {
			System.out.println("- name = "+name);
		}
	}
	
	public JobOperator getJobOperator() {
		return jobOperator;
	}

	public void setJobOperator(JobOperator jobOperator) {
		this.jobOperator = jobOperator;
	}

}
