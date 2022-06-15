/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author Shubhi.2.S
 *
 */
public interface CommunityPortalInvoicePostingJobDAO {
	
	/**
	 * @throws CustomException
	 * 
	 */
	List<InvoiceRequest> getGSTInvoiceDetailsForPosting() throws CustomException;
	List<InvoiceRequest> getDebitCreditInvoiceDetailsForPosting() throws CustomException;
	String getSendToBIALMessageUrl() throws CustomException;
	BigInteger getirpPostingLoopCountLimit() throws CustomException;
	void updateCommunityPortalStatusAndErrorDescForNotPdAccount() throws CustomException;
	void updateCommunityPortalStatusAndErrorDescForUserBial() throws CustomException;
}
