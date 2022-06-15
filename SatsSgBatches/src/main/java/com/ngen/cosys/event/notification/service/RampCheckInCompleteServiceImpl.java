/**
 * RampCheckInCompleteServiceImpl.java
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

import com.ngen.cosys.event.notification.dao.RampCheckInCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Ramp Check In Complete service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class RampCheckInCompleteServiceImpl implements RampCheckInCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(RampCheckInCompleteService.class);
   
   @Autowired
   RampCheckInCompleteDAO rampCheckInCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.RampCheckInCompleteService#getRampCheckInNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    */
   @Override
   public List<FlightEvents> getRampCheckInNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Ramp CheckIn not completed Flights - Data extraction");
      return rampCheckInCompleteDAO
            .getRampCheckInNotCompletedFlights(EventNotificationUtils.importFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.RampCheckInCompleteService#getRampCheckInCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getRampCheckInCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("Ramp CheckIn completed Flights After the last execution time - {}", lastExecutionTime);
      return rampCheckInCompleteDAO.getRampCheckInCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.importFlightEvents(eventType, lastExecutionTime));
   }

}
