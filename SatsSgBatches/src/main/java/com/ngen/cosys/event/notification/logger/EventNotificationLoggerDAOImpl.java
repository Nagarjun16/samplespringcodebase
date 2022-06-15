/**
 * {@link EventNotificationLoggerDAOImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.logger;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.event.notification.enums.EventNotificationSQL;
import com.ngen.cosys.event.notification.model.EventNotificationLog;
import com.ngen.cosys.event.notification.model.EventNotificationLogDetails;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * EventNotification Logger DAO Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class EventNotificationLoggerDAOImpl extends BaseDAO implements EventNotificationLoggerDAO {

   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationLoggerDAO.class);

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#selectEventNotificationJobLog(com.ngen.cosys.event.notification.model.EventTypeConfig)
    */
   @Override
   public EventNotificationLog selectEventNotificationJobLog(EventTypeConfig eventTypeConfig) throws CustomException {
      LOGGER.debug("Event Notification Job Log - Data Access layer extraction");
      return sqlSession.selectOne(EventNotificationSQL.SELECT_EVENT_NOTIFICATION_JOB_LOG.getQueryId(), eventTypeConfig);
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#insertEventNotificationJobLog(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public void insertEventNotificationJobLog(EventNotificationLog eventNotificationLog) throws CustomException {
      LOGGER.debug("Event Notification Job Log - Data Access layer insert");
      super.insertData(EventNotificationSQL.INSERT_EVENT_NOTIFICATION_JOB_LOG.getQueryId(), eventNotificationLog,
            sqlSession);
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#updateEventNotificationJobLog(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public void updateEventNotificationJobLog(EventNotificationLog eventNotificationLog) throws CustomException {
      LOGGER.debug("Event Notification Job Log - Data Access layer update");
      super.updateData(EventNotificationSQL.UPDATE_EVENT_NOTIFICATION_JOB_LOG.getQueryId(), eventNotificationLog,
            sqlSession);
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#updateEventNotificationSENTTimeJobLog(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public void updateEventNotificationSENTTimeJobLog(EventNotificationLog eventNotificationLog) throws CustomException {
      LOGGER.debug("Event Notification Job Log - Data Access layer update SENT Time");
      super.updateData(EventNotificationSQL.UPDATE_EVENT_NOTIFICATION_SENT_TIME_JOB_LOG.getQueryId(),
            eventNotificationLog, sqlSession);
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#selectEventNotificationJobDetailsLog(com.ngen.cosys.event.notification.model.EventNotificationLog)
    */
   @Override
   public List<EventNotificationLogDetails> selectEventNotificationJobDetailsLog(
         EventNotificationLog eventNotificationLog) throws CustomException {
      LOGGER.debug("Event Notification Job Details Log - Data Access layer extraction");
      return sqlSession.selectList(EventNotificationSQL.SELECT_EVENT_NOTIFICATION_JOB_DETAILS_LOG.getQueryId(),
            eventNotificationLog);
   }

   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#insertEventNotificationJobDetailsLog(java.util.List)
    */
   @Override
   public void insertEventNotificationJobDetailsLog(List<EventNotificationLogDetails> eventNotificationLogDetails)
         throws CustomException {
      LOGGER.debug("Event Notification Job Details Log - Data Access layer insert");
      super.insertList(EventNotificationSQL.INSERT_EVENT_NOTIFICATION_JOB_DETAILS_LOG.getQueryId(),
            eventNotificationLogDetails, sqlSession);
   }
   
   /**
    * @see com.ngen.cosys.event.notification.logger.EventNotificationLoggerDAO#updateEventNotificationJobDetailsLog(java.util.List)
    */
   @Override
   public void updateEventNotificationJobDetailsLog(List<EventNotificationLogDetails> eventNotificationLogDetails)
         throws CustomException {
      LOGGER.debug("Event Notification Job Details Log - Data Access layer update");
      super.updateData(EventNotificationSQL.UPDATE_EVENT_NOTIFICATION_JOB_DETAILS_LOG.getQueryId(),
            eventNotificationLogDetails, sqlSession);
   }

}
