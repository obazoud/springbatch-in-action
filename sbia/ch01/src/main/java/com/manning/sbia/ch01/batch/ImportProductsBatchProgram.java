/**
 * 
 */
package com.manning.sbia.ch01.batch;

import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author acogoluegnes
 * 
 */
public class ImportProductsBatchProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 3) {
			throw new IllegalArgumentException(
					"Batch job needs 3 parameters: " +
					"input archive, target directory, target file"
			);
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"/import-invoices-job-context.xml",
				"/test-batch-infrastructure-context.xml");

		init(context);

		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		Job job = context.getBean(Job.class);
		JobExecution jobExecution = jobLauncher.run(job,
				new JobParametersBuilder().addString("inputResource", args[0])
						.addString("targetDirectory", args[1]).addString(
								"targetFile", args[2]).toJobParameters());

		System.out.println("Job ended with status: "
				+ jobExecution.getExitStatus());

	}

	private static final void init(ApplicationContext ctx) {
		SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(ctx
				.getBean(DataSource.class));
		jdbcTemplate.update("drop table if exists invoice");
		jdbcTemplate
				.update("CREATE TABLE invoice (" + "id character(9) NOT NULL,"
						+ "customer_id integer NOT NULL,"
						+ "description character varying(50),"
						+ "issue_date date," + "amount float,"
						+ "CONSTRAINT invoice_pkey PRIMARY KEY (id))");
		jdbcTemplate.update("delete from invoice");
		jdbcTemplate
				.update(
						"insert into invoice (id,customer_id,description,issue_date,amount) values(?,?,?,?,?)",
						"PR....214", 9737, "",
						Calendar.getInstance().getTime(), 102.23);
	}

}
