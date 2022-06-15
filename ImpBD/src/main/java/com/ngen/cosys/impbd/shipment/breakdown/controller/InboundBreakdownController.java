package com.ngen.cosys.impbd.shipment.breakdown.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.autoKCTarget.api.AutoKCTarget;
import com.ngen.cosys.autoKCTarget.models.AutoKCTargetModel;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.DataSyncStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent;
import com.ngen.cosys.events.producer.DataSyncStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer;
import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundUldFlightModel;
import com.ngen.cosys.impbd.shipment.breakdown.service.InboundBreakdownService;
import com.ngen.cosys.impbd.shipment.breakdown.validator.InboundBreakDownValidationGroup;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.util.BillingUtilsExpbd;
import com.ngen.cosys.validators.BreakDownWorkListValidationGroup;
import com.ngen.cosys.validators.InboundBreakDownValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * InboundBreakdownController.java , this controller is responsible for inbounds
 * breakdown of a flight
 *
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(values = { BreakDownWorkListValidationGroup.class })
public class InboundBreakdownController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundBreakdownController.class);

	@Autowired
	private UtilitiesModelConfiguration utility;

	@Autowired
	private InboundBreakdownService inboundBreakdownService;

	@Autowired
	private BillingUtilsExpbd billingUtilsExpbd;
	
	@Autowired
	private ShipmentProcessorService shipmentProcessorService;
	
	@Autowired
	InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer producer;

	@Autowired
	private DataSyncStoreEventProducer dataEventProducer;
	
	@Autowired
	private CommonBookingService commonBookingService;
	
	@Autowired
	AutoKCTarget autoKCTargetUtility;

	/**
	 * this controller method gives the Inbound Breakdown of flight
	 * 
	 * @param inboundBreakdownModel
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation("get inbound breakdown of a flight by AWB number")
	@PostRequest(value = "/api/impbd/inboundbreakdown/getdetails", method = RequestMethod.POST)
	public BaseResponse<InboundBreakdownModel> getShipmentDetails(
			@ApiParam("InboundBreakdownModel") @Validated(value = InboundBreakDownValidation.class) @RequestBody InboundBreakdownModel inboundBreakdownModel)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<InboundBreakdownModel> baseResponse = utility.getBaseResponseInstance();
		InboundBreakdownModel response = inboundBreakdownService.get(inboundBreakdownModel);
		if (StringUtils.isEmpty(response)) {
			throw new CustomException("NORECORDS", "No records found ", "E");
		}
		baseResponse.setData(response);
		return baseResponse;
	}

	/**
	 * this controller method gives the Inbound Breakdown of flight
	 * 
	 * @param inboundBreakdownModel
	 * @throws CustomException
	 */
	@ApiOperation("get inbound breakdown of a flight by AWB number")
	@PostRequest(value = "/api/impbd/inboundbreakdown/createShipmentDetails", method = RequestMethod.POST)
	public BaseResponse<InboundBreakdownModel> createShipmentDetails(
			@ApiParam("InboundBreakdownModel") @Validated(value = InboundBreakDownValidationGroup.class) @RequestBody InboundBreakdownModel inboundBreakdownModel)
			throws CustomException {
		BaseResponse<InboundBreakdownModel> baseResponse = utility.getBaseResponseInstance();
		try {

			LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
					inboundBreakdownModel.getTenantId());
			
			baseResponse.setData(inboundBreakdownService.breakDown(inboundBreakdownModel));
			baseResponse.setSuccess(true);

			LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
					inboundBreakdownModel.getTenantId());
			LOGGER.warn("calling creating inbound breakdown controller:: Start Time : {}, End  Time : {}", startTime,
					endTime, inboundBreakdownModel.getShipment().getShipmentNumber());
		} catch (CustomException e) {
			baseResponse.setData(inboundBreakdownModel);
			baseResponse.setSuccess(false);
		}
		LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
				inboundBreakdownModel.getTenantId());
		//setting flag values at service impl
		if(inboundBreakdownModel.getCheckForRCFNFDtrigger()) {
			InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload = new InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent();
	         payload.setShipmentId(inboundBreakdownModel.getShipmentId());
	         payload.setFlightId(inboundBreakdownModel.getFlightId());
	         payload.setPieces(inboundBreakdownModel.getBreakDownPieces());
	         payload.setWeight(inboundBreakdownModel.getBreakDownWeight());
	         payload.setStatus(EventStatus.NEW.getStatus());
	         payload.setConfirmedAt(LocalDateTime.now());
	         payload.setConfirmedBy(inboundBreakdownModel.getLoggedInUser());
	         payload.setCreatedBy(inboundBreakdownModel.getLoggedInUser());
	         payload.setCreatedOn(LocalDateTime.now());
	         payload.setFunction("Inbound Break Down");
	         payload.setEventName(EventTypes.Names.INBOUND_SHIPMENT_PIECES_EQUALS_TO_BREAK_DOWN_PIECES_EVENT);
	         producer.publish(payload);
	         
	         
	         if( inboundBreakdownService.isDataSyncCREnabled() && inboundBreakdownModel.getFlightHandledInSystem()
		 				&& !CollectionUtils.isEmpty(inboundBreakdownModel.getShipment().getInventory()))
				{
					// no part suffix but multiple inventory line items - noPartSuffixFlag
					// Multiple suffix -- noSuffixFlag
					Set<String> suffixSet = new HashSet<>();
					PartSuffix partSuffix = new PartSuffix();
			     	partSuffix.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
			     	partSuffix.setShipmentDate(inboundBreakdownModel.getShipment().getShipmentdate());
			     	List<String> flightKeys=new ArrayList<String>();
			     	flightKeys.add(inboundBreakdownModel.getFlightNumber());
			     	partSuffix.setFlightKeyList(flightKeys);
					if (inboundBreakdownModel.getShipment() != null && this.commonBookingService.methodToCheckSQOrOalShipment(partSuffix)
							&& !MultiTenantUtility.isTenantCityOrAirport(inboundBreakdownModel.getShipment().getDestination())) {
						for (InboundBreakdownShipmentInventoryModel inv : inboundBreakdownModel.getShipment()
								.getInventory()) {
							suffixSet.add(inv.getPartSuffix());
						}

						DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();
						if(inboundBreakdownModel.getShipmentId() !=null)
						eventPayload.setShipmentId(inboundBreakdownModel.getShipmentId());
						else
							eventPayload.setShipmentId(inboundBreakdownModel.getShipment().getShipmentId());
						eventPayload.setFlightId(inboundBreakdownModel.getFlightId());
						eventPayload.setStatus(EventStatus.NEW.getStatus());
						eventPayload.setConfirmedAt(LocalDateTime.now());
						eventPayload.setConfirmedBy("BATCH");
						eventPayload.setCreatedBy("BATCH");
						eventPayload.setCreatedOn(LocalDateTime.now());
						eventPayload.setFunction("Data Sync Job");
						eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);

						//
						for (String suffix : suffixSet) {
							if(StringUtils.isEmpty(suffix))
								eventPayload.setPartSuffix("");
							eventPayload.setPartSuffix(suffix);
							dataEventProducer.publish(eventPayload);
						}

					}

				}
	         
		}
		
		  
		
		if (!ObjectUtils.isEmpty(baseResponse.getData())) {
			billingUtilsExpbd.calculateBillForBreakOutOnSave(inboundBreakdownModel);
		}
		
		//AutoKC Targetting
		Boolean shipmentTargetted = autoKCTarget(inboundBreakdownModel);
		if (!ObjectUtils.isEmpty(baseResponse.getData())) {
			baseResponse.getData().setIsShipmentTargetted(shipmentTargetted);
		}

		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
				inboundBreakdownModel.getTenantId());
		LOGGER.warn("calling billing in inbound breakdown controller:: Start Time : {}, End Time : {}", startTime,
				endTime, inboundBreakdownModel.getShipment().getShipmentNumber());
		return baseResponse;
	}

	private Boolean autoKCTarget(InboundBreakdownModel inboundBreakdownModel) {
		Boolean targetted=Boolean.FALSE;
		try {
			if (!MultiTenantUtility.isTenantCityOrAirport(inboundBreakdownModel.getShipment().getDestination())) {
				// Screening validations for transhiments
				AutoKCTargetModel shipmentInfo = new AutoKCTargetModel();
				shipmentInfo.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
				shipmentInfo.setShipmentDate(inboundBreakdownModel.getShipment().getShipmentdate());
				// shipmentInfo.setSHCs(inboundBreakdownModel.getShipment().getShcList());
				shipmentInfo.setHandledAs("TRANS");
				shipmentInfo.setTerminal(inboundBreakdownModel.getTerminal());
				shipmentInfo.setCreatedBy(inboundBreakdownModel.getCreatedBy());
				targetted = autoKCTargetUtility.getUrlForAutoKCTarget(shipmentInfo);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return targetted;
	}

	@ApiOperation("get inbound breakdown of a flight by AWB number")
	@PostRequest(value = "/api/impbd/inboundbreakdown/createShipmentDetailsList", method = RequestMethod.POST)
	public BaseResponse<InboundBreakdownModel> createShipmentDetailsList(
			@ApiParam("InboundBreakdownModel") @Validated(value = InboundBreakDownValidationGroup.class) @RequestBody List<InboundBreakdownModel> inboundBreakdownModelList)
			throws CustomException {
		BaseResponse<InboundBreakdownModel> baseResponse = utility.getBaseResponseInstance();
		for (InboundBreakdownModel inboundBreakdownModel : inboundBreakdownModelList) {
			try {

				LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
						inboundBreakdownModel.getTenantId());
				LocalDate shipmentDate = this.shipmentProcessorService.getShipmentDate(inboundBreakdownModel.getShipment().getShipmentNumber());
				inboundBreakdownModel.getShipment().setShipmentdate(shipmentDate);
				inboundBreakdownModel.setGroupCreateLocation(Boolean.TRUE);
				baseResponse.setData(inboundBreakdownService.breakDown(inboundBreakdownModel));
				baseResponse.setSuccess(true);
				LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
						inboundBreakdownModel.getTenantId());
				LOGGER.warn("calling creating inbound breakdown controller:: Start Time : {}, End Time : {}", startTime,
						endTime, inboundBreakdownModel.getShipment().getShipmentNumber());
			} catch (CustomException e) {
				baseResponse.setData(inboundBreakdownModel);
				baseResponse.setSuccess(false);
			}
			
			//setting flag values at service impl
			if(inboundBreakdownModel.getCheckForRCFNFDtrigger()) {
				InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload = new InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent();
		         payload.setShipmentId(inboundBreakdownModel.getShipmentId());
		         payload.setFlightId(inboundBreakdownModel.getFlightId());
		         payload.setPieces(inboundBreakdownModel.getBreakDownPieces());
		         payload.setWeight(inboundBreakdownModel.getBreakDownWeight());
		         payload.setStatus(EventStatus.NEW.getStatus());
		         payload.setConfirmedAt(LocalDateTime.now());
		         payload.setConfirmedBy(inboundBreakdownModel.getLoggedInUser());
		         payload.setCreatedBy(inboundBreakdownModel.getLoggedInUser());
		         payload.setCreatedOn(LocalDateTime.now());
		         payload.setFunction("Inbound Break Down");
		         payload.setEventName(EventTypes.Names.INBOUND_SHIPMENT_PIECES_EQUALS_TO_BREAK_DOWN_PIECES_EVENT);
		         producer.publish(payload);
		         
		         
		         if( inboundBreakdownService.isDataSyncCREnabled() && inboundBreakdownModel.getFlightHandledInSystem()
			 				&& !CollectionUtils.isEmpty(inboundBreakdownModel.getShipment().getInventory()))
					{
						// no part suffix but multiple inventory line items - noPartSuffixFlag
						// Multiple suffix -- noSuffixFlag
						Set<String> suffixSet = new HashSet<>();
						PartSuffix partSuffix = new PartSuffix();
				     	partSuffix.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
				     	partSuffix.setShipmentDate(inboundBreakdownModel.getShipment().getShipmentdate());
				     	List<String> flightKeys=new ArrayList<String>();
				     	flightKeys.add(inboundBreakdownModel.getFlightNumber());
				     	partSuffix.setFlightKeyList(flightKeys);
						if (inboundBreakdownModel.getShipment() != null && this.commonBookingService.methodToCheckSQOrOalShipment(partSuffix)
								&& !MultiTenantUtility.isTenantCityOrAirport(inboundBreakdownModel.getShipment().getDestination())) {
							for (InboundBreakdownShipmentInventoryModel inv : inboundBreakdownModel.getShipment()
									.getInventory()) {
								suffixSet.add(inv.getPartSuffix());
							}

							DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();
							if(inboundBreakdownModel.getShipmentId() !=null)
							eventPayload.setShipmentId(inboundBreakdownModel.getShipmentId());
							else
								eventPayload.setShipmentId(inboundBreakdownModel.getShipment().getShipmentId());
							eventPayload.setFlightId(inboundBreakdownModel.getFlightId());
							eventPayload.setStatus(EventStatus.NEW.getStatus());
							eventPayload.setConfirmedAt(LocalDateTime.now());
							eventPayload.setConfirmedBy("BATCH");
							eventPayload.setCreatedBy("BATCH");
							eventPayload.setCreatedOn(LocalDateTime.now());
							eventPayload.setFunction("Data Sync Job");
							eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);

							//
							for (String suffix : suffixSet) {
								if(StringUtils.isEmpty(suffix))
									eventPayload.setPartSuffix("");
								eventPayload.setPartSuffix(suffix);
								dataEventProducer.publish(eventPayload);
							}

						}

					}
		         
			}
			
			if (!ObjectUtils.isEmpty(baseResponse.getData())) {
				billingUtilsExpbd.calculateBillForBreakOutOnSave(inboundBreakdownModel);
			}
			//AutoKC Targetting
			Boolean shipmentTargetted = autoKCTarget(inboundBreakdownModel);
			if (!ObjectUtils.isEmpty(baseResponse.getData())) {
				baseResponse.getData().setIsShipmentTargetted(shipmentTargetted);
			}
			
		}
		return baseResponse;
	}

	
	
	@ApiOperation("get inbound breakdown")
	@PostRequest(value = "/api/impbd/inboundbreakdown/getFlightInfoForUld", method = RequestMethod.POST)
	public BaseResponse<InboundUldFlightModel> getFlightInfoForUld(
			@ApiParam("InboundBreakdownModel") @RequestBody InboundBreakdownModel inboundBreakdownModel)
			throws CustomException {
		BaseResponse<InboundUldFlightModel> baseResponse = utility.getBaseResponseInstance();
		InboundUldFlightModel flightmodel=new InboundUldFlightModel();
		flightmodel.setUldNumber(inboundBreakdownModel.getShipment().getUldNumber());
		List<InboundUldFlightModel> flightlist = inboundBreakdownService.getFlightInfoForUld(flightmodel);
		flightmodel.setFlightList(flightlist);
		baseResponse.setData(flightmodel);
		return baseResponse; 
	}
  
   
   
	
	
	
	
	
	
	
	
	
	
}