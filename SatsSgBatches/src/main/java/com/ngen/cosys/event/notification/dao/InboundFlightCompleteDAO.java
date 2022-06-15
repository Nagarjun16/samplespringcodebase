/**
 * {@link InboundFlightCompleteDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.dao;

import java.util.List;

import com.ngen.cosys.event.notification.model.FlightEvents;
import com.ngen.cosys.event.notification.model.ImportFlightEvents;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Inbound Flight Complete Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface InboundFlightCompleteDAO {

   /**
    * Inbound Flight not completed flight details
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getInboundNotCompletedFlights(ImportFlightEvents importFlightEvents) throws CustomException;
   
   /**
    * Inbound completed flights
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getInboundCompletedFlightsAfterTheLastExecutionTime(ImportFlightEvents importFlightEvents)
         throws CustomException;
   
}
