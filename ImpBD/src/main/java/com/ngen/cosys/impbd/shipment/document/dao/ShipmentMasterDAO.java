package com.ngen.cosys.impbd.shipment.document.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterShc;

public interface ShipmentMasterDAO {

	/**
	 * Method to get shipment master info
	 * 
	 * @param shipmentMaster
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	public ShipmentMaster getShipment(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to check whether a shipment is an part shipment or not
    * 
    * @param shipmentMaster
    * @return Boolean - If true then this is an part shipment otherwise false
    * @throws CustomException
    */
   public Boolean isPartShipment(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to check whether a shipment is an service shipment or not
    * 
    * @param shipmentMaster
    * @return Boolean - If true then this is an service shipment otherwise false
    * @throws CustomException
    */
   public Boolean isSVCShipment(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to create shipment master
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   public void createShipment(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to create shipment master routing info
    * 
    * @param shipmentMasterRoutingInfo
    * @throws CustomException
    */
   public void createShipmentMasterRoutingInfo(List<ShipmentMasterRoutingInfo> routing) throws CustomException;

   /**
    * Method to create shipment master shc
    * 
    * @param shipmentMasterShc
    * @throws CustomException
    */
   public void createShipmentMasterShc(List<ShipmentMasterShc> shcs) throws CustomException;

   /**
    * Update the document handling in case document received and photo copy
    * 
    * @param shipmentVerModel
    * @throws CustomException
    */
   public void updateCDHShipmetMasterData(CdhDocumentmaster shipmentVerModel) throws CustomException;

   /**
    * Derive document received on based on flight time
    * 
    * @param shipmentMaster
    * @return LocalDateTime - Earliest document received time
    * @throws CustomException
    */
   LocalDateTime deriveDocumentReceivedDateTime(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   public void updateExportBookingPieceWieght(ShipmentMaster shipmentMaster) throws CustomException;
   
   /**
    * Method to get shipment master info
    * 
    * @param shipmentMaster
    * @return ShipmentMaster
    * @throws CustomException
    */
   public ShipmentMaster checkCOUShipment(ArrivalManifestShipmentInfoModel shipmentMaster) throws CustomException;
   
   /**
    * get Shipment Info with Number and Date
    * @param shipmentMaster
    * @return
    * @throws CustomException
    */
   public ShipmentMaster getShipmentInfo(ShipmentMaster shipmentMaster) throws CustomException;
}