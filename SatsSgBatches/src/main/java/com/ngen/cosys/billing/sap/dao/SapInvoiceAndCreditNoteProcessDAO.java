package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.InvoiceAndCreditNote;
import com.ngen.cosys.billing.sap.model.InvoiceSentByEmail;
import com.ngen.cosys.framework.exception.CustomException;

public interface SapInvoiceAndCreditNoteProcessDAO {

	public InvoiceAndCreditNote saveInvoiceAndCreditNoteDetails(InvoiceAndCreditNote invoiceAndCreditNote)
			throws CustomException;

	public List<InvoiceSentByEmail> getInvoiceSentByEmail() throws CustomException;
  
	
	public List<String> fetchEmail(InvoiceSentByEmail invoiceSentByEmail) throws CustomException;
	
	public void updateESupportDocEmailSent(InvoiceSentByEmail invoice) throws CustomException;

	public String getCompanyCode() throws CustomException;
}
