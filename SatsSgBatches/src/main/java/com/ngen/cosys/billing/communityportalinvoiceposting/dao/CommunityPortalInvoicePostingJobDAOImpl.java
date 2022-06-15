/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * @author Shubhi.2.S
 *
 */
@Repository("communityportalinvoicepostingjobDAO")
public class CommunityPortalInvoicePostingJobDAOImpl extends BaseDAO implements CommunityPortalInvoicePostingJobDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	public List<InvoiceRequest> getGSTInvoiceDetailsForPosting() throws CustomException {
		return super.fetchList("fetchGSTInvoiceList", null, sqlSession);
	}

	@Override
	public List<InvoiceRequest> getDebitCreditInvoiceDetailsForPosting() throws CustomException {
		return super.fetchList("fetchCreditDebitInvoiceList", null, sqlSession);
	}

	@Override
	public String getSendToBIALMessageUrl() throws CustomException {
		String data = fetchObject("communityPortalInvoicePostingUrl", null, sqlSession);
		return data;
	}
	
	@Override
	public BigInteger getirpPostingLoopCountLimit() throws CustomException {
		BigInteger data = fetchObject("irpPostingBatchLoopCountLimit", null, sqlSession);
		return data;
	}


	@Override
	public void updateCommunityPortalStatusAndErrorDescForNotPdAccount() throws CustomException {
		super.updateData("sqlUpdateCommunityPortalStatusAndErrorDescForNotPdAccount", "test", sqlSession);
	}

	@Override
	public void updateCommunityPortalStatusAndErrorDescForUserBial() throws CustomException {
		super.updateData("sqlUpdateCommunityPortalStatusAndErrorDescForUserBial", "test", sqlSession);
	}

}
