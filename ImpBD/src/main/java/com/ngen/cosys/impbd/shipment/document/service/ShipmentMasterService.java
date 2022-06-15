/**
 * This is a class which holds business methods for creating Shipment Master
 * Entity
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createShipment() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.document.service;

import java.time.LocalDateTime;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;

public interface ShipmentMasterService {

   /**
    * Method to create Shipment Record from either Inbound Break Down/Document
    * Verification/Inbound Print Bar Code
    * 
    * @param shipmentMaster
    *           - Function just passes shipment number/date and if applicable
    *           document receive information
    *
    * 
    * @throws CustomException
    */
   void createShipment(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Update the document handling details
    * 
    * @param shipmentVerModel
    * @throws CustomException
    */
   void updateCDHShipmetMasterData(CdhDocumentmaster shipmentVerModel) throws CustomException;

   /**
    * Derive document received on based on flight time
    * 
    * @param shipmentMaster
    * @return LocalDateTime - Earliest document received time
    * @throws CustomException
    */
   LocalDateTime deriveDocumentReceivedDateTime(ShipmentMaster shipmentMaster) throws CustomException;

}