/**
 * 
 */
package com.manning.sbia.ch11;

import java.io.File;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author acogoluegnes
 * 
 */
public class EnterpriseIntegrationTest {

	private static final String BASE_URI = "http://localhost:8080/enterpriseintegration/";

	private JdbcTemplate jdbcTemplate;

	private Server server;

	@Before
	public void setUp() throws Exception {
		startWebContainer();
	}

	@After
	public void tearDown() throws Exception {
		stopServer();
	}

	@Test
	public void enterpriseIntegration() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String importId = "partner1-1";
		restTemplate.postForLocation(BASE_URI + "product-imports/{importId}",
				loadProductFiles(importId), importId);

	}

	private String loadProductFiles(String importId) throws Exception {
		return FileUtils
				.readFileToString(
						new File(
								new File(
										"./src/test/resources/com/manning/sbia/ch11/product-import-samples"),
								"products-" + importId + ".xml"), "UTF-8");
	}

	private DataSource getWebAppDataSource(WebAppContext wac) {
		ApplicationContext webAppAppCtx = WebApplicationContextUtils
				.getWebApplicationContext(wac.getServletContext());
		Assert.assertNotNull(webAppAppCtx);
		return webAppAppCtx.getBean(DataSource.class);
	}

	private void setUpJdbcTemplate(WebAppContext wac) {
		this.jdbcTemplate = new JdbcTemplate(getWebAppDataSource(wac));
	}

	private void startWebContainer() throws Exception {
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/enterpriseintegration");
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);

		server.start();
		setUpJdbcTemplate(wac);
	}

	private void stopServer() throws Exception {
		if (server != null && server.isRunning()) {
			server.stop();
		}
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.setProperty("product.import.pickup.dir",
				LaunchEnterpriseIntegrationServer.PICKUP_DIR);
		File pickUpDir = new File(LaunchEnterpriseIntegrationServer.PICKUP_DIR);
		if (pickUpDir.exists()) {
			FileUtils.cleanDirectory(pickUpDir);
		}
	}

}
