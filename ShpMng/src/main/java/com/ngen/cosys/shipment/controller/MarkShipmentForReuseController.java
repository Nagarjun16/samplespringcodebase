/**
 * 
 * MarkShipmentForReuseController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 January, 2018 NIIT -
 */
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
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.model.AddShipmentNumberForReuse;
import com.ngen.cosys.shipment.model.MarkShipmentForReuse;
import com.ngen.cosys.shipment.model.SearchShipmentNumberForReuse;
import com.ngen.cosys.shipment.service.MarkShipmentForReuseService;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for Mark Shipment For Reuse functionalities.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@NgenCosysAppInfraAnnotation
public class MarkShipmentForReuseController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MarkShipmentForReuseController.class);

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private MarkShipmentForReuseService markShipmentForReuseService;

   /**
    * Searching shipment details
    */
   @ApiOperation("Searching for Shipment number available for reuse")
   @RequestMapping(value = "/api/shipment/reuse/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> searchShipment(@Valid @RequestBody MarkShipmentForReuse shipmentNumber)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> reuseResponse = utility.getBaseResponseInstance();
      try {
         List<SearchShipmentNumberForReuse> fetchAwbList = markShipmentForReuseService.search(shipmentNumber);
         reuseResponse.setData(fetchAwbList);
      } catch (CustomException e) {
         reuseResponse.setData(shipmentNumber);
         lOgger.error(EXCEPTION, e);
      }

      return reuseResponse;
   }

   /**
    * Searching All shipment Numbers available for Reuse
    */
   @ApiOperation("Searching for Shipment Numbers available for Reuse")
   @RequestMapping(value = "/api/shipment/reuse/searchAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<SearchShipmentNumberForReuse>> searchAllShipmentsForReuse() throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<SearchShipmentNumberForReuse>> reuseResponse = utility.getBaseResponseInstance();
      List<SearchShipmentNumberForReuse> fetchAwbList = markShipmentForReuseService.searchAll();
      reuseResponse.setData(fetchAwbList);
      return reuseResponse;
   }

   /**
    * Adding shipment Numbers available for Reuse
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_REUSE_AWB_NUMBER)
   @ApiOperation("Adding a Shipment Number available for Reuse")
   @RequestMapping(value = "/api/shipment/reuse/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> addShipmentNumber(@Valid @RequestBody AddShipmentNumberForReuse shipmentNumber)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> reuseResponse = utility.getBaseResponseInstance();
      try {
         List<SearchShipmentNumberForReuse> addAwbList = markShipmentForReuseService.add(shipmentNumber);
         reuseResponse.setData(addAwbList);
      } catch (CustomException e) {
         reuseResponse.setData(shipmentNumber);
         lOgger.error(EXCEPTION, e);
      }

      return reuseResponse;
   }

   /**
    * Deleting Shipment Number available for Reuse
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_REUSE_AWB_NUMBER)
   @ApiOperation("Deleting a Shipment Number available for Reuse")
   @RequestMapping(value = "/api/shipment/reuse/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> deleteShipmentNumber(
         @RequestBody List<SearchShipmentNumberForReuse> markShipmentForReuse) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> reuseResponse = utility.getBaseResponseInstance();
      try {
         markShipmentForReuseService.delete(markShipmentForReuse);
         reuseResponse.setData(markShipmentForReuse);
      } catch (CustomException e) {
         reuseResponse.setData(markShipmentForReuse);
         lOgger.error(EXCEPTION, e);
      }
      return reuseResponse;
   }

}