/**
 * 
 */
package com.manning.sbia.ch05;

import java.text.SimpleDateFormat;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.util.StringUtils;

/**
 * @author acogoluegnes
 *
 */
public class JobParametersConverterTest {

	@Test public void convert() {
		Properties params = StringUtils.splitArrayElementsIntoProperties(new String[]{
			"someString(string)=someStringValue",
			"someDate(date)=2010/12/08",
			"someLong(long)=23",
			"someDouble(double)=23.4"
		}, "=");
		JobParametersConverter converter = new DefaultJobParametersConverter();
		JobParameters parameters = converter.getJobParameters(params);
		Assert.assertEquals("someStringValue",parameters.getString("someString"));
		Assert.assertEquals(23L,parameters.getLong("someLong"));
		Assert.assertEquals(23.4,parameters.getDouble("someDouble"),0);
		Assert.assertEquals(
			"2010/12/08",
			new SimpleDateFormat("yyyy/MM/dd").format(parameters.getDate("someDate"))
		);
	}
	
}
