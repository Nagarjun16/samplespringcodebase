/**
 * This is a service interface for handling functionalities on capturing break
 * down/location information of shipment/flight
 */
package com.ngen.cosys.impbd.shipment.breakdown.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundUldFlightModel;

public interface InboundBreakdownService {

   /**
    * Method to get shipment information for capturing location information
    * 
    * @param inboundBreakdownModel
    * @return InboundBreakdownModel - Shipment Information
    * @throws CustomException
    */
   InboundBreakdownModel get(InboundBreakdownModel inboundBreakdownModel) throws CustomException;

   /**
    * Method to capture location information
    * 
    * @param inboundBreakdownModel
    * @return InboundBreakdownModel - New location information which has been
    *         captured
    * @throws CustomException
    */
   InboundBreakdownModel breakDown(InboundBreakdownModel inboundBreakdownModel) throws CustomException;
   
   
   
  List<InboundUldFlightModel> getFlightInfoForUld(InboundUldFlightModel inboundBreakdownModel) throws CustomException;

/**
 * @return
 */
  public boolean isDataSyncCREnabled()throws CustomException;

}