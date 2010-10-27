/**
 * 
 */
package com.manning.sbia.sandbox.robustness;

/**
 * @author acogoluegnes
 *
 */
public interface BusinessService {
	
	String reading();

	void writing(String item);
	
	void processing(String item);
	
}
