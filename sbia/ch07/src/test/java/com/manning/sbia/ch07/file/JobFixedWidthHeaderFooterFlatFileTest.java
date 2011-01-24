package com.manning.sbia.ch07.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bazoud
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobFixedWidthHeaderFooterFlatFileTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void testFixedWidthHeaderFooter() throws Exception {
        JobExecution exec = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(BatchStatus.COMPLETED, exec.getStatus());

        Resource ouput= new FileSystemResource("./target/outputs/fixedwidth-headerfooter.txt");
        AssertLine.assertLineFileEquals(ouput, 2, "PR....210124.60BlackBerry 8100 Pearl    ");
        AssertLine.assertLineFileEquals(ouput, 8, "PR....216289.20AT&T 8525 PDA            ");
        AssertLine.assertLineFileEquals(ouput, 9, "PR....21713.70Canon Digital Rebel XT 8MP");
    }
    
}
