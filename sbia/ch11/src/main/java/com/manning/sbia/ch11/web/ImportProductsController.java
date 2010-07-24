/**
 * 
 */
package com.manning.sbia.ch11.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.manning.sbia.ch11.integration.ProductImportGateway;
import com.manning.sbia.ch11.repository.ProductImportRepository;

/**
 * @author acogoluegnes
 * 
 */
@Controller
public class ImportProductsController {
	
	@Autowired
	private ProductImportRepository productImportRepository;
	
	@Autowired
	private ProductImportGateway productImportGateway;

	@RequestMapping(value="/product-imports/{importId}",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void importProducts(@PathVariable String importId,@RequestBody String content) {
		productImportRepository.createProductImport(importId);
		// TODO handle idempotency (don't submit twice the import)
		productImportGateway.importProducts(content);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT,reason="Import already submitted.")
	public void duplicateImport() { }
	
	// TODO implement GET for ProductImport (use JAXB2)
	
}
