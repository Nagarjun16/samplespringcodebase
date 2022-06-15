/**
 * ShipmentBreakdownCompleteService.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.ImportFlightEvents;
import com.ngen.cosys.event.notification.model.ShipmentEvents;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for Shipment breakdown complete Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface ShipmentBreakdownCompleteService {

   /**
    * Breakdown not completed Flights after Flight Arrival of {} duration
    * 
    * @param eventType
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getBreakdownNotCompletedFlights(EntityEventTypes eventType) throws CustomException;
   
   /**
    * GET breakdown completed flights after the last RUN
    * 
    * @param eventType
    * @param lastExecutionTime
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getBreakdownCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException;
   
   /**
    * Breakdown not completed Shipments after Flight Arrival of {} duration
    * 
    * @param eventType
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<ShipmentEvents> getBreakdownNotCompletedShipments(EntityEventTypes eventType,
         ImportFlightEvents importFlightEvents) throws CustomException;
   
   /**
    * GET breakdown completed shipments after the last RUN 
    * 
    * @param lastExecutionTime
    * @return
    * @throws CustomException
    */
   List<ShipmentEvents> getBreakdownCompletedShipmentsAfterTheLastExecutionTime(LocalDateTime lastExecutionTime)
         throws CustomException;
   
}
