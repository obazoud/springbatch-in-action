/**
 * 
 */
package com.manning.sbia.ch02.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.manning.sbia.ch02.domain.Invoice;

/**
 * @author acogoluegnes
 *
 */
public class InvoiceJdbcItemWriter implements ItemWriter<Invoice> {
	
	private SimpleJdbcTemplate jdbcTemplate;
	
	public InvoiceJdbcItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends Invoice> items) throws Exception {
		for(Invoice item : items) {
			int updated = jdbcTemplate.update("update invoice set customer_id=?, description=?, issue_date=?, amount=? where id = ?",
				item.getCustomerId(),item.getDescription(),item.getIssueDate(),item.getAmount(),item.getId()
			);
			if(updated == 0) {
				jdbcTemplate.update(
					"insert into invoice (id,customer_id,description,issue_date,amount) values(?,?,?,?,?)",
					item.getId(),item.getCustomerId(),item.getDescription(),item.getIssueDate(),item.getAmount()
				);	
			}								
		}
	}

}
