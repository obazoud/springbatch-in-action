/**
 * 
 */
package com.manning.sbia.ch03;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creates the Spring Batch's job repository tables. 
 * You must create the database first!
 * @author acogoluegnes
 *
 */
public class CreateJobRepositoryAndInvoiceTables {
	
	
	
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(
			"/initialize-job-repository-context.xml",
			"/com/manning/sbia/ch03/batch-infrastructure-pgsql-context.xml"
		);		
		
		
	}	
	

}
