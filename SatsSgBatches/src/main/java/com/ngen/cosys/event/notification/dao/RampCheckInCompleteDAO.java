/**
 * {@link RampCheckInCompleteDAO}
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
 * Ramp Check In Completed Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface RampCheckInCompleteDAO {

   /**
    * Ramp Check In not completed flight details
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getRampCheckInNotCompletedFlights(ImportFlightEvents importFlightEvents) throws CustomException;
   
   /**
    * Ramp Check In completed flights
    * 
    * @param importFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getRampCheckInCompletedFlightsAfterTheLastExecutionTime(ImportFlightEvents importFlightEvents)
         throws CustomException;
   
}
