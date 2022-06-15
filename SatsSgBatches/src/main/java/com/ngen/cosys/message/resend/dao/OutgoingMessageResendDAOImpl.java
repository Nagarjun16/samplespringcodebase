/**
 * {@link OutgoingMessageResendDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.enums.MessageResendSQL;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Outgoing Message Resend DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class OutgoingMessageResendDAOImpl extends BaseDAO implements OutgoingMessageResendDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingMessageResendDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * @see com.ngen.cosys.message.resend.dao.OutgoingMessageResendDAO#getOutgoingErrorMessageLogDetails()
    */
   @Override
   public List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogDetails() throws CustomException {
      LOGGER.info("Outgoing Message Resend DAO :: Message log details - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_OUTGOING_MESSAGE_ERROR_LOG_DETAILS.getQueryId());
   }

   /**
    * @see com.ngen.cosys.message.resend.dao.OutgoingMessageResendDAO#updateOutgoingMessagesProcessingState(java.util.List)
    */
   @Override
   public void updateOutgoingMessagesProcessingState(List<OutgoingErrorMessageLog> outgoingErrorMessages)
         throws CustomException {
      LOGGER.info("Outgoing Message Resend DAO :: UPDATE Message Processing State - {}");
      for (OutgoingErrorMessageLog errorMessage : outgoingErrorMessages) {
         updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_MESSAGE_PROCESSING_STATE.getQueryId(),
               errorMessage.getOutgoingMessageLogId(), sqlSession);
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.OutgoingMessageResendDAO#updateResentOutgoingMessageLog(java.util.List)
    */
   @Override
   public void updateResentOutgoingMessageLog(List<OutgoingErrorMessageLog> resentErrorMessageLogs) throws CustomException {
      LOGGER.info("Outgoing Message Resend DAO :: UPDATE Resent Message log - {}");
      for (OutgoingErrorMessageLog errorMessageLog : resentErrorMessageLogs) {
         if (errorMessageLog.isProcessed()) { // Processed Log Status UPDATE
            // Outgoing Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_RESENT_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
            errorMessageLog.getOutgoingResendMessageLog().setAlertStatus(getAlertStatus(retryLimit));
            // Outgoing Error Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_FAILED_ERROR_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            // Resend Message Log Stats Update
            updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                  errorMessageLog.getOutgoingResendMessageLog(), sqlSession);
         }
      }
   }

   /**
    * @see com.ngen.cosys.message.resend.dao.OutgoingMessageResendDAO#updateFailedOutgoingErrorMessageLog(java.util.List)
    */
   @Override
   public void updateFailedOutgoingErrorMessageLog(List<OutgoingErrorMessageLog> failedErrorMessageLogs)
         throws CustomException {
      LOGGER.info("Outgoing Message Resend DAO :: UPDATE Failed Error Message log - {}");
      for (OutgoingErrorMessageLog errorMessageLog : failedErrorMessageLogs) {
         if (!errorMessageLog.isProcessed()) { // Not Processed Log Status UPDATE
            // Outgoing Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_RESENT_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            // Outgoing Error Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_FAILED_ERROR_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
            errorMessageLog.getOutgoingResendMessageLog().setAlertStatus(getAlertStatus(retryLimit));
            // Resend Message Log Stats Update
            int updateCount = updateData(MessageResendSQL.SQL_UPDATE_OUTGOING_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                  errorMessageLog.getOutgoingResendMessageLog(), sqlSession);
            if (updateCount == 0) {
               insertData(MessageResendSQL.SQL_INSERT_OUTGOING_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                     errorMessageLog.getOutgoingResendMessageLog(), sqlSession);
            }
         }
      }
   }
   
   /**
    * @param retryLimit
    * @return
    */
   private String getAlertStatus(Integer retryLimit) {
      String alertStatus = null;
      if (Objects.nonNull(retryLimit)) {
         alertStatus = (retryLimit == 2) ? MessageResendConstants.WARNING_ALERT : MessageResendConstants.CRITICAL_ALERT;
      } else {
         alertStatus = MessageResendConstants.INFO_ALERT;
      }
      return alertStatus;
   }

 @Override
 public List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogDetailsAisats() throws CustomException {
	 LOGGER.info("Outgoing Message Resend DAO :: Message log details - {}");
     return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_AISATSOUTGOING_MESSAGE_ERROR_LOG_DETAILS.getQueryId());
}

}
