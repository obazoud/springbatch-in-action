package com.manning.sbia.ch14.chunk;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.manning.sbia.ch14.DummyProductWriter;

public class RemoteChunkingSpringIntegrationStepTest {
	
	private ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
		this.getClass().getSimpleName()+"-context.xml",RemoteChunkingSpringIntegrationStepTest.class
	);

	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	@Qualifier("remoteChunkingImportProductsJob")
	private Job remoteChunkingImportProductsJob;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DummyProductWriter itemWriter;
	
	@Before
	public void setUp() throws Exception {
		injectDependenciesIntoTest();
		int count = 55;
		for (int i = 0; i < count; i++) {
			String sql = "insert into product (id,name,description,price) values(?,?,?,?)";
			jdbcTemplate.update(
				sql,
				i,"Product "+i,"",124.6
			);
		}
	}

	@After
	public void teardDown() {
		ctx.close();
	}
	
	@Test
	public void testMultithreadedStep() throws Exception {
		JobExecution remoteChunkingImportProductsJobExec = launcher.run(
				remoteChunkingImportProductsJob,
			new JobParametersBuilder()
				.toJobParameters()
		);
		assertEquals(ExitStatus.COMPLETED, remoteChunkingImportProductsJobExec.getExitStatus());
		assertEquals(jdbcTemplate.queryForInt("select count(1) from product"),itemWriter.getProducts().size());
	}
	
	private void injectDependenciesIntoTest() {
		// annotation support must be enabled in the application context
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
	}
}
