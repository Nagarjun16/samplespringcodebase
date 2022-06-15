package com.ngen.cosys.billing.sap.airline.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteMaster;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryMaster;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentBankTransfer;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCASH;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCheque;
import com.ngen.cosys.framework.exception.CustomException;

public interface SapAirlineDAO {
	public void updateSapBillingStatus(List<List<Integer>> idsSet) throws CustomException;
	public List<List<Integer>> fetchARInvoiceSalesEntryIds() throws CustomException;
	public List<List<Integer>> fetchDebitNoteIds() throws CustomException;
	public List<List<Integer>> fetchCreditNoteIds() throws CustomException;
	public List<ARInvoiceSalesEntryMaster> fetchARInvoiceSalesEntryMasterDetails(List<List<Integer>> idsSet) throws CustomException;
	public List<ARInvoiceSalesEntryChild> fetchARInvoiceSalesEntryChildDetails(List<List<Integer>> idsSet) throws CustomException;
	public List<ARInvoiceCreditDebitNoteMaster> fetchARInvoiceCreditNoteMasterDetails(List<List<Integer>> idsSet) throws CustomException;
	public List<ARInvoiceCreditDebitNoteMaster> fetchARInvoiceDebitNoteMasterDetails(List<List<Integer>> idsSet) throws CustomException;
	public List<ARInvoiceCreditDebitNoteChild> fetchARInvoiceDebitNoteChildDetails(List<List<Integer>> idsSet) throws CustomException;
	public List<IncomingPaymentBankTransfer> fetchIncomingPaymentBankTransferDetails() throws CustomException;
	public List<IncomingPaymentCASH> fetchIncomingPaymentCASHDetails() throws CustomException;
	public List<IncomingPaymentCheque> fetchIncomingPaymentChequeDetails() throws CustomException;
	public String getSAPSpecialCode() throws CustomException;
	public LocalDateTime getSAPDelayHour() throws CustomException;

}
