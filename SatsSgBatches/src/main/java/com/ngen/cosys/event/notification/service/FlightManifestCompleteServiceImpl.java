/**
 * FlightManifestCompleteServiceImpl.java
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

import com.ngen.cosys.event.notification.dao.FlightManifestCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Flight Manifest Complete service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class FlightManifestCompleteServiceImpl implements FlightManifestCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightManifestCompleteService.class);
   
   @Autowired
   FlightManifestCompleteDAO flightManifestCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.FlightManifestCompleteService#getManifestNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    */
   @Override
   public List<FlightEvents> getManifestNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Manifest not completed Flights - Data extraction");
      return flightManifestCompleteDAO
            .getManifestNotCompletedFlights(EventNotificationUtils.exportFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.FlightManifestCompleteService#getManifestCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getManifestCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("Manifest completed Flights After the last execution time - {}", lastExecutionTime);
      return flightManifestCompleteDAO.getManifestCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.exportFlightEvents(eventType, lastExecutionTime));
   }

}
