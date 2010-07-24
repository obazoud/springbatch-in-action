/**
 * 
 */
package com.manning.sbia.ch11.integration;

import org.springframework.integration.annotation.Gateway;

/**
 * @author acogoluegnes
 *
 */
public interface ProductImportGateway {

	void importProducts(String content);
	
}
