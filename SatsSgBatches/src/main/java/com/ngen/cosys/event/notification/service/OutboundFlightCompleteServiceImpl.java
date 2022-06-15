/**
 * OutboundFlightCompleteServiceImpl.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.event.notification.dao.OutboundFlightCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Outbound Flight Complete service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class OutboundFlightCompleteServiceImpl implements OutboundFlightCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(OutboundFlightCompleteService.class);

   @Autowired
   OutboundFlightCompleteDAO outboundFlightCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.OutboundFlightCompleteService#getOutboundNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    */
   @Override
   public List<FlightEvents> getOutboundNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Outbound not completed Flights - Data extraction");
      return outboundFlightCompleteDAO
            .getOutboundNotCompletedFlights(EventNotificationUtils.exportFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.OutboundFlightCompleteService#getOutboundCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getOutboundCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("Manifest completed Flights After the last execution time - {}", lastExecutionTime);
      return outboundFlightCompleteDAO.getOutboundCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.exportFlightEvents(eventType, lastExecutionTime));
   }
   
}
