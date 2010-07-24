/**
 * 
 */
package com.manning.sbia.ch11.repository;

import org.springframework.dao.DuplicateKeyException;

/**
 * @author acogoluegnes
 *
 */
public interface ProductImportRepository {

	void createProductImport(String importId) throws DuplicateKeyException;
	
	// TODO implement loading of the product import
	
}
