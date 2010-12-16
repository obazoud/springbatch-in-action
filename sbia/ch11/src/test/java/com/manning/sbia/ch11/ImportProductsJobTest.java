/**
 * 
 */
package com.manning.sbia.ch11;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.manning.sbia.ch11.batch.BatchService;
import com.manning.sbia.ch11.batch.ImportMetadata;
import com.manning.sbia.ch11.batch.ImportMetadataHolder;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"batch-infrastructure.xml","import-products-job.xml"})
public class ImportProductsJobTest {
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Autowired
	private Tasklet readWriteProductsTasklet;
	
	@Autowired
	private ImportMetadataHolder importMetadataHolder;

	@Test public void importProducts() throws Exception {
		String archiveFile = "input.zip";
		String workingDirectory = "/tmp";
		
		ImportMetadata metadata = new ImportMetadata();
		metadata.setImportId("1");
		
		assertNull(importMetadataHolder.get());
		
		when(batchService.extractMetadata(workingDirectory))
			.thenReturn(metadata);
		
		when(batchService.exists(archiveFile))
			.thenReturn(true);
		
		when(readWriteProductsTasklet.execute(any(StepContribution.class), any(ChunkContext.class)))
			.thenAnswer(new Answer<RepeatStatus>() {
				@Override
				public RepeatStatus answer(InvocationOnMock invocation)
						throws Throwable {
					StepContribution contribution = (StepContribution) invocation.getArguments()[0];
					contribution.incrementReadSkipCount();
					return RepeatStatus.FINISHED;
				}
			});
		
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("archiveFile", archiveFile)
			.addString("workingDirectory",workingDirectory).toJobParameters();
		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		assertEquals(4,jobExecution.getStepExecutions().size());
		
		assertSame(metadata, importMetadataHolder.get());
		
		verify(batchService,times(1)).download(archiveFile);
		verify(batchService,times(1)).decompress(archiveFile, workingDirectory);
		verify(batchService,times(1)).verify(workingDirectory);
		verify(batchService,times(1)).extractMetadata(workingDirectory);
		
	}
	
}
