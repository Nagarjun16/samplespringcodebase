/**
 * {@link ShipmentBreakdownCompleteDAO}
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
 * Shipment Breakdown Completed Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface ShipmentBreakdownCompleteDAO {

   /**
    * Breakdown not completed flight details
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getBreakdownNotCompletedFlights(ImportFlightEvents importFlightEvents) throws CustomException;
   
   /**
    * breakdown completed flights
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getBreakdownCompletedFlightsAfterTheLastExecutionTime(ImportFlightEvents importFlightEvents)
         throws CustomException;
   
}
