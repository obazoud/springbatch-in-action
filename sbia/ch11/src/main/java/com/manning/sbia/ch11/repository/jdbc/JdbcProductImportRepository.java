/**
 * 
 */
package com.manning.sbia.ch11.repository.jdbc;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manning.sbia.ch11.repository.ProductImportRepository;

/**
 * @author acogoluegnes
 *
 */
@Repository
@Transactional
public class JdbcProductImportRepository implements ProductImportRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcProductImportRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see com.manning.sbia.ch11.repository.ProductImportRepository#createProductImport(java.lang.String)
	 */
	@Override
	public void createProductImport(String importId)
			throws DuplicateKeyException {
		// TODO handle state of the import
		int count = jdbcTemplate.queryForInt("select count(1) from product_import where import_id = ?",importId);
		if(count > 0) {
			throw new DuplicateKeyException("Import already exists: "+importId);
		}
		jdbcTemplate.update("insert into product_import (import_id,creation_date) values (?,?)",importId,new Date());
	}

}
