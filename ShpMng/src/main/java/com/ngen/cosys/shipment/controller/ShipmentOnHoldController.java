package com.ngen.cosys.shipment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.SearchAWB;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.shipment.service.ShipmetnOnHoldService;

/**
 * This class is controller for Shipment On Hold.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@NgenCosysAppInfraAnnotation
public class ShipmentOnHoldController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(ShipmentOnHoldController.class);

   @Autowired
   private ShipmetnOnHoldService shipmetnOnHoldService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   /**
    * Searching All shipment details
    * 
    * @param shipmentId
    * @throws CustomException
    */
   @RequestMapping(value = "/api/shipment/shipmentonhold/fetch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> fetchShipmentOnHold(@RequestBody @Valid SearchAWB paramAWB) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> shipmentOnHoldListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         List<ShipmentMaster> searchList = shipmetnOnHoldService.getLockDetails(paramAWB);
         shipmentOnHoldListResponse.setData(searchList);
      } catch (CustomException e) {
         shipmentOnHoldListResponse.setData(paramAWB);
         lOgger.error(EXCEPTION, e);
      }
      return shipmentOnHoldListResponse;
   }

   /**
    * Updating hold details for either entire AWB or individual shipment
    * 
    * @param hold
    * @throws CustomException
    */

   @RequestMapping(value = "/api/shipment/shipmentonhold/hold", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ShipmentMaster> updateShipmentOnHold(@RequestBody @Valid ShipmentMaster shipmentMaster)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ShipmentMaster> shipmentOnHoldListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         shipmetnOnHoldService.updateLockDetails(shipmentMaster);
         shipmentOnHoldListResponse.setData(shipmentMaster);

      } catch (CustomException e) {
         shipmentOnHoldListResponse.setData(shipmentMaster);
         lOgger.error(EXCEPTION, e);
      }
      return shipmentOnHoldListResponse;
   }

   /**
    * Updating hold details for either entire AWB or individual shipment
    * 
    * @param hold
    * @throws CustomException
    */

   @RequestMapping(value = "/api/shipment/shipmentonhold/generateCTOcase", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ShipmentMaster> generateCTOcase(@RequestBody @Valid ShipmentMaster shipmentMaster)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<ShipmentMaster> shipmentOnHoldListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         shipmetnOnHoldService.generateCTOcase(shipmentMaster);
         shipmentOnHoldListResponse.setData(shipmentMaster);
      } catch (CustomException e) {
         shipmentOnHoldListResponse.setData(shipmentMaster);
         lOgger.error(EXCEPTION, e);
      }
      return shipmentOnHoldListResponse;
   }

}