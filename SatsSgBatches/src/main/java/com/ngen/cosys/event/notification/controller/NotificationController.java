/**
 * {@link NotificationController}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.event.notification.common.NotificationEventTypes;
import com.ngen.cosys.event.notification.job.EventNotificationFrequencyJob;
import com.ngen.cosys.event.notification.model.NotificationEvent;
import com.ngen.cosys.event.notification.type.BreakdownComplete;
import com.ngen.cosys.event.notification.type.FlightDLSComplete;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

/**
 * Notification Controller holds of Temp Event Notification testing
 * 
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(path = "/notification-event/api")
public class NotificationController {

   private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
   
   @Autowired
   BeanFactory beanFactory;
   
   @Autowired
   @Qualifier(value = NotificationEventTypes.DLS_COMPLETE)
   FlightDLSComplete flightDLSComplete;
   
   @Autowired
   @Qualifier(value = NotificationEventTypes.BREAKDOWN_COMPLETE)
   BreakdownComplete breakdownComplete;
   
   @Autowired
   EventNotificationFrequencyJob notificationFrequencyJob;
   
   /**
    * @param notificationEvent
    * @return
    * @throws CustomException
    */
   @SuppressWarnings("unchecked")
   @ApiOperation(value = "Notification Event Service")
   @PostRequest(value = "/testing", method = RequestMethod.POST)
   public BaseResponse<NotificationEvent> notificationEventService(@RequestBody NotificationEvent notificationEvent)
         throws CustomException {
      LOGGER.debug("Notification Event Services are invoked");
      BaseResponse<NotificationEvent> response = beanFactory.getBean(BaseResponse.class);
      flightDLSComplete.flightDLSCompleteTesting();
      // breakdownComplete.breakdownCompleteTesting();
      // notificationFrequencyJob.notificationFrequencyTesting();
      return response;
   }
   
}
