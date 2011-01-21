package com.manning.sbia.ch07.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author bazoud
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobPassThroughFlatFileTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    public static void assertLineFileEquals(Resource resource, int lineNumber,  String expectedLine) throws Exception {
        assertLineFileEquals(resource.getFile(), lineNumber,  expectedLine);
    }
    public static void assertLineFileEquals(File file, int lineNumber,  String expectedLine) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            int lineNum = 1;
            String line = reader.readLine();
            while (line != null && lineNum < lineNumber) {
                lineNum++;
                line = reader.readLine();
            }
           Assert.assertEquals("Line number " + lineNum + " does not match.", lineNumber, lineNum);
           Assert.assertEquals("Content line at number " + lineNum + " does not match.", expectedLine, line);
        }
        finally {
            reader.close();
        }
    }
    @Test
    public void delimitedJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
        JobExecution exec = jobLauncherTestUtils.launchJob(jobParameters);
        Assert.assertEquals(BatchStatus.COMPLETED, exec.getStatus());

        Resource ouput= new FileSystemResource("./target/test-outputs/products-output-passthrough.txt");
        assertLineFileEquals(ouput, 1, "Product [id=PR....210, name=BlackBerry 8100 Pearl]");
        assertLineFileEquals(ouput, 7, "Product [id=PR....216, name=AT&T 8525 PDA]");
        assertLineFileEquals(ouput, 8, "Product [id=PR....217, name=Canon Digital Rebel XT 8MP Digital SLR Camera]");
    }

}
