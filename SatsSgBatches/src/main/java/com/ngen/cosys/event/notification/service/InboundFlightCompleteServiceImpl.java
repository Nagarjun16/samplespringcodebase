/**
 * InboundFlightCompleteServiceImpl.java
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

import com.ngen.cosys.event.notification.dao.InboundFlightCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Inbound Flight Complete service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class InboundFlightCompleteServiceImpl implements InboundFlightCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(InboundFlightCompleteService.class);

   @Autowired
   InboundFlightCompleteDAO inboundFlightCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.InboundFlightCompleteService#getInboundNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    */
   @Override
   public List<FlightEvents> getInboundNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Inbound not completed Flights - Data extraction");
      return inboundFlightCompleteDAO
            .getInboundNotCompletedFlights(EventNotificationUtils.importFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.InboundFlightCompleteService#getInboundCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getInboundCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("Inbound completed Flights After the last execution time - {}", lastExecutionTime);
      return inboundFlightCompleteDAO.getInboundCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.importFlightEvents(eventType, lastExecutionTime));
   }
   
}
