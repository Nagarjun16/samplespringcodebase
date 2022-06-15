/**
 * InboundFlightCompleteService.java
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
 * This interface is used for Inbound Flight complete Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface InboundFlightCompleteService {

   /**
    * Inbound Flight not completed Flights after Flight Arrival of {} duration
    * 
    * @param eventType
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getInboundNotCompletedFlights(EntityEventTypes eventType) throws CustomException;

   /**
    * GET Inbound completed flights after the last RUN
    * 
    * @param eventType
    * @param lastExecutionTime
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getInboundCompletedFlightsAfterTheLastExecutionTime(EntityEventTypes eventType,
         LocalDateTime lastExecutionTime) throws CustomException;
   
}
