/**
 * 
 */
package com.manning.sbia.sandbox.robustness;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SkipTest {
	
	@Before public void setUp() {
		reset(service);
	}
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	BusinessService service;

	@Test public void sunnyDay() throws Exception {
		int read = 12;
		configureServiceForRead(service, read);
		JobExecution exec = jobLauncher.run(
			job, 
			new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED,exec.getExitStatus());
		assertRead(read, exec);
		assertWrite(read, exec);
		assertReadSkip(0, exec);
		assertProcessSkip(0, exec);
		assertWriteSkip(0, exec);
		assertCommit(3, exec);
		assertRollback(0, exec);
	}
	
	@Test public void exceptionInReading() throws Exception {
		when(service.reading())
			.thenReturn("1")
			.thenReturn("2")
			.thenReturn("3")
			.thenReturn("4")
			.thenReturn("5")
			.thenReturn("6")
			.thenThrow(new FlatFileParseException("", ""))
			.thenReturn("8")
			.thenReturn("9")
			.thenReturn("10")
			.thenReturn("11")
			.thenReturn("12")
			.thenReturn(null);
		JobExecution exec = jobLauncher.run(
			job, 
			new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED,exec.getExitStatus());
		verify(service,times(13)).reading();
		verify(service,times(11)).processing(anyString());
		verify(service,times(11)).writing(anyString());
		assertRead(11, exec);
		assertWrite(11, exec);
		assertReadSkip(1, exec);
		assertProcessSkip(0, exec);
		assertWriteSkip(0, exec);
		assertCommit(3, exec);
		assertRollback(0, exec);
	}
	
	@Test public void exceptionInWriting() throws Exception {
		int read = 12;
		configureServiceForRead(service, read);
		
		final String toFailWriting = "7";
		doNothing().when(service).writing(argThat(new BaseMatcher<String>() {
			@Override
			public boolean matches(Object input) {				
				return !toFailWriting.equals(input);
			}
			
			@Override
			public void describeTo(Description desc) { }
			
		}));
		doThrow(new DataIntegrityViolationException("")).when(service).writing(toFailWriting);
			
		JobExecution exec = jobLauncher.run(
			job, 
			new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED,exec.getExitStatus());
		verify(service,times(13)).reading();
		verify(service,times(5+2+1+1+3+2)).writing(anyString());
		verify(service,times(5+2+1+1+3+2)).writing(anyString());
		assertRead(read, exec);
		assertWrite(read-1, exec);
		assertReadSkip(0, exec);
		assertProcessSkip(0, exec);
		assertWriteSkip(1, exec);
		assertCommit(6, exec);
		assertRollback(2, exec);
	}
	
	@Test public void exceptionInProcessing() throws Exception {
		int read = 12;
		configureServiceForRead(service, read);
		
		final String toFailProcessing = "7";
		doNothing().when(service).processing(argThat(new BaseMatcher<String>() {
			@Override
			public boolean matches(Object input) {				
				return !toFailProcessing.equals(input);
			}
			
			@Override
			public void describeTo(Description desc) { }
			
		}));
		doThrow(new DataIntegrityViolationException("")).when(service).processing(toFailProcessing);
			
		doNothing().when(service).writing(anyString());
		
		JobExecution exec = jobLauncher.run(
			job, 
			new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters()
		);
		Assert.assertEquals(ExitStatus.COMPLETED,exec.getExitStatus());
		verify(service,times(5+5+2+1)).reading();
		verify(service,times(5+1+1+4+2)).processing(anyString());
		verify(service,times(5+4+2)).writing(anyString());
		assertRead(read, exec);
		assertWrite(read-1, exec);
		assertReadSkip(0, exec);
		assertProcessSkip(1, exec);
		assertWriteSkip(0, exec);
		assertCommit(3, exec);
		assertRollback(1, exec);
	}
	
	private void configureServiceForRead(BusinessService service,int count) {
		List<String> args = new ArrayList<String>();
		for(int i=2;i<=count;i++) {
			args.add(String.valueOf(i));
		}
		args.add(null);
		when(service.reading()).thenReturn("1",args.toArray(new String[0]));
	}
	
	private void assertRead(int read, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(read,stepExec.getReadCount());
	}
	
	private void assertWrite(int write, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(write,stepExec.getWriteCount());
	}
	
	private void assertProcessSkip(int processSkip, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(processSkip,stepExec.getProcessSkipCount());
	}
	
	private void assertReadSkip(int readSkip, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(readSkip,stepExec.getReadSkipCount());
	}
	
	private void assertWriteSkip(int writeSkip, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(writeSkip,stepExec.getWriteSkipCount());
	}
	
	private void assertCommit(int commit, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(commit,stepExec.getCommitCount());
	}
	
	private void assertRollback(int rollback, JobExecution exec) {
		StepExecution stepExec = getStepExecution(exec);
		Assert.assertEquals(rollback,stepExec.getRollbackCount());
	}

	private StepExecution getStepExecution(JobExecution exec) {
		StepExecution stepExec = exec.getStepExecutions().iterator().next();
		return stepExec;
	}
	
}
