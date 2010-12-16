/**
 * 
 */
package com.manning.sbia.ch12.integration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.PollableChannel;
import org.springframework.integration.core.Message;
import org.springframework.integration.selector.MessageSelector;

/**
 * @author acogoluegnes
 *
 */
public class FileLaunchingJobTest {
	
	private static final long RECEIVING_TIMEOUT = 1000;

	private static final File DROP_IN_DIRECTORY = new File(System.getProperty("java.io.tmpdir")
		 	+File.separator+"sbia");
	
	private static final String JOB_NAME = "simpleJob";
	
	private static PollableChannel jobExecutionsChannel;
	
	@BeforeClass public static void setUpClass() throws Exception {
		FileUtils.deleteDirectory(DROP_IN_DIRECTORY);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				FileLaunchingJobTest.class.getSimpleName()+"-context.xml", FileLaunchingJobTest.class);
		jobExecutionsChannel = context.getBean("job-executions", PollableChannel.class);
	}
	
	@Before public void setUp() throws Exception {
		FileUtils.cleanDirectory(DROP_IN_DIRECTORY);
		jobExecutionsChannel.purge(new MessageSelector() {			
			@Override
			public boolean accept(Message<?> message) {
				return true;
			}
		});
		
	}

	@Test public void fileLaunchingJobEmptyParams() throws Exception {
		sendFile("launch-job-with-params","simpleJob");
		
		JobExecution jobExecution = receiveMessage();
		Assert.assertEquals(JOB_NAME,jobExecution.getJobInstance().getJobName());
		Assert.assertTrue(jobExecution.getJobInstance().getJobParameters().isEmpty());		
	}
	
	@Test public void fileLaunchingJobSomeParams() throws Exception {
		sendFile("launch-job","simpleJob[date=2010-07-23]");
		
		JobExecution jobExecution = receiveMessage();
		Assert.assertEquals(JOB_NAME,jobExecution.getJobInstance().getJobName());
		Assert.assertFalse(jobExecution.getJobInstance().getJobParameters().isEmpty());
		Assert.assertEquals("2010-07-23",jobExecution.getJobInstance().getJobParameters().getString("date"));
	}

	private void sendFile(String filename,String launchingString) throws IOException {
		Writer writer = new FileWriter(new File(DROP_IN_DIRECTORY,filename+".tmp"));
		IOUtils.write(launchingString,writer);
		writer.flush();
		FileUtils.moveFile(new File(DROP_IN_DIRECTORY,filename+".tmp"), 
				new File(DROP_IN_DIRECTORY,filename+".txt"));
	}

	private JobExecution receiveMessage() {
		Message<?> message = jobExecutionsChannel.receive(RECEIVING_TIMEOUT);
		Assert.assertNotNull(message);
		Assert.assertEquals(JobExecution.class,message.getPayload().getClass());
		return (JobExecution) message.getPayload();
	}
	
}
