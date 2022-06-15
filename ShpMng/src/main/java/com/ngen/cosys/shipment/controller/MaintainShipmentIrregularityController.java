/**
 * 
 * ShipmentIrregularityController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 3 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.model.IrregularityDetail;
import com.ngen.cosys.shipment.model.IrregularitySummary;
import com.ngen.cosys.shipment.model.SearchShipmentIrregularity;
import com.ngen.cosys.shipment.service.MaintainShipmentIrregularityService;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for Shipment Irregularity functionalities.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@NgenCosysAppInfraAnnotation
public class MaintainShipmentIrregularityController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MaintainShipmentIrregularityController.class);

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private MaintainShipmentIrregularityService service;

   @Autowired
   private SubmitDataToCustoms submitDataToCustoms;

   /**
    * Searching shipment details
    */
   @ApiOperation("Searching for Shipment Details")
   @PostRequest(value = "/api/shipment/irregularity/search", method = RequestMethod.POST)
   public BaseResponse<Object> searchShipment(@Valid @RequestBody SearchShipmentIrregularity search)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> irregularityResponse = utility.getBaseResponseInstance();
      try {
         IrregularitySummary fetchList = service.search(search);
         irregularityResponse.setData(fetchList);
      } catch (CustomException e) {
         irregularityResponse.setData(search);
         lOgger.error(EXCEPTION, e);
      }
      return irregularityResponse;
   }

   /**
    * Adding/Updating an irregularity
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_SHIPMENT_IRREGULARITY)
   @ApiOperation("Adding a Shipment Irregularity")
   @PostRequest(value = "/api/shipment/irregularity/add", method = RequestMethod.POST)
   public BaseResponse<Object> addIrregularity(@Valid @RequestBody IrregularitySummary irregularity)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Object> irregularityResponse = utility.getBaseResponseInstance();
      try {
         IrregularitySummary summary = service.add(irregularity);
         irregularityResponse.setData(summary);
         // do not trigger if Export shipment
         if (!MultiTenantUtility.isTenantCityOrAirport(irregularity.getOrigin())) {
            // Submit the delivered shipments to Customs
            for (IrregularityDetail irregularityInfo : irregularity.getIrregularityDetails()) {
               if (BigInteger.ZERO.compareTo(irregularityInfo.getPieces()) < 0
                     && (ObjectUtils.isEmpty(irregularityInfo.getWeight())
                           || BigDecimal.ZERO.compareTo(irregularityInfo.getWeight()) < 0)
                     && ("MSCA".equalsIgnoreCase(irregularityInfo.getIrregularityType())
                           || "FDCA".equalsIgnoreCase(irregularityInfo.getIrregularityType()))) {
                  BigInteger flightId = service.getFlightId(irregularityInfo);
                  CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
                  customsShipmentInfo.setShipmentNumber(irregularity.getShipmentNumber());
                  customsShipmentInfo.setShipmentDate(irregularityInfo.getShipmentDate());
                  customsShipmentInfo.setOrigin(irregularity.getOrigin());
                  customsShipmentInfo.setDestination(irregularity.getDestination());
                  customsShipmentInfo.setTenantId(irregularity.getTenantAirport());
                  customsShipmentInfo.setCreatedBy(irregularity.getLoggedInUser());
                  customsShipmentInfo.setModifiedBy(irregularity.getLoggedInUser());
                  customsShipmentInfo.setCreatedOn(LocalDateTime.now());
                  customsShipmentInfo.setModifiedOn(LocalDateTime.now());

                  // Check for empty
                  if (!StringUtils.isEmpty(irregularityInfo.getFlight_No())) {
                     customsShipmentInfo.setFlightKey(irregularityInfo.getFlight_No());
                  } else {
                     customsShipmentInfo.setFlightKey(irregularityInfo.getFlightKey());
                  }

                  customsShipmentInfo.setFlightDate(irregularityInfo.getFlightDate().toLocalDate());
                  customsShipmentInfo.setFlightId(flightId);
                  customsShipmentInfo.setPieces(new BigInteger(irregularityInfo.getPieces() + ""));
                  customsShipmentInfo.setWeight(irregularityInfo.getWeight());
                  customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);
                  customsShipmentInfo.setEventType(CustomsEventTypes.Type.INVENTORY_ADJUSTMENTS);
                  submitDataToCustoms.submitShipment(customsShipmentInfo);
               }

               // Raise the event for break down
               if ("FDCA".equalsIgnoreCase(irregularityInfo.getIrregularityType())) {
                  irregularityInfo.setLoggedInUser(irregularity.getLoggedInUser());
                  irregularityInfo.setTenantId(irregularity.getTenantAirport());
                  irregularityInfo.setShipmentId(summary.getShipmentId());
                  irregularityInfo.setFlightId(this.service.getFlightId(irregularityInfo));
                  this.service.raiseBreakDownEvent(irregularityInfo);
               }
            }

         }

      } catch (Exception e) {
         irregularityResponse.setData(irregularity);
         lOgger.error(EXCEPTION, e);
      }
      return irregularityResponse;
   }
}