package com.ngen.cosys.shipment.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.autoKCTarget.api.AutoKCTarget;
import com.ngen.cosys.autoKCTarget.models.AutoKCTargetModel;
import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.model.SearchShipmentLocation;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.shipment.service.MaintainShipmentLocationService;
import com.ngen.cosys.shipment.util.ShipmentUtility;

@NgenCosysAppInfraAnnotation
public class MaintainShipmentLocation {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MaintainShipmentLocation.class);

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private MaintainShipmentLocationService maintainShipmentLocationService;

   @Autowired
   private SubmitDataToCustoms submitDataToCustoms;
   
   @Autowired
   private ShipmentUtility shipmentUtility;
   
   @Autowired
   AutoKCTarget autoKCTargetUtility;

   /**
    * Searching Shipment Location
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/maintainShipLoc/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> searchLocation(@RequestBody @Valid SearchShipmentLocation paramAWB)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> searchListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         ShipmentMaster search = maintainShipmentLocationService.getSearchedLoc(paramAWB);
         searchListResponse.setData(search);
      } catch (CustomException e) {
         searchListResponse.setData(paramAWB);
         searchListResponse.setSuccess(false);
         lOgger.error(EXCEPTION, e);
      }
      return searchListResponse;
   }

   /**
    * Spliting Shipment Location
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/maintainShipLoc/split", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> splitLocation(@RequestBody @Valid ShipmentMaster paramAWB) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> splitListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         maintainShipmentLocationService.getSplittedLoc(paramAWB);
         splitListResponse.setData(paramAWB);
         // Billing Call
         shipmentUtility.calculateLocationCharges(paramAWB);

         // Submit to customs
         this.submitDataToCustoms(paramAWB);
      } catch (CustomException e) {
         splitListResponse.setData(paramAWB);
         splitListResponse.setSuccess(false);
         lOgger.error(EXCEPTION, e);
      }

      return splitListResponse;
   }

   /**
    * Merging Shipment Location
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/maintainShipLoc/merge", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> mergeLocation(@RequestBody @Valid ShipmentMaster paramAWB) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> searchListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         maintainShipmentLocationService.getMergedLoc(paramAWB);
         searchListResponse.setData(paramAWB);
         // Billing Call
         shipmentUtility.calculateLocationCharges(paramAWB);

         // Submit to customs
         this.submitDataToCustoms(paramAWB);
      } catch (CustomException e) {
         searchListResponse.setData(paramAWB);
         searchListResponse.setSuccess(false);
         lOgger.error(EXCEPTION, e);
      }

      return searchListResponse;
   }

   /**
    * Updating Shipment Location
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/maintainShipLoc/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ShipmentMaster> updateLocation(@RequestBody @Valid ShipmentMaster paramAWB)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ShipmentMaster> updateListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         maintainShipmentLocationService.getUpdatedLoc(paramAWB);
         updateListResponse.setData(paramAWB);

         // Billing Call
         try {			
        	 shipmentUtility.calculateLocationCharges(paramAWB);
		} catch (Exception e) {
			 lOgger.error(EXCEPTION, e);
		}

         // Submit to customs
         this.submitDataToCustoms(paramAWB);       

         // Raise an event for import shipments to trigger outbound messages
         this.maintainShipmentLocationService.raiseEventForInventoryOnImportShipments(paramAWB);
         
         //AutoKC Targetting
  		 Boolean shipmentTargetted = autoKCTarget(paramAWB);
  		 if (!ObjectUtils.isEmpty(updateListResponse.getData())) {
  			updateListResponse.getData().setIsShipmentTargetted(shipmentTargetted);
  		 }
         
         

      } catch (CustomException e) {
         updateListResponse.setData(paramAWB);
         updateListResponse.setSuccess(false);
         lOgger.error(EXCEPTION, e);
      }
      return updateListResponse;
   }

   /**
    * Deleting Shipment Inventory
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/maintainShipLoc/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ShipmentMaster> deleteInventory(@RequestBody @Valid ShipmentMaster paramAWB)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ShipmentMaster> deleteListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         maintainShipmentLocationService.getDeletedInventory(paramAWB);
         deleteListResponse.setData(paramAWB);

         // Billing Call
         shipmentUtility.calculateLocationCharges(paramAWB);

         // Submit to customs
         this.submitDataToCustomsOnDelete(paramAWB);

      } catch (CustomException e) {
         deleteListResponse.setData(paramAWB);
         deleteListResponse.setSuccess(false);
         lOgger.error(EXCEPTION, e);
      }

      return deleteListResponse;
   }

   /**
    * Submit data to customs
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void submitDataToCustoms(ShipmentMaster shipmentMaster) throws CustomException {
      if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin())) {
         CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
         customsShipmentInfo.setShipmentNumber(shipmentMaster.getShipmentNumber());
         customsShipmentInfo.setShipmentDate(shipmentMaster.getShipmentDate());
         customsShipmentInfo.setTenantId(shipmentMaster.getTenantAirport());
         customsShipmentInfo.setEventType(CustomsEventTypes.Type.INVENTORY_ADJUSTMENTS);
         customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);
         customsShipmentInfo.setCreatedBy(shipmentMaster.getLoggedInUser());
         customsShipmentInfo.setCreatedOn(LocalDateTime.now());
         customsShipmentInfo.setModifiedBy(shipmentMaster.getLoggedInUser());
         customsShipmentInfo.setModifiedOn(LocalDateTime.now());
         // Submit to customs
         this.submitDataToCustoms.submitShipment(customsShipmentInfo);
      }

   }

   /**
    * On delete of the shipment update customs info
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   private void submitDataToCustomsOnDelete(ShipmentMaster shipmentMaster) throws CustomException {
      if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin())) {
         CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
         customsShipmentInfo.setOrigin(shipmentMaster.getOrigin());
         customsShipmentInfo.setDestination(shipmentMaster.getDestination());
         customsShipmentInfo.setShipmentNumber(shipmentMaster.getShipmentNumber());
         customsShipmentInfo.setShipmentDate(shipmentMaster.getShipmentDate());
         customsShipmentInfo.setTenantId(shipmentMaster.getTenantAirport());
         customsShipmentInfo.setEventType(CustomsEventTypes.Type.INBOUND_DELETE_INVENTORY);
         customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);
         customsShipmentInfo.setCreatedBy(shipmentMaster.getLoggedInUser());
         customsShipmentInfo.setCreatedOn(LocalDateTime.now());
         customsShipmentInfo.setModifiedBy(shipmentMaster.getLoggedInUser());
         customsShipmentInfo.setModifiedOn(LocalDateTime.now());

         // If not empty then capture break down information
         if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getOrigin())
               && !CollectionUtils.isEmpty(shipmentMaster.getShipmentInventories())) {

            // Check for records which has been marked for delete
            List<ShipmentInventory> deletedInventory = shipmentMaster.getShipmentInventories().stream()
                  .filter(t -> "Y".equalsIgnoreCase(t.getFlagDelete())).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(deletedInventory)) {
               Map<Integer, List<ShipmentInventory>> groupByFlightInventory = deletedInventory.stream()
                     .collect(Collectors.groupingBy(ShipmentInventory::getFlightId));
               if (!CollectionUtils.isEmpty(groupByFlightInventory)) {
                  for (Map.Entry<Integer, List<ShipmentInventory>> entry : groupByFlightInventory.entrySet()) {
                     customsShipmentInfo.setFlightKey(entry.getValue().get(0).getFlightKey());
                     customsShipmentInfo.setFlightDate(entry.getValue().get(0).getFlightKeyDate());
                     customsShipmentInfo.setFlightId(BigInteger.valueOf(entry.getKey().intValue()));
                     customsShipmentInfo.setPieces(BigInteger.valueOf(entry.getValue().get(0).getPiecesInv()));
                     customsShipmentInfo.setWeight(entry.getValue().get(0).getWeightInv());

                     customsShipmentInfo.setFlightType(CustomsShipmentType.Type.IMPORT);
                     
                     customsShipmentInfo.setDeleteTheInventory(Boolean.TRUE);
                   
                     
                     // Submit to customs
                     this.submitDataToCustoms.submitShipment(customsShipmentInfo);
                  }
               }
            }
         }

      }
   }
   
   private Boolean autoKCTarget(ShipmentMaster shipmentMaster) {
		Boolean targetted=Boolean.FALSE;
		try {
			if (!MultiTenantUtility.isTenantCityOrAirport(shipmentMaster.getDestination())) {
				// Screening validations for transhiments
				AutoKCTargetModel shipmentInfo = new AutoKCTargetModel();
				shipmentInfo.setShipmentNumber(shipmentMaster.getShipmentNumber());
				shipmentInfo.setShipmentDate(shipmentMaster.getShipmentDate());
				// shipmentInfo.setSHCs(inboundBreakdownModel.getShipment().getShcList());
				shipmentInfo.setHandledAs("TRANS");
				shipmentInfo.setTerminal(shipmentMaster.getTerminal());
				shipmentInfo.setCreatedBy(shipmentMaster.getCreatedBy());
				targetted = autoKCTargetUtility.getUrlForAutoKCTarget(shipmentInfo);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return targetted;
	}
   
   @RequestMapping(value = "/api/shipment/maintainShipLoc/getInboundDetailsForPartsuffix", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public  BaseResponse<Object> getInboundDetailsForPartsuffix(@RequestBody ShipmentInventory shipmentInventory) throws CustomException {
	   BaseResponse<Object> responseData = utilitiesModelConfiguration.getBaseResponseInstance();
	   ShipmentInventory inv  =maintainShipmentLocationService.getInboundDetailsForPartsuffix(shipmentInventory);
	   responseData.setData(inv);
	return responseData;
	   
   }
}