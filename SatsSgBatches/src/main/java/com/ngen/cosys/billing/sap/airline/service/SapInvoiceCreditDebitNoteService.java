package com.ngen.cosys.billing.sap.airline.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapInvoiceCreditDebitNoteService {
	public void createAndSendDebitNoteMessageToSap() throws CustomException ;
	public void createAndSendCreditNoteMessageToSap() throws CustomException;
}
