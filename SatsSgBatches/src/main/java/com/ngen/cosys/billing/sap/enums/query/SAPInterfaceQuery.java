package com.ngen.cosys.billing.sap.enums.query;

public enum SAPInterfaceQuery {

   INSERT_SAP_FILE_INFO_DETAILS("insertsapFileInfodetails"),
   UPDATE_SAP_FILE_INFO_DETAILS("updatesapFileInfodetails"), 
   INSERT_SAP_FILE_PROCESSING_ERROR_DETAILS("insertSapFileProcessingErrorDetails"),
	
   INSERT_INVOICE_AND_CREDIT_NOTE_DETAILS("insertInvoiceAndCreditNoteDetails"), 
   INSERT_INVOICE_AND_CREDIT_NOTE_ENTRY_DETAILS("insertInvoiceAndCreditNoteEntryDetails"),

   UPDATE_INVOICE_NUMBER("updatefinsysinvoiceno"),

   INSERT_MATERIAL_MASTER("insertMaterialMaster"), 
   UPDATE_MATERIAL_MASTER("updateMaterialMaster"), 
   DELETE_MATERIAL_MASTER("deleteMaterialMaster"),

   INSERT_CUSTOMER_MASTER("insertCutomerMaster"), 
   UPDATE_CUSTOMER_MASTER("updateCutomerMaster"), 
   DELETE_CUSTOMER_MASTER("deleteCutomerMaster"),

   FETCH_ACCOUNT_PAYABLE_DETAILS("fetchAccountPayableDetails"), 
   FETCH_SALE_AND_DISTRIBUTION_BILLING_DETAILS("fetchSalesAndDistributionBillingDetails"),

   UPDATE_BILLGEN_POSTING_STATUS("updatBillGenPostingStatus"), 
   UPDATE_BILLENTRY_POSTING_STATUS("updateBillEntryPostingStatus"),
   
   UPDATE_APBILLENTRY_POSTING_STATUS("updateAPBillEntryPostingStatus"),

   FETCH_OUT_BOUND_FILE_FOLDER("getFileOutboundFolder"), 
   FETCH_SALE_AND_DISTRIBUTION_FILE_SEQ_NUMBER("getSaleAndDistributionFileSeqNo"),

   FETCH_ACCOUNT_PAYABLE_FILE_SEQ_NUMBER("getAccountPayableFileSeqNo"), GET_SALE_AND_DISTRIBUTION_EXTERNAL_SALE_ORDER_TYPE("getSaleAndDistirbutionExternalSaleOrderType"),
   GET_SALE_AND_DISTRIBUTION_INTERNAL_SALE_ORDER_TYPE("getSaleAndDistirbutionInternalSaleOrderType"), GET_SALE_AND_DISTRIBUTION_PRICING_TYPE_CODE("getSaleAndDistirbutionPricingTypeCode"),
   GET_SALE_AND_DISTRIBUTION_COMPANY_CODE("getSaleAndDistirbutionCompanyCode");
	
	

	String queryId;

	SAPInterfaceQuery(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return queryId;
	}

}
