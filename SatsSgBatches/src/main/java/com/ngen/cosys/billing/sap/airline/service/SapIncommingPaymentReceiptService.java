package com.ngen.cosys.billing.sap.airline.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface SapIncommingPaymentReceiptService {
	public void createAndSendMessageToSap() throws CustomException;
}
