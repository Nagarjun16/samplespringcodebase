/**
 * {@link FlightManifestCompleteDAO}
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
 * Flight Manifest Completed Repository Implementation
 * 
 * @author NIIT Technologies Ltd
 */
public interface FlightManifestCompleteDAO {

   /**
    * Manifest not completed flight details
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getManifestNotCompletedFlights(ExportFlightEvents exportFlightEvents) throws CustomException;

   /**
    * Manifest completed flights
    * 
    * @param exportFlightEvents
    * @return
    * @throws CustomException
    */
   List<FlightEvents> getManifestCompletedFlightsAfterTheLastExecutionTime(ExportFlightEvents exportFlightEvents)
         throws CustomException;
   
}
