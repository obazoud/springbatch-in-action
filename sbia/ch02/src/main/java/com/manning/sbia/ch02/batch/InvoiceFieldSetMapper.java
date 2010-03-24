/**
 * 
 */
package com.manning.sbia.ch02.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.manning.sbia.ch02.domain.Invoice;

/**
 * @author acogoluegnes
 *
 */
public class InvoiceFieldSetMapper implements FieldSetMapper<Invoice> {
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet(org.springframework.batch.item.file.transform.FieldSet)
	 */
	public Invoice mapFieldSet(FieldSet fieldSet) throws BindException {
		Invoice invoice = new Invoice();
		invoice.setId(fieldSet.readString("INVOICE_ID"));
		invoice.setCustomerId(fieldSet.readString("CUSTOMER_ID"));
		invoice.setDescription(fieldSet.readString("DESCRIPTION"));
		invoice.setIssueDate(fieldSet.readDate("ISSUE_DATE"));
		invoice.setAmount(fieldSet.readBigDecimal("AMOUNT"));
		return invoice;
	}
	
}
