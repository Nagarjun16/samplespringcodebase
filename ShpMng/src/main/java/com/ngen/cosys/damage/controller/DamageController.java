package com.ngen.cosys.damage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.damage.model.CaptureDamageDetails;
import com.ngen.cosys.damage.model.CaptureDamageModel;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.damage.model.FileUploadModel;
import com.ngen.cosys.damage.model.MailingDetails;
import com.ngen.cosys.damage.model.SearchDamageDetails;
import com.ngen.cosys.damage.service.DamageService;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DamageController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private DamageService service;

   @Autowired
   private SendEmailEventProducer publisher;

   @Autowired
   AirmailStatusEventProducer producer;

   
   @ApiOperation(value = "Api method to save damage")
   @PostRequest(value = "api/shipment/damage/save", method = RequestMethod.POST)
   public BaseResponse<CaptureDamageModel> damage(@Valid @RequestBody CaptureDamageModel captureDamageModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
    
      BaseResponse<CaptureDamageModel> response = utility.getBaseResponseInstance();
      List<CaptureDamageDetails> damageLineItem = captureDamageModel.getCaptureDetails().stream()
            .filter(obj -> "C".equalsIgnoreCase(obj.getFlagCRUD())).collect(Collectors.toList());
      String damageCode=null;
      if(!CollectionUtils.isEmpty(damageLineItem)){
         CaptureDamageDetails damageLineItemForMail=damageLineItem.get(0);
         damageCode  = service.getDamageCodeForMail(damageLineItemForMail.getListNatureOfDamage().get(0));
      }
      response.setData(service.save(captureDamageModel));
      
      if ("MBN".equalsIgnoreCase(captureDamageModel.getEntityType())) {
    	  if(!CollectionUtils.isEmpty(captureDamageModel.getCaptureDetails())) {
    		  
    	  List<CaptureDamageDetails> damageLineItemForMailList = captureDamageModel.getCaptureDetails().stream()
                  .filter(obj -> "C".equalsIgnoreCase(obj.getFlagCRUD())).collect(Collectors.toList());
    	  if(!CollectionUtils.isEmpty(damageLineItemForMailList)) {
    		  CaptureDamageDetails damageLineItemForMail=damageLineItemForMailList.get(0);
         AirmailStatusEventParentModel eventParentModel = new AirmailStatusEventParentModel();
         List<AirmailStatusEvent> eventList = new ArrayList<>();
         AirmailStatusEvent event = new AirmailStatusEvent();
         event.setSourceTriggerType("CAPTUREDMG");
         event.setMailBag(captureDamageModel.getEntityKey());
         event.setCarrierCode(service.getCarrierCodeForAMailBag(event.getMailBag()));
         event.setRemarks(captureDamageModel.getRemark());
         event.setCreatedBy(captureDamageModel.getCreatedBy());
         event.setCreatedOn(captureDamageModel.getCreatedOn());
         event.setTenantId(captureDamageModel.getTenantAirport());
         
      
         event.setDamageCode(damageCode);
        
        
         event.setStatus("CREATED");
         eventList.add(event);
         eventParentModel.setAllMessage(eventList);
         producer.publish(eventParentModel);
    	  }
      }
      }
      return response;
   }

   @SuppressWarnings("rawtypes")
   @ApiOperation(value = "Api method to save damage")
   @PostRequest(value = "api/shipment/damage/delete", method = RequestMethod.POST)
   public BaseResponse delete(@Valid @RequestBody CaptureDamageDetails captureDamageDetails) throws CustomException {
      BaseResponse response = utility.getBaseResponseInstance();
      service.delete(captureDamageDetails);
      return response;
   }

   @ApiOperation(value = "Api method to fetch damage")
   @PostRequest(value = "api/shipment/damage/fetch", method = RequestMethod.POST)
   public BaseResponse<CaptureDamageModel> fetchdamage(@Valid @RequestBody SearchDamageDetails captureDamageModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<CaptureDamageModel> response = utility.getBaseResponseInstance();
      response.setData(service.fetch(captureDamageModel));
      return response;
   }

   @ApiOperation(value = "Api method to save damage") 
   @PostRequest(value = "api/shipment/damage/fetch/manifestflightdetails", method = RequestMethod.POST)
   public BaseResponse<List<CaptureDamageModel>> fetchManifestFlightDetails(
         @Valid @RequestBody SearchDamageDetails captureDamageModel) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<CaptureDamageModel>> response = utility.getBaseResponseInstance();
      response.setData(service.fetchManifestFlightDetails(captureDamageModel));
      return response;
   }
   
   @ApiOperation(value = "Api method to save damage for mobile") 
   @PostRequest(value = "api/shipment/damage/fetch/manifestflightdetailsmobile", method = RequestMethod.POST)
   public BaseResponse<List<CaptureDamageModel>> fetchManifestFlightDetailsMobile(
         @Valid @RequestBody SearchDamageDetails captureDamageModel) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<CaptureDamageModel>> response = utility.getBaseResponseInstance();
      response.setData(service.fetchManifestFlightDetailsMobile(captureDamageModel));
      return response;
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @ApiOperation(value = "Api method to save damage")
   @PostRequest(value = "api/shipment/damage/upload", method = RequestMethod.POST)
   public BaseResponse<FileUpload> fileUpload(@Valid @RequestBody List<FileUpload> fileData) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<FileUpload> response = utility.getBaseResponseInstance();
      service.storeFiles(fileData);
      return response;

   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @PostRequest(value = "api/shipment/damage/delete-file", method = RequestMethod.POST)
   public BaseResponse<FileUpload> deleteFile(@Valid @RequestBody FileUpload deleteFile) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<FileUpload> response = utility.getBaseResponseInstance();
      response.setData(service.deleteFile(deleteFile));
      return response;
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @PostRequest(value = "api/shipment/damage/send-email", method = RequestMethod.POST)
   public BaseResponse<FileUpload> sendDamagePhotoEmail(@Valid @RequestBody FileUploadModel fileUploadModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<FileUpload> response = utility.getBaseResponseInstance();
      List<FileUpload> filesList = service.loadListOfFiles(fileUploadModel);
      fileUploadModel.setFilesList(filesList);
      sendEmailEvent(fileUploadModel, "Sending Damage Capture Photo ");
      return response;
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @PostRequest(value = "api/shipment/capture-photo/send-email", method = RequestMethod.POST)
   public BaseResponse<FileUpload> sendCapturePhotoEmail(@Valid @RequestBody FileUploadModel fileUploadModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<FileUpload> response = utility.getBaseResponseInstance();
      List<FileUpload> filesList = service.loadListOfFiles(fileUploadModel);
      fileUploadModel.setFilesList(filesList);
      sendEmailEvent(fileUploadModel, "Sending Capture Photo ");
      return response;
   }

   private void sendEmailEvent(FileUploadModel fileUploadModel, String mailSubject) {
      if (!CollectionUtils.isEmpty(fileUploadModel.getFilesList())) {
         EMailEvent emailEvent = new EMailEvent();
         emailEvent.setMailSubject(mailSubject);
         emailEvent.setMailBody(mailSubject);
         emailEvent.setMailTo(fileUploadModel.getEmailTo());
         // Attachments
         Map<String, AttachmentStream> attachments = new HashMap<>();
         AttachmentStream attachment = null;
         //
         for (FileUpload file : fileUploadModel.getFilesList()) {
            attachment = new AttachmentStream();
            attachment.setFileId(file.getUploadDocId());
            attachment.setFileName(file.getDocumentName());
            attachment.setFileType(file.getDocumentFormat());
            attachment.setFileData(file.getDocument());
            //
            attachments.put(file.getDocumentFormat(), attachment);
         }
         emailEvent.setMailAttachments(attachments);
         // Template Params
         TemplateBO template = new TemplateBO();
         template.setTemplateName("FLIGHT COMPLETE");
         emailEvent.setTemplate(template);
         publisher.publish(emailEvent);
      }
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @PostRequest(value = "api/shipment/capturePhoto/sendEmailWithUploadedDoc", method = RequestMethod.POST)
   public BaseResponse<MailingDetails> sendEmailWithUploadedDoc(@Valid @RequestBody MailingDetails uploadedFile)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<MailingDetails> response = utility.getBaseResponseInstance();
      service.sendEmail(uploadedFile);
      response.setData(uploadedFile);
      return response;
   }
   
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE)
   @PostRequest(value = "api/shipment/captureDamage/sendDamageEmailWithUploadedDoc", method = RequestMethod.POST)
   public BaseResponse<MailingDetails> sendDamageEmailWithUploadedDoc(@Valid @RequestBody MailingDetails uploadedFile)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<MailingDetails> response = utility.getBaseResponseInstance();
		service.sendEmailForDamage(uploadedFile);
      response.setData(uploadedFile);
      response.setSuccess(true);
      return response;
   }
   
   @RequestMapping(value = "api/shipment/damage/isShipmentHandledByHouse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Boolean> isHandledByHouse(@RequestBody SearchDamageDetails searchDamageDetails) throws CustomException {
	   BaseResponse<Boolean> ishandledByHouse = utility.getBaseResponseInstance();
	   ishandledByHouse.setData(Boolean.valueOf(service.isHandledByHouse(searchDamageDetails)));
	   return ishandledByHouse;
   }
}