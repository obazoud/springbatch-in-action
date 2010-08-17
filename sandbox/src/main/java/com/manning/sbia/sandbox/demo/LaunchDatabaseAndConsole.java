/**
 * 
 */
package com.manning.sbia.sandbox.demo;

import org.h2.tools.Console;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author acogoluegnes
 *
 */
public class LaunchDatabaseAndConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(
			"/com/manning/sbia/sandbox/demo/batch-infrastructure-context.xml",
			"/com/manning/sbia/sandbox/demo/root-database-context.xml"
		);
		Console.main(args);
	}

}
