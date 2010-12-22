package com.manning.sbia.ch13;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JobRepositoryJdbcConnectionTest {

	@Test public void testJdbcConnection() throws Exception {
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost:9092/ch13");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		//dataSource.setSuppressClose(true);

		try {
			
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
}
