/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author Shubhi.2.S
 *
 */
public interface CommunityPortalInvoicePostingJobService {
	
	/**
	 * 
	 * @throws CustomException
	 */
	List<InvoiceRequest> getGSTInvoiceDetailsForPosting() throws CustomException;
	List<InvoiceRequest> getDebitCreditInvoiceDetailsForPosting() throws CustomException;
	String getSendToBIALMessageUrl() throws CustomException;
	BigInteger getirpPostingLoopCountLimit();
	void updateCommunityPortalStatusAndErrorDesc() throws CustomException;

}
