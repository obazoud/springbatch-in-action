/**
 * 
 */
package com.manning.sbia.ch02.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * @author acogoluegnes
 *
 */
public class DecompressTaskletTest {
	
	private static final String[] EXPECTED_CONTENT = new String [] {
		"INVOICE_ID,CUSTOMER_ID,DESCRIPTION,ISSUE_DATE,AMOUNT",
		"PR....210,9834,,2010-09-15,124.60",
		"PR....211,9834,,2010-10-25,139.45",
		"PR....212,6765,,2010-08-26,97.80",
		"PR....213,4525,,2010-12-19,166.20",
		"PR....214,9737,,2010-11-18,145.50",
		"PR....215,7362,,2010-06-10,190.70",
		"PR....216,3580,,2010-10-22,289.20",
		"PR....217,8736,,2010-07-15,13.70"
	};

	@Test public void execute() throws Exception {
		DecompressTasklet tasklet = new DecompressTasklet();
		tasklet.setInputResource(new ClassPathResource("/input/invoices.zip"));
		File outputDir = new File("./target/decompresstasklet");
		if(outputDir.exists()) {
			FileUtils.deleteDirectory(outputDir);
		}
		tasklet.setTargetDirectory(outputDir.getAbsolutePath());
		tasklet.setTargetFile("invoices.txt");
		
		tasklet.execute(null, null);
		
		File output = new File(outputDir,"invoices.txt");
		Assert.assertTrue(output.exists());
		
		Assert.assertArrayEquals(EXPECTED_CONTENT, FileUtils.readLines(output).toArray());
		
	}
	
}
