/**
 * {@link FlightDLSCompleteDAO}
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
 * Flight DLS Completed Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface FlightDLSCompleteDAO {

   /**
    * DLS not completed flight details
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getDLSNotCompletedFlights(ExportFlightEvents exportFlightEvents) throws CustomException;

   /**
    * DLS completed flights
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getDLSCompletedFlightsAfterTheLastExecutionTime(ExportFlightEvents exportFlightEvents)
         throws CustomException;
   
}
