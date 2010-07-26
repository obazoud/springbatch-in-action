/**
 * 
 */
package com.manning.sbia.ch11.integration;

/**
 * @author acogoluegnes
 *
 */
public class ProductImport {

	private String importId;
	
	private String state;

	public ProductImport(String importId, String state) {
		super();
		this.importId = importId;
		this.state = state;
	}

	public String getImportId() {
		return importId;
	}

	public String getState() {
		return state;
	}

	@Override
	public String toString() {
		return "ProductImport [importId=" + importId + ", state=" + state + "]";
	}	
	
}
