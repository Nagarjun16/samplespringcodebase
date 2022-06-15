/**
 * FlightDLSCompleteServiceImpl.java
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

import com.ngen.cosys.event.notification.dao.FlightDLSCompleteDAO;
import com.ngen.cosys.event.notification.dao.InboundFlightCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Flight DLS Complete service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class FlightDLSCompleteServiceImpl implements FlightDLSCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightDLSCompleteService.class);
   
   @Autowired
   FlightDLSCompleteDAO flightDLSCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.FlightDLSCompleteService#getDLSNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    */
   @Override
   public List<FlightEvents> getDLSNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("DLS not completed Flights - Data extraction");
      return flightDLSCompleteDAO
            .getDLSNotCompletedFlights(EventNotificationUtils.exportFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.FlightDLSCompleteService#getDLSCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getDLSCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("DLS completed Flights After the last execution time - {}", lastExecutionTime);
      return flightDLSCompleteDAO.getDLSCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.exportFlightEvents(eventType, lastExecutionTime));
   }

}
