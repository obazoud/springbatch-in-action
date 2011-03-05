package com.manning.sbia.ch14.partition;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PartitionSpringIntegrationStepTest {
	
	private ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
		this.getClass().getSimpleName()+"-context.xml",PartitionSpringIntegrationStepTest.class
	);

	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	@Qualifier("partitionImportProductsJob")
	private Job partitionImportProductsJob;
	
	@Before public void setUp() {
		injectDependenciesIntoTest();
	}
	
	@After public void tearDown() {
		ctx.close();
	}

	@Test
	public void testMultithreadedStep() throws Exception {
		/*JobExecution partitionImportProductsJobExec = launcher.run(
				partitionImportProductsJob,
			new JobParametersBuilder()
				.toJobParameters()
		);*/
	}
	
	private void injectDependenciesIntoTest() {
		// annotation support must be enabled in the application context
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
	}
}
