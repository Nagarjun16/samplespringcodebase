package com.ngen.cosys.billing.sap.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapInvoiceSentByEmailService {

	void invoiceSentByEmail() throws CustomException;
	
}
