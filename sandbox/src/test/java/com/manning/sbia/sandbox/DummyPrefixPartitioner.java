/**
 * 
 */
package com.manning.sbia.sandbox;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

/**
 * @author acogoluegnes
 *
 */
public class DummyPrefixPartitioner implements Partitioner {
	
	private String [] prefixes;
	
	public DummyPrefixPartitioner(String[] prefixes) {
		super();
		this.prefixes = prefixes;
	}



	/* (non-Javadoc)
	 * @see org.springframework.batch.core.partition.support.Partitioner#partition(int)
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> map = new LinkedHashMap<String, ExecutionContext>(gridSize);
		int i = 0;
		for(String prefix : prefixes) {
			ExecutionContext executionContext = new ExecutionContext();
			executionContext.putString("prefix", prefix);
			map.put("partition"+i, executionContext);
			i++;
		}
		return map;
	}

}
