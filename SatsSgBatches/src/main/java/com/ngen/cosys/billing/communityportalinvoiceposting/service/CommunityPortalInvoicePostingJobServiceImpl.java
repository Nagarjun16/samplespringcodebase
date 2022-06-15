/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.communityportalinvoiceposting.dao.CommunityPortalInvoicePostingJobDAO;
import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author Shubhi.2.S
 *
 */
@Service
public class CommunityPortalInvoicePostingJobServiceImpl implements CommunityPortalInvoicePostingJobService{
	
	@Autowired
	CommunityPortalInvoicePostingJobDAO communityPortalInvoicePostingJobDAO;

	@Override
	public List<InvoiceRequest> getGSTInvoiceDetailsForPosting() throws CustomException {
		return communityPortalInvoicePostingJobDAO.getGSTInvoiceDetailsForPosting();
	}

	@Override
	public List<InvoiceRequest> getDebitCreditInvoiceDetailsForPosting() throws CustomException {
		return communityPortalInvoicePostingJobDAO.getDebitCreditInvoiceDetailsForPosting();
	}
	
	/**
	 *reutrn URL for hitting aisats interface
	 */
	@Override
	public String getSendToBIALMessageUrl() throws CustomException {
		return communityPortalInvoicePostingJobDAO.getSendToBIALMessageUrl();
	}

	@Override
	public BigInteger getirpPostingLoopCountLimit() {
		try {
			return communityPortalInvoicePostingJobDAO.getirpPostingLoopCountLimit();
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateCommunityPortalStatusAndErrorDesc() throws CustomException {
			communityPortalInvoicePostingJobDAO.updateCommunityPortalStatusAndErrorDescForNotPdAccount();
			communityPortalInvoicePostingJobDAO.updateCommunityPortalStatusAndErrorDescForUserBial();
	}

}
