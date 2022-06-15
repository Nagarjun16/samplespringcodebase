/**
 * {@link MailResendDAOImpl}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.email.enums.MailResendSQL;
import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Mail Resend DAO Implementation
 *
 */
@Repository
public class MailResendDAOImpl implements MailResendDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailResendDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * @see com.ngen.cosys.email.dao.MailResendDAO#getListOfFailedMailDetails()
    */
   @Override
   public List<MailResendDetail> getListOfFailedMailDetails() throws CustomException {
      LOGGER.debug("Mail Resend DAO :: GET list of failed mail details - {}");
      return sqlSessionROI.selectList(MailResendSQL.SELECT_FAILED_MAIL_DETAILS.getQueryId());
   }

   /**
    * @see com.ngen.cosys.email.dao.MailResendDAO#getListOfFailedWrongMailAddressDetails()
    */
   @Override
   public List<MailResendDetail> getListOfFailedWrongMailAddressDetails() throws CustomException {
      LOGGER.debug("Mail Resend DAO :: GET list of failed wrong mail address details - {}");
      return sqlSessionROI.selectList(MailResendSQL.SELECT_FAILED_WRONG_MAIL_ADDRESS_DETAILS.getQueryId());
   }

   /**
    * @see com.ngen.cosys.email.dao.MailResendDAO#updateResendMailStatus(java.util.List)
    */
   @Override
   public void updateResendMailStatus(List<MailResendDetail> resendMailDetails) throws CustomException {
      LOGGER.debug("Mail Resend DAO :: UPDATE Resend mail status - {}");
      for (MailResendDetail mailResendDetail : resendMailDetails) {
         sqlSession.update(MailResendSQL.UPDATE_RESEND_MAIL_STATUS.getQueryId(), mailResendDetail);
      }
   }

   /**
    * @see com.ngen.cosys.email.dao.MailResendDAO#getNotifyMailAddressForRejectedMails()
    */
   @Override
   public String getNotifyMailAddressForRejectedMails() throws CustomException {
      LOGGER.debug("Mail Resend DAO :: NOTIFY Resend mail status - {}");
      return sqlSessionROI.selectOne(MailResendSQL.NOTIFY_MAIL_ADDRESS_FOR_REJECTED_MAILS.getQueryId());
   }

}
