package com.ngen.cosys.shipment.information.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.shipment.awb.model.AWBPrintRequest;
import com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO;
import com.ngen.cosys.shipment.information.model.BookingShipmentDetails;
import com.ngen.cosys.shipment.information.model.BookingShipmentDetailsBuilder;
import com.ngen.cosys.shipment.information.model.DimensionInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentFlightModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSendMailModel;
import com.ngen.cosys.shipment.information.service.ICMSBookingApiService;
import com.ngen.cosys.shipment.information.service.ShipmentInformationService;
import com.ngen.cosys.shipment.model.ShipmentMaster;

import io.swagger.annotations.ApiOperation;
import reactor.util.CollectionUtils;

/**
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class ShipmentInformationController {

	@Autowired
	private ShipmentInformationService shipmentInformationService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	private SendEmailEventProducer publisher;
	
	@Autowired
	private BookingShipmentDetailsBuilder bookingShipmentDetailsBuilder; 
	
	@Autowired
	private ICMSBookingApiService icmsBookingApiService;
	
	@Autowired
	private ShipmentInformationDAO shipmentInformationDAO;

	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentInformationController.class);

	private void sendMail(ShipmentInfoSendMailModel request) {
		EMailEvent emailEvent = new EMailEvent();
		String mailSubj = "SHIPMENT INFORMATION FOR AWB NUMBER " + request.getShipmentNumber();
		emailEvent.setMailSubject(mailSubj);
		emailEvent.setMailBody(mailSubj);
		emailEvent.setMailTo(request.getEmailTo());

		HashMap<String, String> templateParams = new HashMap<>();
		templateParams.put("shipmentNumber", (request.getShipmentNumber()));

		// Attachments
		Map<String, AttachmentStream> attachments = new HashMap<>();
		AttachmentStream attachment = null;
		//
		for (FileUpload file : request.getUploadFilesList()) {
			attachment = new AttachmentStream();
			attachment.setFileId(file.getUploadDocId());
			attachments.put(file.getDocumentName(), attachment);
		}
		emailEvent.setMailAttachments(attachments);

		// Template Params
		TemplateBO template = new TemplateBO();
		template.setTemplateName("SHIPMENT INFORMATION DOCUMENTS");
		template.setTemplateParams(templateParams);
		emailEvent.setTemplate(template);
		publisher.publish(emailEvent);
	}

	/**
	 * Searching for Shipment Information.
	 * 
	 * @param search
	 * @return ShipmentInfoModel
	 * @throws CustomException
	 */
	@ApiOperation("Searching for shipment information")
	@RequestMapping(value = "/api/shipmentinfo/getShipmentInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoModel> getShipmentInformation(@Valid @RequestBody ShipmentInfoSearchReq search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentInfoModel> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();
		ShipmentInfoModel fetchLShipmenInfo = shipmentInformationService.getShipmentInfo(search);
		shipmentInfo.setData(fetchLShipmenInfo);
		return shipmentInfo;
	}

	@ApiOperation("Print AWB Barcode")
	@RequestMapping(value = "/api/shipmentinfo/printAWBBarcode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoModel> printAWBTag(@Valid @RequestBody AWBPrintRequest search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentInfoModel> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();
		shipmentInformationService.printAWBBarcode(search);
		return shipmentInfo;
	}

	/**
	 * send uploaded documents for Shipment Information.
	 * 
	 * @param mailReq
	 * @return ShipmentInfoSendMailModel
	 * @throws CustomException
	 */
	@ApiOperation("send uploaded docs for shipment information")
	@RequestMapping(value = "/api/shipmentinfo/senddocs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoSendMailModel> sendUploadedDocsThrMail(
			@Valid @RequestBody ShipmentInfoSendMailModel mailReq) {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentInfoSendMailModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		sendMail(mailReq);
		return response;
	}

	/**
	 * Cancel Shipment
	 * 
	 * @param search
	 * @return ShipmentInfoModel
	 * @throws CustomException
	 */

	@ApiOperation("Cancel shipment")
	@RequestMapping(value = "/api/shipmentinfo/cancelshipment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoModel> cancelShipmentInformation(@Valid @RequestBody ShipmentInfoModel search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentInfoModel> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();

		try {
			// Cancel the shipment
			ShipmentInfoModel fetchLShipmenInfo = shipmentInformationService.cancelShipment(search);

			// Call billing
			if (fetchLShipmenInfo.getCancelCharge() && fetchLShipmenInfo.getStausForCancel().equalsIgnoreCase("Yes")) {
				ChargeableEntity chargeableEntity = new ChargeableEntity();
				chargeableEntity.setReferenceId(search.getShipmentId());
				chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
				chargeableEntity.setHandlingTerminal(search.getTerminal());
				chargeableEntity.setUserCode(search.getLoggedInUser());
				Charge.cancelCharge(chargeableEntity);
			}
			//call ICMS Cancel Booking 
			if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Icms.class)) {
				List<ShipmentFlightModel> bookingFlightList = new ArrayList<>();
				if(!CollectionUtils.isEmpty(search.getIncomingFlightDetails())) {
					bookingFlightList.addAll(search.getIncomingFlightDetails());
				}
				if(!CollectionUtils.isEmpty(search.getOutbooundFlightDetails())) {
					bookingFlightList.addAll(search.getOutbooundFlightDetails());
				}
				Boolean checkOutgoingFlightCarrier = bookingShipmentDetailsBuilder.checkCarrierGroup(bookingFlightList);
				if (checkOutgoingFlightCarrier && search.getShipmentNumber().length()==11 && !"UNK".equals(search.getShipmentNumber().substring(0,3))) {
					BookingShipmentDetails bookingShipmentDetails = bookingShipmentDetailsBuilder.buildCancelBookingModelForICMS(search);
					icmsBookingApiService.callICMSCancelBookingApi(bookingShipmentDetails);
				}
			}
			
			shipmentInfo.setData(fetchLShipmenInfo);
		} catch (CustomException e) {
			shipmentInfo.setData(search);
			shipmentInfo.setSuccess(false);
		}
		return shipmentInfo;
	}

	@ApiOperation("Searching for Message information")
	@RequestMapping(value = "/api/shipmentinfo/fetchMessageInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<List<ShipmentInfoMessageModel>> getMessageInformation(
			@Valid @RequestBody ShipmentInfoModel search) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<ShipmentInfoMessageModel>> messageInfo = utilitiesModelConfiguration
				.getBaseResponseInstance();
		List<ShipmentInfoMessageModel> fetchMessageInfo = shipmentInformationService.fetchMessages(search);
		messageInfo.setData(fetchMessageInfo);
		return messageInfo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Purge shipment")
	@RequestMapping(value = "/api/shipmentinfo/purgeShipment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoModel> purgeShipment(@Valid @RequestBody ShipmentInfoModel search)
			throws CustomException {
		BaseResponse<ShipmentInfoModel> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();

		try {
			// Cancel the shipment
			ShipmentInfoModel fetchLShipmenInfo = shipmentInformationService.purgeShipment(search);
			// No more issue, Can move the shipment to Archival
			if (fetchLShipmenInfo.isReadyForPurge()) {
				//TenantZone mainToArchivalTenant = TenantZone.enumOf(search.getTenantAirport());
				if (Objects.nonNull(TenantContext.getTenantId())) {
				/**	try {
						CosysApplicationContext.switchToArchivalDbAndExecute(() -> {
							// Write a logic to move to Archival process
							shipmentInformationService.executeProcedureToMerge();
							// Clean up the Archival process context thread
							return null;
						});
					} catch (Throwable e) {
						LOGGER.error("Exception " + e.getMessage());
					} **/
					
					shipmentInformationService.executeProcedureToMerge();
					
					String url = shipmentInformationService.getUrlForClearingShipmentDateCache();
					restTemplate = CosysApplicationContext.getRestTemplate();
					// set your headers
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					// set your entity to send
					HttpEntity entity = new HttpEntity(search.getShipmentNumber(), headers);
					restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				} else {
					LOGGER.error(
							"Shipment Information Controller :: Purge Shipment MainToArchival tenant not found {}");
				}
			}
			shipmentInfo.setData(fetchLShipmenInfo);
		} catch (CustomException e) {
			search.getMessageList().addAll(e.getErrorHolder());
			shipmentInfo.setData(search);
			shipmentInfo.setSuccess(false);
		}
		return shipmentInfo;
	}
	

	   /**
	    * Check if the Shipment is handled by House or Masters
	    * 
	    * @param shipmentInfoModel
	    * @throws CustomException
	    */
	   @RequestMapping(value = "/api/shipmentinfo/isShipmentHandledByHouse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public boolean isHandledByHouse(@RequestBody ShipmentMaster shipmentMaster) throws CustomException {
		   return shipmentInformationService.isHandledByHouse(shipmentMaster);
	   }
	   @RequestMapping(value = "/api/shipmentinfo/isShipmentHandledByHouseMobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public BaseResponse<Boolean> isHandledByHouseMobile(@RequestBody ShipmentMaster shipmentMaster) throws CustomException {
		   BaseResponse<Boolean> ishandledByHouse = utilitiesModelConfiguration.getBaseResponseInstance();
		   ishandledByHouse.setData(Boolean.valueOf(shipmentInformationService.isHandledByHouse(shipmentMaster)));
		   return ishandledByHouse;
		}
	   /**
		 * Searching for Shipment Information.
		 * 
		 * @param search
		 * @return ShipmentInfoModel
		 * @throws CustomException
		 */
		@ApiOperation("Searching for shipment dimension information")
		@RequestMapping(value = "/api/shipmentinfo/getDimensionInformation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public BaseResponse<DimensionInfoModel> getShipmentDimensionInfo(@Valid @RequestBody ShipmentInfoSearchReq search)
				throws CustomException {
			LOGGER.info("Method start getShipmentDimensionInfo, ShipmentInfoSearchReq : " + search);
			@SuppressWarnings("unchecked")
			BaseResponse<DimensionInfoModel> dimensionInfo = utilitiesModelConfiguration.getBaseResponseInstance();
			DimensionInfoModel dimensionModel = shipmentInformationService.getDimensionInfo(search);
			dimensionInfo.setData(dimensionModel);
			LOGGER.info("Method End getShipmentDimensionInfo");
			return dimensionInfo;
		}
/**
		 * saving for Shipment Dimension Information.
		 * 
		 * @param search
		 * @return DimensionInfoModel
		 * @throws CustomException
		 */
		@ApiOperation("Saving shipment dimension information")
		@RequestMapping(value = "/api/shipmentinfo/saveDimensionInformation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public BaseResponse<DimensionInfoModel> saveDimensionInformation(@Valid @RequestBody DimensionInfoModel dimensionInfoModel)
				throws CustomException {
			LOGGER.info("Method start saveDimensionInformation, dimensionInfoModel : " + dimensionInfoModel);
			@SuppressWarnings("unchecked")
			BaseResponse<DimensionInfoModel> dimensionInfo = utilitiesModelConfiguration.getBaseResponseInstance();
			DimensionInfoModel dimensionModel=shipmentInformationService.saveShipmentDimensions(dimensionInfoModel);
			dimensionInfo.setData(dimensionModel);
			
			LOGGER.info("Method End saveDimensionInformation, dimensionInfoModel : ");
			return dimensionInfo;
		}		
}