/**
 * 
 * TransferManifestDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 14 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.mail.transfermanifest.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.transfermanifest.model.SearchTransferManifestDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierResponse;

/**
 * This class takes care of the Transfer Manifest services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("transferManifestDAO")
public class TransferManifestDAOImpl extends BaseDAO implements TransferManifestDAO{

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public List<TransferCarrierResponse> fetchTransferManifestDetails(SearchTransferManifestDetails search)
         throws CustomException {
    
      return super.fetchList("fetchTMDetails", search, sqlSession);
   }

   @Override
   public String getTrmCount(TransferCarrierDetails e) throws CustomException {
      return fetchObject("getCountBaseOnCarrierCodeFrom", e, sqlSession);
   }
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_TRANSFER_MANIEST)
   @Override
   public void insertTransferManifestByMail(TransferCarrierDetails mailInfo) throws CustomException {
   super.insertData("insertTransferManifestMail", mailInfo, sqlSession);
   }

   @Override
   public void insertTransferManifestInfoByMail(TransferCarrierDetails mailInfo) throws CustomException {
      super.insertData("insertTransferManifestMailInfo", mailInfo, sqlSession);
   }
//   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_TRANSFER_MANIEST)
	@Override
	public void updateLyingListOfShipments(List<TransferCarrierDetails> shipmentIds) throws CustomException {
      super.updateData("updateLyingListOfShipments", shipmentIds, sqlSession);	
	}
}
