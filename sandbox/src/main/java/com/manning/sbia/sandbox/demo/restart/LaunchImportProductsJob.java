/**
 * 
 */
package com.manning.sbia.sandbox.demo.restart;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Launches the import invoices job.
 * @author acogoluegnes
 *
 */
public class LaunchImportProductsJob {

	/**
     * @param args
     */
    public static void main(String[] args) throws Exception {
            ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "/com/manning/sbia/sandbox/demo/import-products-job-context.xml",
                    "/com/manning/sbia/sandbox/demo/batch-infrastructure-context.xml",
        			"/com/manning/sbia/sandbox/demo/connect-database-context.xml"
            );
            
            JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
            Job job = ctx.getBean(Job.class);       
            
            jobLauncher.run(job, new JobParametersBuilder()
            	.addString("inputFile", "file:./src/main/resources/com/manning/sbia/sandbox/demo/input/products1.txt")
                .toJobParameters()
            );
    }
	
}
