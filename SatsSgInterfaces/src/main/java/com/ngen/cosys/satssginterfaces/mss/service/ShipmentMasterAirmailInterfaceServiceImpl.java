/**
 * This is a class which holds entire functionality for managing of shipment
 * information for shipment.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createShipment() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.ShipmentMasterAirmailInterfaceDAO;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerAddressInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceHandlingArea;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentOtherChargeInfoAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentRemarksAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentMasterAirmailInterfaceServiceImpl implements ShipmentMasterAirmailInterfaceService {

   @Autowired
   private ShipmentMasterAirmailInterfaceDAO dao;

   @Autowired
   ShipmentRemarksAirmailInterfaceService shipmentRemarksService;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService#
    * createShipment(com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
    */
   @Override
   public void createShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {

      // Get the shipment information
      boolean overrideShipmentMasterInfo = true;

      // Check for existing data
      ShipmentMasterAirmailInterface existingShipmentInfo = dao.getShipment(shipmentMaster);
      Optional<ShipmentMasterAirmailInterface> oShipmentMaster = Optional.ofNullable(existingShipmentInfo);
      // Check for shipment existence
      if (oShipmentMaster.isPresent() && (oShipmentMaster.get().getShipmentId() != null)) {
         shipmentMaster.setShipmentId(oShipmentMaster.get().getShipmentId());
         if (oShipmentMaster.get().getManuallyCreated()) {
            overrideShipmentMasterInfo = false;
         }
      }

      // If it can override
      if (overrideShipmentMasterInfo) {
         // If the type of shipment is AWB then check for FWB information
         ShipmentMasterAirmailInterface shipmentMasterTemp = null;
         /*if (ShipmentType.Type.AWB.equalsIgnoreCase(shipmentMaster.getShipmentType())) {
            // 1. Pull FWB message data OR Shipment Master data
            shipmentMasterTemp = dao.getFwbInfo(shipmentMaster);
            if (Optional.ofNullable(shipmentMasterTemp).isPresent()) {
               shipmentMaster.setOrigin(shipmentMasterTemp.getOrigin());
               shipmentMaster.setDestination(shipmentMasterTemp.getDestination());
               shipmentMaster.setPiece(shipmentMasterTemp.getPiece());
               shipmentMaster.setWeight(shipmentMasterTemp.getWeight());
               shipmentMaster.setWeightUnitCode(shipmentMasterTemp.getWeightUnitCode());
               shipmentMaster.setCarrierCode(shipmentMasterTemp.getCarrierCode());

               shipmentMaster.setConsignee(shipmentMasterTemp.getConsignee());
               shipmentMaster.setShipper(shipmentMasterTemp.getShipper());
               shipmentMaster.setOtherChargeInfo(shipmentMasterTemp.getOtherChargeInfo());
               shipmentMaster.setHandlingArea(shipmentMasterTemp.getHandlingArea());
               shipmentMaster.setRouting(shipmentMasterTemp.getRouting());
               shipmentMaster.setShcs(shipmentMasterTemp.getShcs());
               shipmentMaster.setShcHandlingGroup(shipmentMasterTemp.getShcHandlingGroup());
               shipmentMaster.setRemarks(shipmentMasterTemp.getRemarks());
               shipmentMaster.setDocumentReceivedOn(LocalDateTime.now());

            }
         }*/

         // Derive part shipment for a shipment
         Boolean partShipment = dao.isPartShipment(shipmentMaster);
         shipmentMaster.setPartShipment(partShipment);

         // Derive Service Shipment
         Boolean svcShipment = dao.isSVCShipment(shipmentMaster);
         shipmentMaster.setSvc(svcShipment);

         // Add shipment master
         dao.createShipment(shipmentMaster);

         // Add routing info
         this.createRoutingInfo(shipmentMaster);

         // Add SHC
         this.createSHC(shipmentMaster);

         // Add SHC Group
         if (!CollectionUtils.isEmpty(shipmentMaster.getShcHandlingGroup())) {
            shipmentMaster.getShcHandlingGroup().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
            dao.createShipmentMasterShcHandlingGroup(shipmentMaster.getShcHandlingGroup());
         }
         // Add Other Charge Info
         // TODO : Temporary fix for populating charge code
         if (shipmentMaster.getOtherChargeInfo() == null) {
            ShipmentOtherChargeInfoAirmailInterface shipmentOtherChargeInfo = new ShipmentOtherChargeInfoAirmailInterface();
            shipmentOtherChargeInfo.setChargeCode("PP");
            shipmentMaster.setOtherChargeInfo(shipmentOtherChargeInfo);
         }
         this.createOtherChargeDelcaration(shipmentMaster);

         // TODO: Change the logic of fetching terminal from Login Context
         ShipmentMasterAirmailInterfaceHandlingArea shipmentMasterHandlingArea = new ShipmentMasterAirmailInterfaceHandlingArea();
         shipmentMasterHandlingArea.setHandledBy("AFT6");
         shipmentMaster.setHandlingArea(shipmentMasterHandlingArea);
         shipmentMasterHandlingArea.setShipmentId(shipmentMaster.getShipmentId());
         // Add handling area
         this.dao.createShipmentMasterHandlingArea(shipmentMasterHandlingArea);

         // Consignee
         this.createConsignee(shipmentMaster);
         // Shipper
         this.createShipper(shipmentMaster);
         // Remarks
         this.createRemarks(shipmentMaster);
      }
   }

   /**
    * Method to create routing info
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createRoutingInfo(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      // Add Routing Info
      if (!CollectionUtils.isEmpty(shipmentMaster.getRouting())) {
         shipmentMaster.getRouting().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
         dao.createShipmentMasterRoutingInfo(shipmentMaster.getRouting());
      }
   }

   /**
    * Methid to create SHC
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createSHC(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      // Add SHC
      if (!CollectionUtils.isEmpty(shipmentMaster.getShcs())) {
         shipmentMaster.getShcs().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
         dao.createShipmentMasterShc(shipmentMaster.getShcs());
      }
   }

   /**
    * Method to create other charge declaration
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createOtherChargeDelcaration(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      Optional<ShipmentOtherChargeInfoAirmailInterface> otherChargeInfo = Optional.ofNullable(shipmentMaster.getOtherChargeInfo());
      if (otherChargeInfo.isPresent()) {
         shipmentMaster.getOtherChargeInfo().setShipmentId(shipmentMaster.getShipmentId());
         dao.createShipmentOtherChargeInfo(shipmentMaster.getOtherChargeInfo());
      }
   }

   /**
    * Method to create remarks
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createRemarks(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      // Remarks
      if (shipmentMaster.getRemarks() != null) {
         for (ShipmentRemarksAirmailInterfaceModel t : shipmentMaster.getRemarks()) {
            t.setShipmentId(shipmentMaster.getShipmentId());
            t.setShipmentType(shipmentMaster.getShipmentType());
            this.shipmentRemarksService.createShipmentRemarks(t);
         }
      }
   }

   /**
    * Method to create Shipper for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createShipper(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      // Add Shipper
      Optional<ShipmentMasterAirmailInterfaceCustomerInfo> shipper = Optional.ofNullable(shipmentMaster.getShipper());
      if (shipper.isPresent()) {
         shipmentMaster.getShipper().setShipmentId(shipmentMaster.getShipmentId());
         dao.createShipmentMasterCustomerInfo(shipmentMaster.getShipper());

         Optional<ShipmentMasterAirmailInterfaceCustomerAddressInfo> shipperAddress = Optional
               .ofNullable(shipmentMaster.getShipper().getAddress());
         if (shipperAddress.isPresent()) {
            shipmentMaster.getShipper().getAddress().setShipmentCustomerInfoId(shipmentMaster.getShipper().getId());
            dao.createShipmentMasterCustomerAddressInfo(shipmentMaster.getShipper().getAddress());
         }

         if (shipperAddress.isPresent()
               && !CollectionUtils.isEmpty(shipmentMaster.getShipper().getAddress().getContacts())) {
            shipmentMaster.getShipper().getAddress().getContacts()
                  .forEach(t -> t.setShipmentAddressInfoId(shipmentMaster.getShipper().getAddress().getId()));
            dao.createShipmentMasterCustomerContactInfo(shipmentMaster.getShipper().getAddress().getContacts());
         }
      }
   }

   /**
    * Method to create consignee for an shipment
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void createConsignee(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException {
      // Add consignee
      Optional<ShipmentMasterAirmailInterfaceCustomerInfo> consignee = Optional.ofNullable(shipmentMaster.getConsignee());
      if (consignee.isPresent()) {
         shipmentMaster.getConsignee().setShipmentId(shipmentMaster.getShipmentId());
         dao.createShipmentMasterCustomerInfo(shipmentMaster.getConsignee());

         // Add address info
         Optional<ShipmentMasterAirmailInterfaceCustomerAddressInfo> consigneeAddress = Optional
               .ofNullable(shipmentMaster.getConsignee().getAddress());
         if (consigneeAddress.isPresent()) {
            shipmentMaster.getConsignee().getAddress().setShipmentCustomerInfoId(shipmentMaster.getConsignee().getId());
            dao.createShipmentMasterCustomerAddressInfo(shipmentMaster.getConsignee().getAddress());
         }

         // Add contact info
         if (consigneeAddress.isPresent()
               && !CollectionUtils.isEmpty(shipmentMaster.getConsignee().getAddress().getContacts())) {
            shipmentMaster.getConsignee().getAddress().getContacts()
                  .forEach(t -> t.setShipmentAddressInfoId(shipmentMaster.getConsignee().getAddress().getId()));
            dao.createShipmentMasterCustomerContactInfo(shipmentMaster.getConsignee().getAddress().getContacts());
         }
      }
   }

	@Override
	public void updateShipmentDocumentReceivedOn(ShipmentVerificationAirmailInterfaceModel shipmentVerModel ) throws CustomException {
		
		dao.updateShipmentDocumentReceivedOn(shipmentVerModel);
		
	}

	

}