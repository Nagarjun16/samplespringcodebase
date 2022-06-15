/**
 * This is a rest service controller for managing document information for
 * shipments like AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentBreakDownCompleteStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.routing.RoutingResponseModel;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;
import com.ngen.cosys.shipment.awb.service.ShipmentAWBMasterService;
import com.ngen.cosys.shipment.util.ShipmentUtility;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.shipment.validators.SearchAWBDocument;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.validator.dao.UserValidForCarrierDao;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class AWBDocumentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWBDocumentController.class);

	@Autowired
	private ShipmentAWBMasterService docAWBService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	private ShipmentUtility shipmentUtility;
	
	@Autowired
	private InboundShipmentBreakDownCompleteStoreEventProducer producer;
	
	@Autowired
	private UserValidForCarrierDao userValidForCarrierDao;


	@ApiOperation("API operation for retreiving document information on AWB/CBV/UCB")
	@PostRequest(value = "/api/shpmng/awb/get", method = RequestMethod.POST)
	public BaseResponse<AWB> get(@Validated(SearchAWBDocument.class) @RequestBody AWB awbDetails)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<AWB> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();

		String shipmentNumber = awbDetails.getShipmentNumber();
		LocalDate shipmentdate = awbDetails.getShipmentdate();
		//Restricted Airline Validation
		Map<String, Object> parameterMap = new HashMap<>();
	    parameterMap.put("loggedInUser", awbDetails.getLoggedInUser());
	    parameterMap.put("shipmentNumber", awbDetails.getShipmentNumber());
	    parameterMap.put("type", "AWB");
	   
	      if(!userValidForCarrierDao.isUserValidForCarrier(parameterMap)) {	    
	    	  throw new CustomException("user.not.authorized", "Restricted Airline", ErrorType.ERROR);
	      }

		AWB responseModel = docAWBService.get(awbDetails);
		if (responseModel.getShipmentNumber() == null) {
			responseModel.setShipmentNumber(shipmentNumber);
		}

		if (responseModel.getShipmentdate() == null) {
			responseModel.setShipmentdate(shipmentdate);
		}
		if (Objects.nonNull(responseModel)) {
			responseModel.setNonIATA(awbDetails.getNonIATA());
		}
		awbDetailsRS.setData(responseModel);
		return awbDetailsRS;
	}

	@ApiOperation("API operation for save document information on AWB/CBV/UCB")
	@PostRequest(value = "/api/shpmng/awb/save", method = RequestMethod.POST)
	public BaseResponse<AWB> save(@Validated(SaveAWBDocument.class) @Valid @RequestBody AWB awbDetails)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<AWB> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
		String messageType=null;
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			if (MultiTenantUtility.isTenantAirport(awbDetails.getDestination())) {
				messageType = docAWBService.getMessageType(awbDetails);
			}
		}
		LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
		docAWBService.createShipment(awbDetails);
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
		awbDetailsRS.setData(awbDetails);
		LOGGER.warn("AWB Document Controller - SAVE :: Start Time : {}, End Time : {}", startTime, endTime);
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			if (MultiTenantUtility.isTenantAirport(awbDetails.getDestination())) {
				docAWBService.publishShipmentEvent(awbDetails, messageType);
			}
		}
		// calculating bill
		if(!shipmentUtility.isAnIRRCarrier(awbDetails)) {
			if (CollectionUtils.isEmpty(awbDetailsRS.getMessageList())) {
				// Billing Time measure
				startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
				// Call billing if the charge code was modified from CC to PP for deletion
				if (MultiTenantUtility.isTenantCityOrAirport(awbDetails.getDestination())
						&& "PP".equalsIgnoreCase(awbDetails.getChargeCode())) {
					shipmentUtility.cancelShipmentCharge(awbDetails);
				}
				//null Check for BILLING 
				settingDefaultValuesForBillingIfNull(awbDetails);
				shipmentUtility.calculateBilling(awbDetails);
				endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
				LOGGER.warn("AWB Document Controller - BILLING API :: Start Time : {}, End Time : {}", startTime, endTime);
			}
		}
		
		//Calling RCF/NFD messages event  only for import
		if (!MultiTenantUtility.isTenantCityOrAirport(awbDetails.getOrigin()) && awbDetails.getBreakDownPieces() != null
				&& awbDetails.getBreakDownPieces().compareTo(BigInteger.ZERO) == 1
				&& awbDetails.getPieces().compareTo(awbDetails.getOldPieces()) == 1
				&& awbDetails.getBreakDownPieces().compareTo(awbDetails.getPieces()) != -1
				&& awbDetails.getBreakDownPieces().compareTo(awbDetails.getOldPieces().add(awbDetails.getFoundPieces())) == 0
				&& awbDetails.getPieces().compareTo(awbDetails.getRcfStatusUpdateEventPieces()) != 0) {
			InboundShipmentBreakDownCompleteStoreEvent event = new InboundShipmentBreakDownCompleteStoreEvent();
			event.setShipmentId(awbDetails.getShipmentId());
			event.setFlightId(awbDetails.getFlightId());
			event.setPieces(awbDetails.getPieces());
			event.setWeight(awbDetails.getWeight());
			event.setStatus(EventStatus.NEW.getStatus());
			event.setCompletedAt(LocalDateTime.now());
			event.setCompletedBy(awbDetails.getCreatedBy());
			event.setCreatedOn(LocalDateTime.now());
			event.setCreatedBy(awbDetails.getCreatedBy());
			event.setFunction("AWB Document Save");
			event.setEventName(EventTypes.Names.AWB_DOCUMENT_PIECES_UPDATE);
			producer.publish(event);
		}
		
		return awbDetailsRS;
	}

	private void settingDefaultValuesForBillingIfNull(AWB awbDetails) {
		if (awbDetails.getOtherChargeInfo().getFreightCharges() == null) {
			awbDetails.getOtherChargeInfo().setFreightCharges(BigDecimal.ZERO);
		}
		if (awbDetails.getOtherChargeInfo().getValuationCharges() == null) {
			awbDetails.getOtherChargeInfo().setValuationCharges(BigDecimal.ZERO);
		}
		if (awbDetails.getOtherChargeInfo().getTax() == null) {
			awbDetails.getOtherChargeInfo().setTax(BigDecimal.ZERO);
		}
		if (awbDetails.getOtherChargeInfo().getDueFromAgent() == null) {
			awbDetails.getOtherChargeInfo().setDueFromAgent(BigDecimal.ZERO);
		}
		if (awbDetails.getOtherChargeInfo().getDueFromAirline() == null) {
			awbDetails.getOtherChargeInfo().setDueFromAirline(BigDecimal.ZERO);
		}
	}

	@ApiOperation("API operation for Update ShipmentType")
	@PostRequest(value = "/api/shpmng/awb/update/shipmentType", method = RequestMethod.POST)
	public BaseResponse<AWB> updateShipmenType(@Valid @RequestBody AWB awbDetails) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<AWB> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
		LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
		// update ShipmentMaster
		docAWBService.updateShipmentType(awbDetails);
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
		awbDetailsRS.setData(awbDetails);
		LOGGER.warn("AWB Document Controller - SAVE :: Start Time : {}, End Time : {}", startTime, endTime);
		if (!MultiTenantUtility.isTenantCityOrAirport(awbDetails.getOrigin())) {
			// calculating bill
			if (CollectionUtils.isEmpty(awbDetailsRS.getMessageList())) {
				// Billing Time measure
				startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
				// Call billing if the charge code was modified from CC to PP for deletion
				if (MultiTenantUtility.isTenantCityOrAirport(awbDetails.getDestination())
						&& "PP".equalsIgnoreCase(awbDetails.getChargeCode())) {
					shipmentUtility.cancelShipmentCharge(awbDetails);
				}
				settingDefaultValuesForBillingIfNull(awbDetails);
				shipmentUtility.calculateBilling(awbDetails);
				endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), awbDetails.getTenantId());
				LOGGER.warn("AWB Document Controller - BILLING API :: Start Time : {}, End Time : {}", startTime,
						endTime);
			}
		}
		return awbDetailsRS;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for deriving routing information")
	@PostRequest(value = "/api/shpmng/awb/routing", method = RequestMethod.POST)
	public BaseResponse<List<RoutingResponseModel>> getRouting(
			@Validated(SearchAWBDocument.class) @RequestBody RoutingRequestModel requestModel) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<RoutingResponseModel>> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
		// Derive the routing information
		List<RoutingResponseModel> responseModel = docAWBService.routeDetails(requestModel);
		awbDetailsRS.setData(responseModel);
		return awbDetailsRS;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for retreiving Acceptance information of AWB")
	@PostRequest(value = "/api/shpmng/awb/getAcceptanceInfo", method = RequestMethod.POST)
	public BaseResponse<AWB> getAcceptanceInfo(@RequestBody AWB awbDetails) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<AWB> awbDetailsAccpt = utilitiesModelConfiguration.getBaseResponseInstance();

		String shipmentNumber = awbDetails.getShipmentNumber();
		AWB responseModel = docAWBService.getAcceptanceInfo(awbDetails);

		if (!ObjectUtils.isEmpty(responseModel) && StringUtils.isEmpty(responseModel.getShipmentNumber())) {
			responseModel.setShipmentNumber(shipmentNumber);
		}
		awbDetailsAccpt.setData(responseModel);
		return awbDetailsAccpt;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for retreiving email information of AppointedAgent")
	@PostRequest(value = "/api/shpmng/awb/getEmailInfo", method = RequestMethod.POST)
	public BaseResponse<ShipmentMasterCustomerInfo> getEmailInfo(@RequestBody ShipmentMasterCustomerInfo awbDetails)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentMasterCustomerInfo> awbDetailsAccpt = utilitiesModelConfiguration
				.getBaseResponseInstance();

		ShipmentMasterCustomerInfo responseModel = docAWBService.getEmailInfo(awbDetails);

		awbDetailsAccpt.setData(responseModel);
		return awbDetailsAccpt;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for retreiving fWBConsignee information ")
	@PostRequest(value = "/api/shpmng/awb/getFWBConsigneeInfo", method = RequestMethod.POST)
	public BaseResponse<ShipmentMasterCustomerInfo> getFWBConsigneeInfo(@RequestBody AWB awbDetails)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentMasterCustomerInfo> awbDetailsAccpt = utilitiesModelConfiguration
				.getBaseResponseInstance();

		ShipmentMasterCustomerInfo responseModel = docAWBService.getFWBConsigneeInfo(awbDetails);

		awbDetailsAccpt.setData(responseModel);
		return awbDetailsAccpt;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for retreiving fWBConsignee information ")
	@PostRequest(value = "/api/shpmng/awb/getFWBConsigneeAgentInfoOnSelect", method = RequestMethod.POST)
	public BaseResponse<List<ShipmentMasterCustomerInfo>> getFWBConsigneeAgentInfoOnSelect(
			@RequestBody ShipmentMasterCustomerInfo awbDetails) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<ShipmentMasterCustomerInfo>> awbDetailsAccpt = utilitiesModelConfiguration
				.getBaseResponseInstance();

		List<ShipmentMasterCustomerInfo> responseModel = docAWBService.getFWBConsigneeAgentInfoOnSelect(awbDetails);

		awbDetailsAccpt.setData(responseModel);
		return awbDetailsAccpt;
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.AWB_DOCUMENT)
	@ApiOperation("API operation for fetching exchange rate")
	@PostRequest(value = "/api/shpmng/awb/getExchangeRate", method = RequestMethod.POST)
	public BaseResponse<AWB> getExchangeRate(@RequestBody AWB awbDetails) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<AWB> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();

		AWB awbDetail = docAWBService.getExchangeRate(awbDetails);
		awbDetailsRS.setData(awbDetail);
		return awbDetailsRS;
	}

	@ApiOperation("API operation for fetching All Valid Agents")
	@PostRequest(value = "/api/shpmng/awb/getAllAppointedAgents", method = RequestMethod.POST)
	public BaseResponse<ShipmentMasterCustomerAddressInfo> getAllAppointedAgents(
			@RequestBody ShipmentMasterCustomerInfo request) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentMasterCustomerAddressInfo> awbDetailsRS = utilitiesModelConfiguration
				.getBaseResponseInstance();

		ShipmentMasterCustomerAddressInfo awbDetail = docAWBService.getAllAppointedAgents(request);
		awbDetailsRS.setData(awbDetail);
		return awbDetailsRS;
	}
}