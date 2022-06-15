package com.ngen.cosys.shipment.inactive.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.customs.api.SubmitDataToCustoms;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.customs.api.enums.CustomsShipmentType;
import com.ngen.cosys.customs.api.model.CustomsShipmentInfo;
import com.ngen.cosys.customs.api.model.CustomsShipmentLocalAuthorityDetails;
import com.ngen.cosys.customs.api.model.CustomsShipmentLocalAuthorityInfo;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentFlightCompletedStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentDeliveredStoreEventProducer;
import com.ngen.cosys.events.producer.OutboundShipmentFlightCompletedStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.helper.BOEOCValidation;
import com.ngen.cosys.helper.model.BOEOCValidationRequest;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.inactive.model.InactiveSearch;
import com.ngen.cosys.shipment.inactive.model.InactiveSearchList;
import com.ngen.cosys.shipment.inactive.model.LocalAuthorityDetailsModel;
import com.ngen.cosys.shipment.inactive.model.ShipmentData;
import com.ngen.cosys.shipment.inactive.service.InactiveOldCargoService;
import com.ngen.cosys.timezone.util.TenantZoneTime;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class InacativeOldCargo {

   private static final Logger LOGGER = LoggerFactory.getLogger(InacativeOldCargo.class);

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private InactiveOldCargoService searchInactiveService;

   @Autowired
   private InboundShipmentDeliveredStoreEventProducer producer;

   @Autowired
   private SubmitDataToCustoms submitDataToCustoms;
   
	@Autowired
	private BOEOCValidation boeocValidation;

   @Autowired
   private OutboundShipmentFlightCompletedStoreEventProducer outboundShipmentFlightCompletedStoreEventProducer;

   @ApiOperation("Get inactive cargo List")
   @RequestMapping(value = "/api/shipment/inactive/getlist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<BaseResponse<List<InactiveSearchList>>> getGroupList(@RequestBody InactiveSearch searchGp)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<InactiveSearchList>> searchGroupList = this.utilitiesModelConfiguration
            .getBaseResponseInstance();

      List<InactiveSearchList> gp;
      if (searchGp != null) {
         gp = searchInactiveService.serchInactiveList(searchGp);
         if (gp != null) {
            searchGroupList.setData(gp);
            searchGroupList.setSuccess(true);
         }
      }
      return new ResponseEntity<>(searchGroupList, HttpStatus.OK);
   }

   @ApiOperation("move abandoncargo List")
  
   @RequestMapping(value = "/api/shipment/freightout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<BaseResponse<String>> moveAbandonCargoToImport(@Valid @RequestBody InactiveSearch requestModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<String> searchGroupList = this.utilitiesModelConfiguration.getBaseResponseInstance();

      LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());

      String status ="";
      if(requestModel.getIsHandledByHouse()) {
    	BOEOCValidationRequest request=new BOEOCValidationRequest();
      	request.setInventoryId(requestModel.getInventoryId());
      	request.setHawbNumber(requestModel.getHawbNumber());
      	request.setShipmentId(requestModel.getShipmentData().get(0).getShipmentId());
      	request.setOrigin(requestModel.getShipmentData().get(0).getOrigin());
      	request.setDestination(requestModel.getShipmentData().get(0).getDestination());
      	request=boeocValidation.checkBOEOCValidation(request);
      	 if (!CollectionUtils.isEmpty(request.getMessageList())) {
      		searchGroupList.setMessageList(request.getMessageList());
      	}
      	 else { 
         status = searchInactiveService.moveToFreightOut(requestModel);
     	 }
      }
      else {
    	  status = searchInactiveService.moveToFreightOut(requestModel);
      }
      
      LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());

      LOGGER.warn("call of moveToFreightOut() method in ManualFreightout Screen:: Start Time : {}, End Time : {}",
            startTime, endTime, requestModel.getShipmentNumber());

      if (!StringUtils.isEmpty(status)) {
         searchGroupList.setData(status);
         searchGroupList.setSuccess(true);

         // If the freight out is based on delivery order trigger Shipment Delivery
         // Message
         if (!CollectionUtils.isEmpty(requestModel.getShipmentData())) {
            for (ShipmentData shipment : requestModel.getShipmentData()) {

               // Calculate the charges for Shipment
               if (MultiTenantUtility.isTenantCityOrAirport(shipment.getDestination())) {
                  BillingShipment billingShipment = new BillingShipment();
                  billingShipment.setShipmentNumber(requestModel.getShipmentData().get(0).getAwbNumber());
                  billingShipment.setShipmentDate(requestModel.getShipmentData().get(0).getShipmentDate());
                  billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
                  billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
                  billingShipment.setUserCode(requestModel.getShipmentData().get(0).getLoggedInUser());
                  billingShipment.setHandlingTerminal(requestModel.getShipmentData().get(0).getTerminal());
                  Charge.calculateCharge(billingShipment);
               }

               if (!StringUtils.isEmpty(shipment.getDeliveryOrderNo())) {

                  startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());
                  // Raise the delivery request event
                  InboundShipmentDeliveredStoreEvent event = new InboundShipmentDeliveredStoreEvent();
                  event.setCreatedBy(requestModel.getCreatedBy());
                  event.setCreatedOn(LocalDateTime.now());
                  event.setDeliveredAt(LocalDateTime.now());
                  event.setDeliveredBy(requestModel.getCreatedBy());
                  event.setDoNumber(shipment.getDeliveryOrderNo());
                  event.setStatus(EventStatus.NEW.getStatus());
                  event.setShipmentDate(shipment.getShipmentDate());
                  event.setShipmentId(shipment.getShipmentId());
                  event.setShipmentType(shipment.getShipmentType());
                  event.setShipmentNumber(shipment.getAwbNumber());
                  event.setCarrier(shipment.getCarrier());
                  event.setFunction("Manual Freight Out");
                  event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_DELIVERED_EVENT);

                  this.producer.publish(event);

                  endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());

                  LOGGER.warn(
                        "call of producer.publish() method to Raise the Event for Cargo Messaging in ManualFreightout:: Start Time : {}, End Time : {}",
                        startTime, endTime, shipment.getAwbNumber());
               }

               if ((!StringUtils.isEmpty(shipment.getDeliveryOrderNo())
                     && !ObjectUtils.isEmpty(shipment.getDeliveryId()))
                     || (!StringUtils.isEmpty(shipment.getFlightkey())
                           && !ObjectUtils.isEmpty(shipment.getFlightDate()))) {
                  // Submit the delivered shipments to Customs
                  CustomsShipmentInfo customsShipmentInfo = new CustomsShipmentInfo();
                  customsShipmentInfo.setShipmentNumber(shipment.getAwbNumber());
                  customsShipmentInfo.setShipmentDate(shipment.getShipmentDate());
                  customsShipmentInfo.setOrigin(shipment.getOrigin());
                  customsShipmentInfo.setDestination(shipment.getDestination());
                  customsShipmentInfo.setTenantId(shipment.getTenantAirport());
                  customsShipmentInfo.setCreatedBy(requestModel.getLoggedInUser());
                  customsShipmentInfo.setModifiedBy(requestModel.getLoggedInUser());

                  customsShipmentInfo.setCreatedOn(LocalDateTime.now());
                  customsShipmentInfo.setModifiedOn(LocalDateTime.now());
                  customsShipmentInfo.setPieces(shipment.getPieces());
                  customsShipmentInfo.setWeight(shipment.getWeight());

                  // Details for import
                  if ((!requestModel.getContinueCustomsSubmission())
                        && !StringUtils.isEmpty(shipment.getDeliveryOrderNo())
                        && !ObjectUtils.isEmpty(shipment.getDeliveryId())) {
                     customsShipmentInfo.setDeliveryId(shipment.getDeliveryId());
                     customsShipmentInfo.setDeliveryOrderNo(shipment.getDeliveryOrderNo());
                     customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.IMPORT);
                     customsShipmentInfo.setEventType(CustomsEventTypes.Type.INBOUND_CREATE_DELIVERY);
                     CustomsShipmentLocalAuthorityInfo larInfo = new CustomsShipmentLocalAuthorityInfo();
                     larInfo.setType(requestModel.getType());
                     List<CustomsShipmentLocalAuthorityDetails> listdetails = new ArrayList<>();
                     for (LocalAuthorityDetailsModel element : requestModel.getLocalAuthorityDetail()) {
                        CustomsShipmentLocalAuthorityDetails details = new CustomsShipmentLocalAuthorityDetails();
                        details.setReferenceNumber(element.getReferenceNumber());
                        details.setLicense(element.getLicense());
                        details.setCustomerAppAgentId(element.getCustomerAppAgentId());
                        details.setRemarks(element.getRemarks());
                        details.setDeliveryOrderNo(shipment.getDeliveryOrderNo());
                        listdetails.add(details);
                     }
                     larInfo.setDetails(listdetails);
                     customsShipmentInfo.setLocalAuthorityInfo(larInfo);
                  } else {
                     customsShipmentInfo.setNatureOfGoods(shipment.getNatureOfGoodsDescription());

                     // If the total pieces/weight is empty then set inventory pieces
                     if (ObjectUtils.isEmpty(shipment.getTotalPieces())) {
                        customsShipmentInfo.setTotalPieces(shipment.getPieces());
                        customsShipmentInfo.setTotalWeight(shipment.getWeight());
                     } else {
                        customsShipmentInfo.setTotalPieces(shipment.getTotalPieces());
                        customsShipmentInfo.setTotalWeight(shipment.getTotalWeight());
                     }
                     customsShipmentInfo.setEventType(CustomsEventTypes.EXP_FLIGHT_COMPLETE.getType());
                     customsShipmentInfo.setShipmentType(CustomsShipmentType.Type.EXPORT);
                     customsShipmentInfo.setFlightKey(shipment.getFlightkey());
                     customsShipmentInfo.setFlightDate(shipment.getFlightDate().toLocalDate());
                     customsShipmentInfo.setFlightType(CustomsShipmentType.Type.EXPORT);
                  }
                  startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());
                  // Submit
                  submitDataToCustoms.submitShipment(customsShipmentInfo);

                  endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), requestModel.getTenantId());

                  LOGGER.warn(
                        "call of submitDataToCustoms.submitShipment() method in ManualFreightout:: Start Time : {}, End Time : {}",
                        startTime, endTime, requestModel.getShipmentNumber());

               }

               if ("DEP".equalsIgnoreCase(requestModel.getRemarkType())) {
                  OutboundShipmentFlightCompletedStoreEvent event = new OutboundShipmentFlightCompletedStoreEvent();
                  event.setFlightId(shipment.getFlightId());
                  event.setFlightKey(shipment.getFlightkey());
                  event.setShipmentId(shipment.getShipmentId());
                  event.setCarrierCode(shipment.getCarrier());
                  event.setShipmentNumber(shipment.getAwbNumber());
                  event.setShipmentDate(shipment.getShipmentDate());
                  event.setShipmentType(shipment.getShipmentType());
                  event.setPieces(shipment.getPieces());
                  event.setWeight(shipment.getWeight());
                  event.setStatus(EventStatus.NEW.getStatus());
                  event.setCompletedBy(requestModel.getLoggedInUser());
                  event.setCompletedAt(LocalDateTime.now());
                  event.setCreatedBy(requestModel.getLoggedInUser());
                  event.setCreatedOn(LocalDateTime.now());
                  event.setSector(shipment.getFlightOffPoint());
                  event.setFunction("Manual Freight Out" + " - " + requestModel.getLoggedInUser());
                  event.setEventName(EventTypes.Names.OUTBOUND_SHIPMENT_FLIGHT_COMPLETED_EVENT);
                  outboundShipmentFlightCompletedStoreEventProducer.publish(event);
               }
            }
         }
      }
      return new ResponseEntity<>(searchGroupList, HttpStatus.OK);
   }

   @ApiOperation("move abandoncargo List")
   @RequestMapping(value = "/api/shipment/defaultCreationDays", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<BaseResponse<InactiveSearch>> defaultingDays(@Valid @RequestBody InactiveSearch searchGp)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<InactiveSearch> searchGroupList = this.utilitiesModelConfiguration.getBaseResponseInstance();
      InactiveSearch inactiveSearch = searchInactiveService.defalutingDays(searchGp);
      if (!ObjectUtils.isEmpty(inactiveSearch)) {
         searchGroupList.setData(inactiveSearch);
         searchGroupList.setSuccess(true);
      }
      return new ResponseEntity<>(searchGroupList, HttpStatus.OK);
   }

}