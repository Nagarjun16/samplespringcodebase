package com.ngen.cosys.impbd.mail.breakdown.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownShipmentModel;
import com.ngen.cosys.impbd.mail.breakdown.service.InboundMailBreakDownService;
import com.ngen.cosys.impbd.mail.document.service.InboundMailDocumentService;
import com.ngen.cosys.impbd.mail.manifest.controller.InboundMailManifestController;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailBreakDownValidationGroup;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

import io.swagger.annotations.ApiOperation;
import reactor.util.CollectionUtils;

@NgenCosysAppInfraAnnotation
@RequestMapping("/api/mail/breakdown")
public class InboundMailBreakDownController {

   @Autowired
   private BeanFactory beanFactory;

   @Autowired
   private InboundMailBreakDownService service;

   @Autowired
   private InboundMailDocumentService documentSerice;

   @Autowired
   private AirmailStatusEventProducer produce;

   private static final Logger logger = LoggerFactory.getLogger(InboundMailManifestController.class);

   @ApiOperation(value = "Api which allows user to get break down information")
   @RequestMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> search(@Validated @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.search(requestModel);
      response.setData(responseModel);
      return response;
   }

   @ApiOperation(value = "Api which allows user to get mail break down information")
   @RequestMapping(value = "/searchmailbreakdown", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> searchMailBreakDown(
         @Validated @RequestBody InboundMailBreakDownModel requestModel) throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.searchMailBreakDown(requestModel);
      response.setData(responseModel);
      return response;
   }

   // checkContainerDestinationForBreakDown
   @ApiOperation(value = "Api which Checks the destination of the container")
   @RequestMapping(value = "/checkContainerDestinationForBreakDown", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> checkContainerDestinationForBreakDown(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);

      InboundMailBreakDownModel responseModel = service.checkContainerDestinationForBreakDown(requestModel);
      response.setData(responseModel);
      response.setSuccess(true);

      return response;
   }

   @ApiOperation(value = "Api which allows user to save the break down information")
   @RequestMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> breakdown(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);

      /*
       * if(requestModel.isValidateContainerDest()) {
       * 
       * InboundMailBreakDownModel mailBreakDown =
       * service.updateContainerDestination(requestModel);
       * response.setData(mailBreakDown); return response; }else {
       */
      InboundMailBreakDownModel responseModel = service.breakDown(requestModel);
      response.setData(responseModel);
      response.setSuccess(true);
      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(createData)) {
         triggerAirmailStatusArrivalEvent(createData, requestModel);
      }
      List<InboundMailBreakDownShipmentModel> deleteData = requestModel.getShipments().stream()
            .filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(deleteData)) {
         triggerAirmailStatusDeleteEvent(deleteData, requestModel);
      }

      // }

      return response;
   }

   private void triggerAirmailStatusArrivalEvent(List<InboundMailBreakDownShipmentModel> createData,
         InboundMailBreakDownModel requestModel) {
      AirmailStatusEventParentModel eventParentmodel = new AirmailStatusEventParentModel();
      List<AirmailStatusEvent> eventList = new ArrayList<>();
      createData.forEach(req -> {
         if (Objects.nonNull(req.getShipmentId())) {
            AirmailStatusEvent event = new AirmailStatusEvent();
            event.setSourceTriggerType("IMPORTBREAKDOWN");
            if (MultiTenantUtility.isTenantCityOrAirport(req.getDestinationOfficeExchange().substring(2, 5))) {
               event.setImportExportIndicator("I");
            } else {
               event.setImportExportIndicator("T");
            }
            event.setBreakDownLocation(req.getUldNumber());
            event.setStoreLocation(req.getShipmentLocation());
            event.setStoreLocationType(req.getShipmentLocationType());
            event.setDestination(req.getDestinationOfficeExchange().substring(2, 5));
            event.setNextDestination(req.getNextDestination());
            event.setBup(req.isBup());
            event.setManifestedUldKey(req.getShipmentLocation());
            event.setShipmentId(req.getShipmentId());
            event.setFlightId(requestModel.getFlightId());
            event.setPieces(req.getPieces());
            event.setWeight(req.getWeight());
            event.setStatus("CREATED");
            event.setCreatedOn(requestModel.getCreatedOn());
            event.setCreatedBy(requestModel.getCreatedBy());
            event.setTenantId(requestModel.getTenantAirport());
            event.setShipmentNumber(req.getMailBagNumber().substring(0, 20));
            event.setCarrierCode(requestModel.getCarrierCode());
            event.setMailBag(req.getMailBagNumber());
            event.setTransferCarrierTo(req.getOutgoingCarrier());
            event.setEmbargo("Y".equalsIgnoreCase(req.getEmbargoFlag()) ? true : false);
            eventList.add(event);
         }
      });
      eventParentmodel.setAllMessage(eventList);
      logger.warn("data before triggering import breakdown arrival save: " + eventParentmodel);
      produce.publish(eventParentmodel);

   }

   private void triggerAirmailStatusDeleteEvent(List<InboundMailBreakDownShipmentModel> deleteData,
         InboundMailBreakDownModel requestModel) {
      AirmailStatusEventParentModel parentEvent = new AirmailStatusEventParentModel();
      List<AirmailStatusEvent> eventList = new ArrayList<>();
      deleteData.forEach(obj -> {
         AirmailStatusEvent event = new AirmailStatusEvent();
         event.setSourceTriggerType("IMPORTBREAKDOWNDELEVENT");
         event.setEventsType("DEL");
         event.setMailBag(obj.getMailBagNumber());
         event.setFlightId(requestModel.getFlightId());
         event.setCarrierCode(requestModel.getCarrierCode());
         event.setCreatedOn(requestModel.getCreatedOn());
         event.setCreatedBy(requestModel.getCreatedBy());
         event.setTenantId(requestModel.getTenantAirport());
         event.setStatus("CREATED");
         eventList.add(event);
      });
      parentEvent.setAllMessage(eventList);
      produce.publish(parentEvent);

   }

   @ApiOperation(value = "Api which allows to split the mail bag number")
   @RequestMapping(value = "/split", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownShipmentModel> splitMailBagNumber(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownShipmentModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownShipmentModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownShipmentModel responseModel = service.splitMailBagNumber(requestModel);
      response.setData(responseModel);
      return response;
   }

   @ApiOperation(value = "Api which allows to split the mail bag number")
   @RequestMapping(value = "/checkmailbag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> checkMailBag(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.checkMailBag(requestModel);
      response.setData(responseModel);
      return response;
   }

   @PostMapping(value = "/dispatchyear", produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<BigInteger>> getDispatchYears() throws CustomException {
      BaseResponse<List<BigInteger>> response = beanFactory.getBean(BaseResponse.class);
      List<BigInteger> years = documentSerice.getDispatchYears();
      response.setData(years);
      return response;
   }

   @ApiOperation(value = "Api which allows user to get mail break down information")
   @RequestMapping(value = "/searchmailbags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> searchMailBags(
         @Validated @RequestBody InboundMailBreakDownModel requestModel) throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.searchMailBags(requestModel);
      response.setData(responseModel);
      return response;
   }

   @ApiOperation(value = "Api which allows user to save the break down information")
   @RequestMapping(value = "/saveMailBreakDown", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> saveMailBreakDown(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.mailbreakDown(requestModel);
      response.setData(responseModel);
      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(createData)) {
         triggerAirmailStatusArrivalEvent(createData, requestModel);
      }
      List<InboundMailBreakDownShipmentModel> deleteData = requestModel.getShipments().stream()
            .filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(deleteData)) {
         triggerAirmailStatusDeleteEvent(deleteData, requestModel);
      }
      return response;
   }

   @ApiOperation(value = "Api which allows to split the mail bag number")
   @RequestMapping(value = "/splitmailbag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailBreakDownModel> splitMailBag(
         @Validated(value = InboundMailBreakDownValidationGroup.class) @RequestBody InboundMailBreakDownModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailBreakDownModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailBreakDownModel responseModel = service.splitMailBag(requestModel);
      response.setData(responseModel);
      return response;
   }
}