/**
 * Repository interface component which has business methods to process through
 * transit shipments finalized
 */
package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

public interface ThroughTransitShipmentFinalizeDAO {

   /**
    * Method to get shipments which are finalized in through transit working list
    * and required messages have not been triggered yet
    * 
    * @return List<InboundShipmentBreakDownCompleteStoreEvent> - Shipments List
    * @throws CustomException
    */
   List<InboundShipmentBreakDownCompleteStoreEvent> get() throws CustomException;

   boolean isFlighHandledInSystem(InboundShipmentBreakDownCompleteStoreEvent event) throws CustomException;

   boolean isDataSyncCREnabled() throws CustomException;

   /**
    * Method to get suffixes which are not sent in TSM
    * 
    * @param shipmentId
 * @param bigInteger 
    * @return List<String> - List of suffixes
    * @throws CustomException
    */
   List<String> getDataSyncPartSuffix(BigInteger shipmentId) throws CustomException;

}