/**
 * 
 */
package com.manning.sbia.ch02.batch;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * @author acogoluegnes
 *
 */
public class ImportProductsJobParametersValidator implements
		JobParametersValidator,ResourceLoaderAware {
	
	private static final String PARAM_INPUT_RESOURCE = "inputResource";
	private static final String PARAM_TARGET_DIRECTORY = "targetDirectory";
	private static final String PARAM_TARGET_FILE = "targetFile";
	
	private ResourceLoader resourceLoader;

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobParametersValidator#validate(org.springframework.batch.core.JobParameters)
	 */
	public void validate(JobParameters parameters)
			throws JobParametersInvalidException {
		Collection<String> missing = new ArrayList<String>();
		checkParameter(PARAM_INPUT_RESOURCE, parameters, missing);
		checkParameter(PARAM_TARGET_DIRECTORY, parameters, missing);
		checkParameter(PARAM_TARGET_FILE, parameters, missing);
		if(!missing.isEmpty()) {
			throw new JobParametersInvalidException("Missing job parameter(s): "+missing);
		}
		
		if(!resourceLoader.getResource(parameters.getString(PARAM_INPUT_RESOURCE)).exists()) {
			throw new JobParametersInvalidException("The input file: "+parameters.getString(PARAM_INPUT_RESOURCE)+" does not exist");
		}
	}
	
	private void checkParameter(String parameterKey,JobParameters parameters,Collection<String> missing) {
		if(!parameters.getParameters().containsKey(parameterKey)) {
			missing.add(parameterKey);
		}
	}
	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;		
	}

}
