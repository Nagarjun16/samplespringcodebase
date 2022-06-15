package com.ngen.cosys.mailbag.overview.information.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.mailbag.overview.information.model.AllStatusOfMailBag;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewDetail;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewSummary;
import com.ngen.cosys.mailbag.overview.information.model.MailbagSearchReq;
import com.ngen.cosys.mailbag.overview.information.service.MailbagOverviewInformationService;
import com.ngen.cosys.mailbag.overview.information.validation.group.MailbagValidationGroup;
import com.ngen.cosys.mailbag.overview.information.validation.group.MailbagValidationMobileGroup;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class MailbagOverviewInformationController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MailbagOverviewInformationController.class);

   @Autowired
   private BeanFactory beanFactory;

   @Autowired
   private MailbagOverviewInformationService mailbagOverviewInformationService;
   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private AirmailStatusEventProducer produce;

   @Autowired
   private MoveableLocationTypesValidator moveableLocationTypesValidator;

   /**
    * Searching for Mailbag Information.
    * 
    * @param search
    * @return MailbagOverviewDetailList
    * @throws CustomException
    */
   @ApiOperation("Searcing for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/getmailbaglist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> getMailbagDetailList(
         @Validated(value = { MailbagValidationGroup.class }) @RequestBody MailbagSearchReq search)
         throws CustomException {
      BaseResponse<Object> mailbagInfoList = utilitiesModelConfiguration.getBaseResponseInstance();
      List<MailbagOverviewSummary> fetchMailbagInfo = new ArrayList<>();
      try {
         fetchMailbagInfo = mailbagOverviewInformationService.getMailBagDetails(search);
         mailbagInfoList.setSuccess(true);
         mailbagInfoList.setData(fetchMailbagInfo);
      } catch (Exception e) {
         mailbagInfoList.setSuccess(false);
         mailbagInfoList.setData(search);
         lOgger.error(EXCEPTION, e.getMessage());
      }
      return mailbagInfoList;
   }

   /**
    * Searching for Mailbag Information.
    * 
    * @param search
    * @return MailbagOverviewDetailList
    * @throws CustomException
    */
   @ApiOperation("Searcing for mailbag information for mobile")
   @RequestMapping(value = "/api/mailbaginfo/getmailbagDetailMobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Object> getMailbagDetailMobile(
         @Validated(value = {
               MailbagValidationMobileGroup.class }) @RequestBody MailbagSearchReq mailbagCorrectionResponse)
         throws CustomException {
      BaseResponse<Object> mailbagInfo = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         mailbagInfo.setData(mailbagOverviewInformationService.getMailBagDetail(mailbagCorrectionResponse));
      } catch (Exception e) {
         mailbagInfo.setSuccess(false);
         mailbagInfo.setData(mailbagCorrectionResponse);
         lOgger.error(EXCEPTION, e);
      }
      return mailbagInfo;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkForContentCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<List<MailbagOverviewDetail>> checkForContentCode(
         @Valid @RequestBody List<MailbagOverviewDetail> requestModel) throws CustomException {
      BaseResponse<List<MailbagOverviewDetail>> response = beanFactory.getBean(BaseResponse.class);
      try {
         response.setData(mailbagOverviewInformationService.checkForContentCode(requestModel));
      } catch (Exception e) {
         response.setData(requestModel);

      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkForMobileContentCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<MailbagOverviewSummary> checkForMobileContentCode(
         @Valid @RequestBody MailbagOverviewSummary requestModel) throws CustomException {
      BaseResponse<MailbagOverviewSummary> response = beanFactory.getBean(BaseResponse.class);
      try {
         response.setData(mailbagOverviewInformationService.checkForMobileContentCode(requestModel));
         response.setSuccess(true);
      } catch (Exception e) {
         response.setData(requestModel);
         response.setSuccess(false);
      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkForShcsOtherThanMail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<List<MailbagOverviewDetail>> checkForShcsOtherThanMail(
         @Valid @RequestBody List<MailbagOverviewDetail> requestModel) throws CustomException {
      BaseResponse<List<MailbagOverviewDetail>> response = beanFactory.getBean(BaseResponse.class);
      try {
         response.setData(mailbagOverviewInformationService.checkForShcsOtherThanMail(requestModel));
      } catch (Exception e) {
         response.setData(requestModel);

      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkforshcsotherthanmailformobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<MailbagOverviewSummary> checkForShcsOtherThanMailMobile(
         @Valid @RequestBody MailbagOverviewSummary requestModel) throws CustomException {
      BaseResponse<MailbagOverviewSummary> response = beanFactory.getBean(BaseResponse.class);
      try {
         List<MailbagOverviewDetail> shcCheckDetails = mailbagOverviewInformationService
               .checkForShcsOtherThanMail(requestModel.getMailbagDetails());
         requestModel.setMailbagDetails(shcCheckDetails);
         response.setData(requestModel);
         response.setSuccess(true);
      } catch (Exception e) {
         response.setData(requestModel);
         response.setSuccess(false);
      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkForContainerDestination", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<List<MailbagOverviewDetail>> checkForContainerDestination(
         @Valid @RequestBody List<MailbagOverviewDetail> requestModel) throws CustomException {
      BaseResponse<List<MailbagOverviewDetail>> response = beanFactory.getBean(BaseResponse.class);
      try {
         response.setData(mailbagOverviewInformationService.checkForContainerDestination(requestModel));
      } catch (Exception e) {
         response.setData(requestModel);

      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/checkforcontainerdestinationformobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<MailbagOverviewSummary> checkForContainerDestinationForMobile(
         @Valid @RequestBody MailbagOverviewSummary requestModel) throws CustomException {
      BaseResponse<MailbagOverviewSummary> response = beanFactory.getBean(BaseResponse.class);
      try {
         List<MailbagOverviewDetail> containerDestinationData = mailbagOverviewInformationService
               .checkForContainerDestination(requestModel.getMailbagDetails());
         requestModel.setMailbagDetails(containerDestinationData);
         response.setData(requestModel);
         response.setSuccess(true);
      } catch (Exception e) {
         response.setData(requestModel);
         response.setSuccess(false);
      }
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/updateLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<List<MailbagOverviewDetail>> updateLocation(
         @Valid @RequestBody List<MailbagOverviewDetail> requestModel) throws CustomException {
      BaseResponse<List<MailbagOverviewDetail>> response = beanFactory.getBean(BaseResponse.class);
      List<MailbagOverviewDetail> responseModel = mailbagOverviewInformationService.updateLocation(requestModel);
      response.setData(responseModel);
      List<MailbagOverviewDetail> updateLocationModel = requestModel.stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      AirmailStatusEventParentModel eventParentModel = new AirmailStatusEventParentModel();
      List<AirmailStatusEvent> eventList = new ArrayList<>();
      updateLocationModel.forEach(obj -> {
         AirmailStatusEvent event = new AirmailStatusEvent();
         event.setSourceTriggerType("ContainerToConatinerMovement");
         try {
            event.setStoreLocation(obj.getStoreLocation());
            MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
            if (!StringUtils.isEmpty(obj.getStoreLocation())) {
               movableLocationModel.setKey(obj.getStoreLocation());
               movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
            }
            event.setStoreLocationType(movableLocationModel.getLocationType());
            event.setPreviousStoreLocation(obj.getExistingShipmentLocation());
            if (!StringUtils.isEmpty(obj.getStoreLocation())) {
               movableLocationModel.setKey(obj.getExistingShipmentLocation());
               movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
            }
            event.setNextDestination(obj.getNextDestination());
            event.setPreviousStoreLocationType(movableLocationModel.getLocationType());
            event.setMailBag(obj.getMailBagNumber());
            event.setCarrierCode(mailbagOverviewInformationService.getCarrierCodeForAMailBag(obj.getShipmentId()));
            event.setStatus("CREATED");
            event.setTenantId(obj.getTenantAirport());
            event.setCreatedBy(obj.getCreatedBy());
            event.setCreatedOn(obj.getCreatedOn());
            eventList.add(event);
         } catch (CustomException e) {
            e.printStackTrace();
         }
      });
      eventParentModel.setAllMessage(eventList);
      produce.publish(eventParentModel);
      return response;
   }

   @ApiOperation(value = "Update location for mailbag information")
   @RequestMapping(value = "/api/mailbaginfo/updateMailbagLocationForMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<MailbagOverviewSummary> updateMultipleMailbagLocationMobile(
         @Valid @RequestBody MailbagOverviewSummary requestModel) throws CustomException {
      BaseResponse<MailbagOverviewSummary> response = beanFactory.getBean(BaseResponse.class);
      MailbagOverviewSummary responseModel = mailbagOverviewInformationService
            .updateMultipleMailbagLocationMobile(requestModel);
      response.setData(responseModel);
      return response;
   }

   @ApiOperation(value = "save or update mailbag information mobile")
   @RequestMapping(value = "/api/mailbaginfo/updateLocationMobile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<MailbagOverviewDetail> saveUpdateMailbag(
         @Valid @RequestBody MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      BaseResponse<MailbagOverviewDetail> response = utilitiesModelConfiguration.getBaseResponseInstance();
      response.setData(mailbagOverviewInformationService.saveUpdateMailBagMobile(mailbagOverviewDetail));
      return response;
   }
   
   @ApiOperation(value = "save or update mailbag information mobile")
   @RequestMapping(value = "/api/mailbaginfo/getAllStatusOfTheMailBag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<AllStatusOfMailBag> getAllStatusOfTheMailBag(
         @Valid @RequestBody AllStatusOfMailBag status) throws CustomException {
      BaseResponse<AllStatusOfMailBag> response = utilitiesModelConfiguration.getBaseResponseInstance();
      response.setData(mailbagOverviewInformationService.getAllStatusOfTheMailBag(status));
      return response;
   }
}