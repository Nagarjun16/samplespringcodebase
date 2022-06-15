/**
 * OperativeFlightService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 August, 2017 NIIT -
 */
package com.ngen.cosys.flight.service;

import java.util.List;

import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightFct;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.model.OperativeFlightSegment;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface takes care of the responsibilities related to the
 * OperativeFlight Flight service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface OperativeFlightService {

   /**
    * It manages operative flight including all child functionality.
    * 
    * @param operatingFlight
    * @return
    * @throws CustomException
    */
   OperativeFlight maintain(final OperativeFlight operatingFlight) throws CustomException;


   /**
    * It checks whether Flight exist or not.
    * 
    * @param operatingFlight
    * @return
    * @throws CustomException
    */
   boolean isFlightExists(OperativeFlight operatingFlight) throws CustomException;



   List<OperativeFlightSegment> prepareSegments(List<OperativeFlightLeg> flightLegs);

  

}