/**
 * 
 */
package com.manning.sbia.ch13;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Launching the web console.
 * @author templth
 *
 */
public class LaunchWebConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("ENVIRONMENT", "h2");
		System.setProperty("batch.data.source.init", "false");
		Server server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setPort(8080);
        connector.setHost("127.0.0.1");
        server.addConnector(connector);
              

        WebAppContext wac = new WebAppContext();
        wac.setContextPath("/springbatchadmin");
        wac.setWar("./spring-batch-admin-sample-1.0.0.RELEASE.war");
        server.setHandler(wac);
        server.setStopAtShutdown(true);

        server.start();
        
        System.out.println("**** Spring Batch Admin launched");

	}
}
