/**
 * 
 */
package com.manning.sbia.ch11.integration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author acogoluegnes
 *
 */
public class JobLaunchRequest {

	private String jobName;
	
	private Map<String,String> jobParameters;	

	public JobLaunchRequest(String jobName) {
		this(jobName,Collections.EMPTY_MAP);
	}

	public JobLaunchRequest(String jobName, Map<String,String> jobParameters) {
		super();
		this.jobName = jobName;
		this.jobParameters = jobParameters;
	}
	
	public JobLaunchRequest(String jobName, Properties jobParametersAsProps) {
		this(jobName);
		this.jobParameters = new HashMap<String, String>();
		for(Map.Entry<?,?> entry : jobParametersAsProps.entrySet()) {
			this.jobParameters.put(entry.getKey().toString(), entry.getValue().toString());
		}
	}

	public String getJobName() {
		return jobName;
	}

	public Map<String,String> getJobParameters() {
		return jobParameters == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(jobParameters);
	}
	
}
