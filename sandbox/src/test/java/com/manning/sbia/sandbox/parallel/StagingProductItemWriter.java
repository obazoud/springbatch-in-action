/**
 * 
 */
package com.manning.sbia.sandbox.parallel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class StagingProductItemWriter implements ItemWriter<Product> {
	
	private List<String> poisonPills = new ArrayList<String>();
	
	private JdbcTemplate jdbcTemplate;
	
	public StagingProductItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(final List<? extends Product> items) throws Exception {
		jdbcTemplate.batchUpdate(
				"insert into product (id,name,description,price) values(?,?,?,?)", 
				new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1,items.get(i).getId());
				ps.setString(2,items.get(i).getName());
				ps.setString(3,items.get(i).getDescription());
				ps.setBigDecimal(4,items.get(i).getPrice());
			}
			
			@Override
			public int getBatchSize() {
				return items.size();
			}
		});
		
		jdbcTemplate.batchUpdate(
				"update staging_product set processed = ? where id = ?", 
				new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				if(poisonPills.isEmpty() || !poisonPills.contains(items.get(i).getId())) {
					ps.setBoolean(1,true);
					ps.setString(2,items.get(i).getId());
				} else {
					throw new DeadlockLoserDataAccessException("poison pill!",null);
				}				
			}
			
			@Override
			public int getBatchSize() {
				return items.size();
			}
		});
		Thread.sleep(50);
	}
	
	public void addPoisonPills(String ... ids) {
		poisonPills.addAll(Arrays.asList(ids));
	}
	
	public void cleanPoisonPills() {
		poisonPills.clear();
	}

}
