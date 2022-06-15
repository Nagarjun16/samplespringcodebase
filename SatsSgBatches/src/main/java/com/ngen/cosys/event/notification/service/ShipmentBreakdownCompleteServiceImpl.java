/**
 * ShipmentBreakdownCompleteServiceImpl.java
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

import com.ngen.cosys.event.notification.dao.ShipmentBreakdownCompleteDAO;
import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.ImportFlightEvents;
import com.ngen.cosys.event.notification.model.ShipmentEvents;
import com.ngen.cosys.event.notification.util.EventNotificationUtils;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Shipment Breakdown Service implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class ShipmentBreakdownCompleteServiceImpl implements ShipmentBreakdownCompleteService {

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentBreakdownCompleteService.class);
   
   @Autowired
   ShipmentBreakdownCompleteDAO shipmentBreakdownCompleteDAO;
   
   /**
    * @see com.ngen.cosys.event.notification.service.ShipmentBreakdownCompleteService#getBreakdownNotCompletedFlights(com.ngen.cosys.event.notification.enums.EntityEventTypes)
    * 
    */
   @Override
   public List<FlightEvents> getBreakdownNotCompletedFlights(EntityEventTypes eventType) throws CustomException {
      LOGGER.debug("Breakdown not completed Flights - Data extraction");
      return shipmentBreakdownCompleteDAO
            .getBreakdownNotCompletedFlights(EventNotificationUtils.importFlightEvents(eventType, null));
   }

   /**
    * @see com.ngen.cosys.event.notification.service.ShipmentBreakdownCompleteService#getBreakdownCompletedFlightsAfterTheLastExecutionTime(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      java.time.LocalDateTime)
    */
   @Override
   public List<FlightEvents> getBreakdownCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException {
      LOGGER.debug("Breakdown completed Flights After the last execution time - {}", lastExecutionTime);
      return shipmentBreakdownCompleteDAO.getBreakdownCompletedFlightsAfterTheLastExecutionTime(
            EventNotificationUtils.importFlightEvents(eventType, lastExecutionTime));
   }
   
   /**
    * @see com.ngen.cosys.event.notification.service.ShipmentBreakdownCompleteService#getBreakdownNotCompletedShipments(com.ngen.cosys.event.notification.enums.EntityEventTypes,
    *      com.ngen.cosys.event.notification.model.ImportFlightEvents)
    * 
    */
   @Override
   public List<ShipmentEvents> getBreakdownNotCompletedShipments(EntityEventTypes eventType,
         ImportFlightEvents importFlightEvents) throws CustomException {
      LOGGER.debug("Breakdown Not Completed Shipments - Data extraction ");
      // TODO: Shipment Level
      return null;
   }

   /**
    * @see com.ngen.cosys.event.notification.service.ShipmentBreakdownCompleteService#getBreakdownCompletedShipmentsAfterTheLastExecutionTime(java.time.LocalDateTime)
    * 
    */
   @Override
   public List<ShipmentEvents> getBreakdownCompletedShipmentsAfterTheLastExecutionTime(LocalDateTime lastExecutionTime)
         throws CustomException {
      // TODO: Shipment Level
      return null;
   }

}
