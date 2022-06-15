/**
 * {@link IncomingESBMessageResendDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.util.LoggerUtil;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.enums.MessageResendSQL;
import com.ngen.cosys.message.resend.model.RerouteMessageLog;
import com.ngen.cosys.message.resend.model.OutgoingMessageLog;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Incoming Discard DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class RerouteMessageDAOImpl extends BaseDAO implements RerouteMessageDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(RerouteMessageDAO.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;

   /**
    * @see com.ngen.cosys.message.resend.dao.IncomingDiscardDAO#getDiscardMessages()
    */
   @Override
   public List<RerouteMessageLog> getDiscardMessages()
         throws CustomException {
      LOGGER.info("Incoming ESB Message Resend DAO :: Processing Message log details - {}");
      RerouteMessageLog discardLog = new RerouteMessageLog();
      discardLog.setMessageStatus(MessageResendConstants.REROUTE);
      return sqlSessionROI.selectList(MessageResendSQL.SQL_SELECT_INCOMING_DISCARD_MESSAGE_LOG.getQueryId(),discardLog);
   }
   
   @Override
   public void updateIncomingMessage(BigInteger interfaceIncomingMessageLogId) throws CustomException {
      LOGGER.debug(
            LoggerUtil.getLoggerMessage(this.getClass().getName(), "updateIncomingMessage", Level.DEBUG, null, null));
      RerouteMessageLog discardLog = new RerouteMessageLog();
      discardLog.setMessageStatus(MessageResendConstants.DIVERTED);
      discardLog.setInterfaceIncomingMessageLogId(interfaceIncomingMessageLogId);
      sqlSession.update(MessageResendSQL.SQL_UPDATE_INCOMING_MESSAGE_LOG.getQueryId(), discardLog);
   }
   
   public void logOutgoingMessage(OutgoingMessageLog outgoingMessage) throws CustomException {
	   LOGGER.debug(
	            LoggerUtil.getLoggerMessage(this.getClass().getName(), "logOutgoingMessage", Level.DEBUG, null, null));
	      sqlSession.insert(MessageResendSQL.SQL_INSERT_OUTGOING_MESSAGE_LOG.getQueryId(), outgoingMessage);
   }
   
}
