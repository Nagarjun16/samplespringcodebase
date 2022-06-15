/**
 * EventNotificationConfig.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.config;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.enums.EventEntity;
import com.ngen.cosys.event.notification.enums.EventNotificationSQL;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.event.notification.model.EventTypeConfig;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * This class is used for Event Notification Configuration
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository
public class EventNotificationConfig {

   private static final Logger LOGGER = LoggerFactory.getLogger(EventNotificationConfig.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   private static final String IMPORT = "IMPORT";
   private static final String EXPORT = "EXPORT";
   
   /**
    * GET Config Details
    * 
    * @param entity
    * @param eventType
    * @return
    */
   public boolean eventConfigured(EventEntity entity, EntityEventTypes eventType) {
      LOGGER.warn("Event Config Details : Entity - {}, Event Name - {}", entity, eventType.value());
      EventTypeConfig config = sqlSession.selectOne(EventNotificationSQL.SELECT_EVENT_NOTIFICATION_CONFIG.getQueryId(),
            getEventConfiguration(entity, eventType));
      //
      return Objects.nonNull(config) ? config.isActive() : false;
   }
   
   /**
    * GET Event Notification Configuration Details
    * 
    * @param entity
    * @param eventType
    * @return EventNotificationModel
    * @throws CustomException
    */
   public EventNotificationModel getDetails(EventEntity entity, EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Event Notification GET Details for comparison");
      return sqlSession.selectOne(EventNotificationSQL.SELECT_EVENT_NOTIFICATION_DETAILS.getQueryId(),
            getEventConfiguration(entity, eventType));
   }
   
   /**
    * GET Event Notification Frequency Job Details
    * 
    * @return
    * @throws CustomException
    */
   public List<EventTypeConfig> getNotificationFrequencyJobDetails() throws CustomException {
      LOGGER.debug("Event Notification frequency job details");
      return sqlSession.selectList(EventNotificationSQL.SELECT_EVENT_NOTIFICATION_FREQUENCY_JOB_DETAILS.getQueryId());
   }
   
   /**
    * Last Executed Event Time
    * 
    * @param entity
    * @param eventType
    * @return
    * @throws CustomException
    */
   public LocalDateTime lastExecutedEventTime(EventTypeConfig eventTypeConfig) throws CustomException {
      LOGGER.debug("Event Notification Last Executed Event Time");
      Timestamp lastExecutedTime = sqlSession.selectOne(
            EventNotificationSQL.SELECT_EVENT_NOTIFICATION_LAST_EXECUTION_TIME.getQueryId(), eventTypeConfig);
      return Objects.nonNull(lastExecutedTime) ? lastExecutedTime.toLocalDateTime() : null;
   }
   
   /**
    * GET Event Configuration
    * 
    * @param entity
    * @param eventType
    * @return
    */
   private EventTypeConfig getEventConfiguration(EventEntity entity, EntityEventTypes eventType) {
      EventTypeConfig eventConfig = new EventTypeConfig();
      eventConfig.setEntity(entity.name());
      eventConfig.setEventName(eventType.value());
      boolean importEvent = EventNotificationUtils.importEvent(eventType.value());
      eventConfig.setEventModule(importEvent ? IMPORT : EXPORT);
      return eventConfig;
   }
   
}
