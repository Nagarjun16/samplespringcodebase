/**
 * 
 * MaintainFWBDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 January , 2017 NIIT -
 */

package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FWBDetails;
import com.ngen.cosys.shipment.model.FetchRouting;
import com.ngen.cosys.shipment.model.Routing;

/**
 * @author NIIT
 *
 */
public interface MaintainFWBDAO {

   FWB save(FWB requestModel) throws CustomException;

   FWB delete(FWB requestModel) throws CustomException;

   FWB get(String requestModel) throws CustomException;

   Integer isValidCountry(String code) throws CustomException;

   Routing getRoutingData(FetchRouting requestModel) throws CustomException;

   FWB fwbOnSearch(String awbNumber) throws CustomException;

   FWBDetails fetchFWBDetailsForMobile(FWBDetails requestModel) throws CustomException;

   FWBDetails saveFWBDetailsForMobile(FWBDetails requestModel) throws CustomException;

   /**
    * Method to check whether a shipment is an part shipment or not
    * 
    * @param requestModel
    * @return Boolean - If true then this is an part shipment otherwise false
    * @throws CustomException
    */
   public Boolean isPartShipment(FWB requestModel) throws CustomException;

   /**
    * Method to check whether a shipment is an service shipment or not
    * 
    * @param requestModel
    * @return Boolean - If true then this is an service shipment otherwise false
    * @throws CustomException
    */
   public Boolean isSVCShipment(FWB requestModel) throws CustomException;

   /**
    * Method to check whether an Shipment is an EAWB or not
    * 
    * @param requestModel
    * @return Boolean - true if it is an EAWB otherwise false
    * @throws CustomException
    */
   Boolean isShipmentAnEAWB(FWB requestModel) throws CustomException;

   /**
    * Method to retrieve whether shipment destination/shipment booking offpoint
    * belongs to china
    * 
    * @param requestModel
    * @return Boolean - true if belongs to china OR false
    * @throws CustomException
    */
   Boolean isAirportBelongsToChina(FWB requestModel) throws CustomException;

   /**
    * Method to check whether delivery initiated for an shipment or not
    * 
    * @param requestModel
    * @return Boolean - true if initiated otherwise false
    * @throws MessageProcessingException
    */
   Boolean isShipmentDeliveryInitiated(FWB requestModel) throws CustomException;
   
   /**
    * Method to get customer and shipment info for applying charges on FWB manual creation
    * 
    * @param requestModel
    * @return FWB - It consists of Shipment Id and Customer Id
    * @throws CustomException
    */
   FWB getShipmentInfoForCharges(FWB requestModel) throws CustomException;
   
   /**
    * Method to get eAcceptance Document Information
    * 
    * @param requestModel
    * @return - eAcceptance Origin/Destination/Piece/Weight
    * @throws CustomException
    */
   FWB getAcceptanceInfo(FWB requestModel) throws CustomException;
   
   
   /**
    * 
    * @param requestModel
    * @return count
    * @throws CustomException
    */
   BigInteger getShipmentLodedinfo(AWB requestModel) throws CustomException;

}