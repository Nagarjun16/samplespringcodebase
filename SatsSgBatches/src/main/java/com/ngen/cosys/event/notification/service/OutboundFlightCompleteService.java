/**
 * OutboundFlightCompleteService.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for Outbound Flight complete Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface OutboundFlightCompleteService {

   /**
    * Outbound Flight not completed Flights before Flight Departure of {} duration
    * 
    * @param eventType
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getOutboundNotCompletedFlights(EntityEventTypes eventType) throws CustomException;

   /**
    * GET Outbound completed flights after the last RUN
    * 
    * @param eventType
    * @param lastExecutionTime
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getOutboundCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException;
   
}
