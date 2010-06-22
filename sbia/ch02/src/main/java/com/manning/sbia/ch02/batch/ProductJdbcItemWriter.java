/**
 * 
 */
package com.manning.sbia.ch02.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.manning.sbia.ch02.domain.Product;

/**
 * @author acogoluegnes
 *
 */
public class ProductJdbcItemWriter implements ItemWriter<Product> {
	
	private static final String INSERT_PRODUCT = "insert into product (id,name,description,price) values(?,?,?,?)";
	
	private static final String UPDATE_PRODUCT = "update product set name=?, description=?, price=? where id = ?";
	
	private SimpleJdbcTemplate jdbcTemplate;
	
	public ProductJdbcItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends Product> items) throws Exception {
		for(Product item : items) {
			int updated = jdbcTemplate.update(UPDATE_PRODUCT,
				item.getName(),item.getDescription(),item.getPrice(),item.getId()
			);
			if(updated == 0) {
				jdbcTemplate.update(
					INSERT_PRODUCT,
					item.getId(),item.getName(),item.getDescription(),item.getPrice()
				);	
			}								
		}
	}

}
