/**
 * {@link DashboardConfig}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.config;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ngen.cosys.dashboard.enums.DashboardSQL;
import com.ngen.cosys.dashboard.model.EventNotification;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

/**
 * Dashboard configuration
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class DashboardConfig {

   private static final Logger LOGGER = LoggerFactory.getLogger(DashboardConfig.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;
   
   /**
    * Get Event Notification Configuration details for Dashboard
    * 
    * @param module
    *       Event flow (EXPORT/IMPORT)
    * @return
    */
   public List<EventNotification> getEventNotificationConfiguration(String module) {
      LOGGER.warn("Dashboard Config # GET Event Notification configuration - Module - {}", module);
      // Parameter Type
      EventNotification notification = new EventNotification();
      notification.setModule(module);
      // Result set
      return sqlSessionROI.selectList(DashboardSQL.SELECT_EVENT_NOTIFICATION_CONFIGURATION.getQueryId(), notification);
   }
   
}
