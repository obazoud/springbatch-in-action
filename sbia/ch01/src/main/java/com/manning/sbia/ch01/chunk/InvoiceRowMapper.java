/**
 * 
 */
package com.manning.sbia.ch01.chunk;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author acogoluegnes
 *
 */
public class InvoiceRowMapper implements RowMapper<Invoice> {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Invoice invoice = new Invoice();
		invoice.setId(rs.getString("id"));
		invoice.setCustomerId(rs.getLong("customer_id"));
		invoice.setDescription(rs.getString("description"));
		invoice.setIssueDate(rs.getDate("issue_date"));
		invoice.setAmount(rs.getBigDecimal("amount"));
		return invoice;
	}
	
}
