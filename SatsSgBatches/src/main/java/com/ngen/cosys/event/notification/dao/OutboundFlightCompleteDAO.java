/**
 * {@link OutboundFlightCompleteDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.dao;

import java.util.List;

import com.ngen.cosys.event.notification.model.ExportFlightEvents;
import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Outbound Flight Completed Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface OutboundFlightCompleteDAO {

   /**
    * Outbound Flight not completed flight details
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getOutboundNotCompletedFlights(ExportFlightEvents exportFlightEvents) throws CustomException;

   /**
    * Outbound completed flights
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getOutboundCompletedFlightsAfterTheLastExecutionTime(ExportFlightEvents exportFlightEvents)
         throws CustomException;
   
}
