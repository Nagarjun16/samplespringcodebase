/**
 * Interface component which has business methods to process through transit
 * shipments finalized
 */
package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface ThroughTransitShipmentFinalizeService {

   /**
    * Method to get shipments information which has been through transit finalized
    * and invoke events to be fired
    * 
    * @throws CustomException
    */
   void process() throws CustomException;

}