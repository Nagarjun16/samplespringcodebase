/**
 * {@link IncomingESBMessageResendDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.dao;

import java.math.BigInteger;
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
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Incoming ESB Message Resend DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class IncomingESBMessageResendDAOImpl extends BaseDAO implements IncomingESBMessageResendDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingESBMessageResendDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;

   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#getIncomingESBProcessingMessageLogDetails(com.ngen.cosys.message.resend.model.IncomingMessageSequence)
    */
   @Override
   public List<IncomingESBMessageLog> getIncomingESBProcessingMessageLogDetails(IncomingMessageSequence messageSequence)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: Processing Message log details - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_INCOMING_ESB_PROCESSING_MESSAGE_LOG.getQueryId(),
            messageSequence);
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#getIncomingESBInitiatedMessageLogDetails(com.ngen.cosys.message.resend.model.IncomingMessageSequence)
    */
   @Override
   public List<IncomingESBMessageLog> getIncomingESBInitiatedMessageLogDetails(IncomingMessageSequence messageSequence)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: Initiated Message log details - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_INCOMING_ESB_INITIATED_MESSAGE_LOG.getQueryId(),
            messageSequence);
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#getIncomingESBErrorMessageLogDetails()
    */
   @Override
   public List<IncomingESBErrorMessageLog> getIncomingESBErrorMessageLogDetails() throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: Error Message log details - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_INCOMING_ESB_MESSAGE_ERROR_LOG_DETAILS.getQueryId());
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#verifyShipmentDuplicateReference(com.ngen.cosys.message.resend.model.IncomingESBMessageLog)
    */
   @Override
   public List<BigInteger> verifyShipmentDuplicateReference(IncomingESBMessageLog incomingESBMessage)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: Verify Shipment Duplicate Reference - {}");
      return sqlSessionROI.selectList(MessageResendSQL.SQL_VERIFY_SHIPMENT_DUPLICATE_REFERENCE.getQueryId(),
            incomingESBMessage);
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateESBMessagesReProcessingState(java.util.List)
    */
   @Override
   public void updateESBMessagesReProcessingState(List<IncomingESBMessageLog> processingESBMessages)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: UPDATE Messages Re-Processing state - {}");
      for (IncomingESBMessageLog messageLog : processingESBMessages) {
         updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_MESSAGE_REPROCESSING_STATE.getQueryId(),
               messageLog.getIncomingESBMessageLogId(), sqlSession);
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateESBMessagesProcessingState(java.util.List)
    */
   @Override
   public void updateESBMessagesProcessingState(List<IncomingESBMessageLog> initiatedESBMessages)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: UPDATE Messages processing state - {}");
      for (IncomingESBMessageLog messageLog : initiatedESBMessages) {
         if (messageLog.isDuplicate()) {
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_MESSAGE_DUPLICATE_REFERENCE.getQueryId(),
                  messageLog.getIncomingESBMessageLogId(), sqlSession);
         } else {
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_MESSAGE_PROCESSING_STATE.getQueryId(),
                  messageLog.getIncomingESBMessageLogId(), sqlSession);
         }
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateESBErrorMessagesProcessingState(java.util.List)
    */
   @Override
   public void updateESBErrorMessagesProcessingState(List<IncomingESBErrorMessageLog> incomingESBErrorMessages)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: UPDATE Error Messages processing state - {}");
      for (IncomingESBErrorMessageLog errorMessageLog : incomingESBErrorMessages) {
         updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_MESSAGE_PROCESSING_STATE.getQueryId(),
               errorMessageLog.getIncomingESBMessageLogId(), sqlSession);
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateResentIncomingESBMessageSequenceLog(java.util.List)
    */
   @Override
   public void updateResentIncomingESBMessageSequenceLog(List<IncomingESBMessageLog> messageLogs)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: UPDATE Resent Sequence Message log - {}");
      for (IncomingESBMessageLog messageLog : messageLogs) {
         if (messageLog.isDuplicate()) {
            continue;
         }
         updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_RESENT_SEQUENCE_MESSAGE_LOG.getQueryId(), messageLog,
               sqlSession);
         if (!messageLog.isProcessed()) {
            // Incoming ESB Error Message Log CREATE
            insertData(MessageResendSQL.SQL_INSERT_INCOMING_ESB_ERROR_MESSAGE_LOG.getQueryId(),
                  getIncomingESBErrorMessageLog(messageLog), sqlSession);
         }
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateResentIncomingESBMessageLog(java.util.List)
    */
   @Override
   public void updateResentIncomingESBMessageLog(List<IncomingESBErrorMessageLog> resentErrorMessageLogs)
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: UPDATE Resent Message log - {}");
      for (IncomingESBErrorMessageLog errorMessageLog : resentErrorMessageLogs) {
         if (errorMessageLog.isProcessed()) { // Processed Log Status UPDATE
            // Incoming ESB Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_RESENT_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            Integer retryLimit = errorMessageLog.getIncomingESBResendMessageLog().getRetryLimit();
            errorMessageLog.getIncomingESBResendMessageLog().setAlertStatus(getAlertStatus(retryLimit));
            // Incoming ESB Error Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_FAILED_ERROR_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            // Resend Message Log Stats Update
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                  errorMessageLog.getIncomingESBResendMessageLog(), sqlSession);
         }
      }
   }

   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO#updateFailedIncomingESBErrorMessageLog(java.util.List)
    */
   @Override
   public void updateFailedIncomingESBErrorMessageLog(List<IncomingESBErrorMessageLog> failedErrorMessageLogs)
         throws CustomException {
      LOGGER.info("Incoming Message Resend DAO :: UPDATE Failed Error Message log - {}");
      for (IncomingESBErrorMessageLog errorMessageLog : failedErrorMessageLogs) {
         if (!errorMessageLog.isProcessed()) { // Processed Log Status UPDATE
            // Incoming ESB Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_RESENT_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            // Incoming ESB Error Message Log Status Update
            updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_FAILED_ERROR_MESSAGE_LOG.getQueryId(), errorMessageLog,
                  sqlSession);
            Integer retryLimit = errorMessageLog.getIncomingESBResendMessageLog().getRetryLimit();
            errorMessageLog.getIncomingESBResendMessageLog().setAlertStatus(getAlertStatus(retryLimit));
            // Resend Message Log Stats Update
            int updateCount = updateData(MessageResendSQL.SQL_UPDATE_INCOMING_ESB_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                  errorMessageLog.getIncomingESBResendMessageLog(), sqlSession);
            if (updateCount == 0) {
               insertData(MessageResendSQL.SQL_INSERT_INCOMING_ESB_RESEND_MESSAGE_LOG_STATS.getQueryId(),
                     errorMessageLog.getIncomingESBResendMessageLog(), sqlSession);
            }
         }
      }
   }
   
   /**
    * @param messageLog
    * @return
    */
   private IncomingESBErrorMessageLog getIncomingESBErrorMessageLog(IncomingESBMessageLog messageLog) {
      IncomingESBErrorMessageLog errorMessageLog = new IncomingESBErrorMessageLog();
      errorMessageLog.setIncomingESBMessageLogId(messageLog.getIncomingESBMessageLogId());
      errorMessageLog.setErrorCode(MessageResendConstants.FAILED);
      errorMessageLog.setErrorMessage(messageLog.getExceptionMessage());
      return errorMessageLog;
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

}
