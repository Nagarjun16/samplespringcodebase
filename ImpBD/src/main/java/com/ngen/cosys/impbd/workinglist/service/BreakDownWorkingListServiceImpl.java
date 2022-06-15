/**
 * Service implementation component for processing shipments on break down
 * complete for a flight
 */
package com.ngen.cosys.impbd.workinglist.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.DataSyncStoreEvent;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.MessageReferenceDetail;
import com.ngen.cosys.events.payload.InboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.payload.SMSEvent;
import com.ngen.cosys.events.payload.SegregationReportEvent;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.events.producer.DataSyncStoreEventProducer;
import com.ngen.cosys.events.producer.InboundFlightCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentBreakDownCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.SegregationReportEventProducer;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.producer.SendSMSEventProducer;
import com.ngen.cosys.events.producer.ShipmentNotificationEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.awb.notification.model.AwbNotificationShipment;
import com.ngen.cosys.impbd.constants.HardcodedParam;
import com.ngen.cosys.impbd.events.dao.ImpBdEventDao;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.producer.EAPShipmentsEventProducer;
import com.ngen.cosys.impbd.events.producer.EAWShipmentsEventProducer;
import com.ngen.cosys.impbd.events.producer.GeneralShipmentsEventProducer;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.shipment.irregularity.service.ShipmentIrregularityService;
import com.ngen.cosys.impbd.shipment.remarks.constant.ShipmentRemarksType;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;
import com.ngen.cosys.impbd.shipment.remarks.service.ShipmentRemarksService;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.service.BreakDownSummaryService;
import com.ngen.cosys.impbd.workinglist.dao.BreakDownWorkingListDAO;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListSegmentModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;
import com.ngen.cosys.impbd.workinglist.model.SendTSMMessage;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.inward.service.InwardServiceReportService;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.FlightInfo;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.enums.ShipmentType;
import com.ngen.cosys.validator.model.FlightValidateModel;
import com.ngen.cosys.validator.utils.FlightHandlingSystemValidator;

/**
 * This class is responsible for the Break Down Working List Service operation
 * that comes from the Controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional
public class BreakDownWorkingListServiceImpl implements BreakDownWorkingListService {

	@Autowired
	private BreakDownWorkingListDAO breakDownWorkingListDAO;

	@Autowired
	private ShipmentRemarksService shipRemarksService;

	@Autowired
	private ShipmentIrregularityService shipmentIrregularityService;

	@Autowired
	private InwardServiceReportService inwardReportService;

	@Autowired
	private InboundShipmentBreakDownCompleteStoreEventProducer producer;

	@Autowired
	private InboundFlightCompleteStoreEventProducer flightCompleteProducer;

	@Autowired
	private EAPShipmentsEventProducer eapShipmentsEventProducer;

	@Autowired
	private EAWShipmentsEventProducer eawShipmentsEventProducer;

	@Autowired
	private GeneralShipmentsEventProducer generalShipmentsEventProducer;

	@Autowired
	private BreakDownSummaryService breakDownSummaryService;

	@Autowired
	private ReportMailService reportMailService;

	@Autowired
	ShipmentNotificationEventProducer eventNoftificationProducer;

	@Autowired
	SendSMSEventProducer smsProducer;

	@Autowired
	SendEmailEventProducer emailPublisher;

	@Autowired
	private ImpBdEventDao dao;

	@Autowired
	private DataSyncStoreEventProducer dataEventProducer;

	@Autowired
	private FlightValidationDao flightValidationDao;

	@Autowired
	private FlightHandlingSystemValidator handlingSystemValidator;

	private static final String LH_RCF_NFD = "LH_RCF_NFD";

	@Autowired
	SegregationReportEventProducer segregationReportEventProducer;

	/*
	 * (non-Javadoc) R
	 * 
	 * @see com.ngen.cosys.impbd.service.BreakDownWorkingListService#
	 * getBreakDownWorkingList(com.ngen.cosys.impbd.model.ArrivalManifestFlight)
	 */
	@Override
	public BreakDownWorkingListModel getBreakDownWorkingList(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException {

		BreakDownWorkingListModel data = breakDownWorkingListDAO.getBreakDownWorkingList(breakDownWorkingListModel);
		if (!ObjectUtils.isEmpty(data) && !StringUtils.isEmpty(breakDownWorkingListModel.getSegment())) {
			data.setSegment(breakDownWorkingListModel.getSegment());
		}

		if (!ObjectUtils.isEmpty(data) && !CollectionUtils.isEmpty(data.getBreakDownWorkingListSegment())) {
			for (BreakDownWorkingListSegmentModel t : data.getBreakDownWorkingListSegment()) {
				if (CollectionUtils.isEmpty(t.getBreakDownWorkingListShipmentInfoModel())) {
					t.setNilCargo(true);
				}
			}
		}

		
		// Added data Sync validation
		FlightInfo flightInfo = new FlightInfo();
		flightInfo.setFlightKey(breakDownWorkingListModel.getFlightNumber());
		flightInfo.setFlightDate(breakDownWorkingListModel.getFlightDate());
		flightInfo.setType("I");
		Boolean handlingSystem = handlingSystemValidator.isFlightHandlinginSystem(flightInfo);
		data.setHandlinginSystem(handlingSystem);
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.BreakDownWorkingListService#
	 * updateFlightDelayForShipments(java.util.List)
	 */
	@Override
	public List<Integer> updateFlightDelayForShipments(
			List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResult) throws CustomException {
		for (BreakDownWorkingListShipmentResult bd : breakDownWorkingListShipmentResult) {
			ShipmentRemarksModel remarks = new ShipmentRemarksModel();
			remarks.setShipmentId(bd.getShipmentId());
			remarks.setShipmentNumber(bd.getShipmentNumber());
			remarks.setShipmentdate(bd.getShipmentdate());
			remarks.setFlightId(bd.getFlightId());
			remarks.setRemarkType(ShipmentRemarksType.FLIGHT_DELAY.getType());
			remarks.setShipmentRemarks(HardcodedParam.FLIGHT_DELAY.toString());
			remarks.setCreatedBy(HardcodedParam.CREATED_USER.toString());
			remarks.setCreatedOn(LocalDateTime.now());
			shipRemarksService.createShipmentRemarks(remarks);
		}

		return null;
	}

	@Override
	public BreakDownWorkingListModel reOpenBreakDownComplete(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException {
		// 1. Update Imp_FlightEvents null break down complete status and not 1st time
		// break down complete data time
		breakDownWorkingListDAO.updateBreakdownCompleteNextTimeStatus(breakDownWorkingListModel);
		// 2. Re-open inward report finalize status
		breakDownWorkingListDAO.reopenFinalizeStatus(breakDownWorkingListModel);
		// 3. Re-open the flight complete
		int isFlightCompleted = breakDownWorkingListDAO.isFlightCompleted(breakDownWorkingListModel);
		if (isFlightCompleted > 0) {
			breakDownWorkingListDAO.reopenFlightComplete(breakDownWorkingListModel);
		}
		return null;
	}

	@Override
	public BreakDownWorkingListModel breakDownComplete(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException {

		// Check for Document Complete and Break Down Complete
		FlightValidateModel flight = new FlightValidateModel();
		flight.setFlightKey(breakDownWorkingListModel.getFlightNumber());
		flight.setFlightDate(breakDownWorkingListModel.getFlightDate());
		
		//validate IGM for international Airline(AISATS)
		//check feature enabled
		if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.CustomFlightNumberRequiredForDocumentComplete.class)) {
			this.validateFlightIGMInformation(breakDownWorkingListModel);
		}

		// Check for Document Complete
		boolean isDocumentComplete = flightValidationDao.isDocumentVerificationComplete(flight);
		breakDownWorkingListModel.setDocumentCompleted(isDocumentComplete);
		if (!isDocumentComplete && FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.DocumentCompleteRequiredForBreakdownComplete.class)) {
			throw new CustomException("imp.bd.workinglist.document.complete.check", null, ErrorType.ERROR);
		}

		// Create the irregularity for shipments which has discrepanacy in piece/weight
		List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancy = new ArrayList<>();
		
		List<ShipmentIrregularityModel> shipmentIrregularityModelList = new ArrayList<>();
		if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {
			// Get the irregularity information for Shipment + HAWB
			List<ShipmentIrregularityModel> irregularityForShipmentHouse = breakDownWorkingListDAO
					.getIrregularityForHawbHandling(breakDownWorkingListModel);

			// Aggregate the information by Shipment Number
			if (!CollectionUtils.isEmpty(irregularityForShipmentHouse)) {
				Map<String, List<ShipmentIrregularityModel>> groupByShipmentIrregularity = irregularityForShipmentHouse
						.stream().collect(Collectors.groupingBy(ShipmentIrregularityModel::getShipmentNumber));
				if (!CollectionUtils.isEmpty(groupByShipmentIrregularity)) {
					// Not matching list
					List<String> notMatchingShipmentIrregularityInfo = new ArrayList<>();

					for (Map.Entry<String, List<ShipmentIrregularityModel>> entry : groupByShipmentIrregularity
							.entrySet()) {

						// List of irregularities by shipment
						List<ShipmentIrregularityModel> irregularities = entry.getValue();

						// Check if Shipment OR Shipment + House is size > 0 if yes then continue to
						// check piece/weight and do this for only manifested shipments
						if (!CollectionUtils.isEmpty(irregularities) && irregularities.size() > 1
								&& !ObjectUtils.isEmpty(irregularities.get(0).getManifestPieces())) {
							// AWB manifested Pcs > (Sum of Inventory Pcs for the flight + Sum of MSCA Pcs
							// for the flight – sum of FDCA Pcs for the flight )
							// Error Message – <AWB No> - HWB Irregularity is incomplete.
							Boolean isManifestPiecesNotMatchingWithHouseBreakDownPieces = this.shipmentIrregularityService
									.validatePieceInfoForMorethanOneHouseShipment(irregularities.get(0));
							if (isManifestPiecesNotMatchingWithHouseBreakDownPieces) {
								notMatchingShipmentIrregularityInfo.add(entry.getKey());
							}
						} else if (irregularities.size() == 1
								&& !StringUtils.isEmpty(irregularities.get(0).getHawbNumber())
								&& !ObjectUtils.isEmpty(irregularities.get(0).getManifestPieces())
								&& irregularities.get(0).getManifestPieces()
										.compareTo(irregularities.get(0).getHawbPieces()) == 0) {
							// Add Shipment with only one house
							shipmentIrregularityModelList.addAll(irregularities);
						} else if (StringUtils.isEmpty(irregularities.get(0).getHawbNumber())) {
							// Add Shipment with no house
							shipmentIrregularityModelList.addAll(irregularities);
						}
					}

					// If the shipment are not matching total house break down pieces then throw the
					// error
					if (!CollectionUtils.isEmpty(notMatchingShipmentIrregularityInfo)) {
						throw new CustomException("imp.bd.wrk.list.man.shp.irr.not.matching", null, ErrorType.ERROR,
								new String[] { notMatchingShipmentIrregularityInfo.toString() });
					}
				}
			}
			
		} else {
			shipmentIrregularityModelList = breakDownWorkingListDAO.getIrregularity(breakDownWorkingListModel);
		}
		
		// Update the first break down completed first time status
		breakDownWorkingListDAO.updateBreakdownCompleteFirstTimeStatus(breakDownWorkingListModel);

		for (ShipmentIrregularityModel t : shipmentIrregularityModelList) {
			t.setLoggedInUser(breakDownWorkingListModel.getLoggedInUser());
			if (t.getPiece().compareTo(BigInteger.ZERO) == 0 && StringUtils.isEmpty(t.getCargoIrregularityCode())) {
				breakDownWorkingListDAO.updateTracingInfo(t);
			} else {
				// Do not insert damage information as part of irregularity since damage
				// functionality is separately available.
				if (!"DAMG".equalsIgnoreCase(t.getCargoIrregularityCode())) {
					shipmentIrregularityService.createIrregularity(t);
				}
			}
			// Populate irregulrity info
			InwardServiceReportShipmentDiscrepancyModel serviceReportShipmentDiscrepancyModel = new InwardServiceReportShipmentDiscrepancyModel();
			serviceReportShipmentDiscrepancyModel.setShipmentdate(t.getShipmentdate());
			serviceReportShipmentDiscrepancyModel.setShipmentNumber(t.getShipmentNumber());
			serviceReportShipmentDiscrepancyModel.setDiscrepancyType(t.getDiscrepancyType());
			serviceReportShipmentDiscrepancyModel.setIrregularityDescription(t.getIrregularityRemarks());
			serviceReportShipmentDiscrepancyModel.setIrregularityPieces(t.getPiece());
			serviceReportShipmentDiscrepancyModel.setIrregularityType(t.getCargoIrregularityCode());
			serviceReportShipmentDiscrepancyModel.setOrigin(t.getOrigin());
			serviceReportShipmentDiscrepancyModel.setDestination(t.getDestination());
			serviceReportShipmentDiscrepancyModel.setWeight(t.getWeight());
			serviceReportShipmentDiscrepancyModel.setWeightUnitCode(t.getWeightUnitCode());
			serviceReportShipmentDiscrepancyModel.setPiece(t.getDocumentedPieces());
			serviceReportShipmentDiscrepancyModel.setShipmentType(ShipmentType.Type.AWB);
			serviceReportShipmentDiscrepancyModel.setTotalPieces(t.getTotalPieces());
			serviceReportShipmentDiscrepancyModel.setLoggedInUser(t.getLoggedInUser());
			if (!StringUtils.isEmpty(t.getHawbNumber())) {
				serviceReportShipmentDiscrepancyModel.setHawbNumber(t.getHawbNumber());
			}
			serviceReportShipmentDiscrepancyModel.setManual(Boolean.FALSE);

			// Add it to list
			physicalDiscrepancy.add(serviceReportShipmentDiscrepancyModel);
		}

		// Create service report on flight completed
		InwardServiceReportModel inwardServiceReportModel = new InwardServiceReportModel();
		inwardServiceReportModel.setServiceReportFor(ShipmentType.Type.AWB);
		inwardServiceReportModel.setFlightId(breakDownWorkingListModel.getFlightId());
		inwardServiceReportModel.setPhysicalDiscrepancy(physicalDiscrepancy);
		inwardServiceReportModel.setLoggedInUser(breakDownWorkingListModel.getLoggedInUser());
		inwardServiceReportModel.setCreatedBy(breakDownWorkingListModel.getCreatedBy());
		inwardServiceReportModel.setCreatedOn(breakDownWorkingListModel.getCreatedOn());
		inwardReportService.createServiceReportOnFlightComplete(inwardServiceReportModel);

		boolean rcfSent = false;
		// Raise the event on inbound break down completed

		// filtering shipments and trigger RCF/NFD once in a time
		HashMap<String, Integer> shipmentNumbers = new HashMap<String, Integer>();

		for (BreakDownWorkingListShipmentResult breakDownWorkingListShipments : breakDownWorkingListModel
				.getBreakDownWorkingListShipmentResult()) {
			// get RCF StatusUpdateEventPieces for the given Shipment
				Boolean rcfStatusUpdateEventPieces = breakDownWorkingListDAO
						.getTotalRCFStatusUpdateEventShipmentPieces(breakDownWorkingListShipments);

				BreakDownWorkingListShipmentResult breakDownPiecesWeight = breakDownWorkingListDAO
						.getBreakdownPiecesWeight(breakDownWorkingListShipments);

				if (!shipmentNumbers.containsKey(breakDownWorkingListShipments.getShipmentNumber())) {
					Integer i = 0;
					if (Objects.nonNull(breakDownWorkingListShipments.getShipmentId())
							&& (rcfStatusUpdateEventPieces == null || rcfStatusUpdateEventPieces)) {
						InboundShipmentBreakDownCompleteStoreEvent event = new InboundShipmentBreakDownCompleteStoreEvent();
						event.setShipmentId(breakDownWorkingListShipments.getShipmentId());
						event.setFlightId(breakDownWorkingListShipments.getFlightId());
						event.setPieces(breakDownPiecesWeight.getBdPieces());
						event.setWeight(breakDownPiecesWeight.getBdWeight());
						event.setStatus(EventStatus.NEW.getStatus());
						event.setCompletedAt(LocalDateTime.now());
						event.setCompletedBy(breakDownWorkingListModel.getCreatedBy());
						event.setCreatedOn(LocalDateTime.now());
						event.setCreatedBy(breakDownWorkingListModel.getCreatedBy());
						event.setFunction("Inbound Break Down Complete");
						event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_BREAK_DOWN_COMPLETE_EVENT);

						producer.publish(event);
						shipmentNumbers.put(breakDownWorkingListShipments.getShipmentNumber(), i++);
						rcfSent = true;
					}

				}
			}

		// Filter out shipments which have irregularities
		List<BreakDownWorkingListShipmentResult> notIrregularityList = new ArrayList<>();
		for (BreakDownWorkingListShipmentResult t : breakDownWorkingListModel.getBreakDownWorkingListShipmentResult()) {
			// Check for irregularity
			boolean irregularityExists = false;
			if (!CollectionUtils.isEmpty(shipmentIrregularityModelList)) {
				for (ShipmentIrregularityModel s : shipmentIrregularityModelList) {
					if (!StringUtils.isEmpty(t.getShipmentNumber())
							&& t.getShipmentNumber().equalsIgnoreCase(s.getShipmentNumber())) {
						irregularityExists = true;
						break;
					}
				}
			}

			if (!irregularityExists) {
				notIrregularityList.add(t);
			}
		}

		// On break down complete if document is completed capture the local authority
		// info for transhipment shipments
		if (isDocumentComplete) {
			// Transhipment Cases update Local Authority details
			if (!CollectionUtils.isEmpty(breakDownWorkingListModel.getBreakDownWorkingListShipmentResult())) {
				for (BreakDownWorkingListShipmentResult shipmentData : breakDownWorkingListModel
						.getBreakDownWorkingListShipmentResult()) {
					if (Objects.nonNull(shipmentData) && Objects.nonNull(shipmentData.getDestination())) {
						if (!MultiTenantUtility.isTenantCityOrAirport(shipmentData.getDestination())) {
							breakDownWorkingListDAO.insertLocalAuthorityDetails(shipmentData);
						}
					}
				}
			}

			// Update flight completed status
			breakDownWorkingListDAO.updateFlightComplete(breakDownWorkingListModel);

				// Raise event for post processing General Shipments
				GeneralShipmentsEvent generalShipmentsEvent = new GeneralShipmentsEvent();
				generalShipmentsEvent.setFlightId(breakDownWorkingListModel.getFlightId());
				generalShipmentsEvent.setFlightKey(breakDownWorkingListModel.getFlightNumber());
				generalShipmentsEvent.setFlightDate(breakDownWorkingListModel.getFlightDate());
				generalShipmentsEvent.setTenantId(breakDownWorkingListModel.getTenantAirport());
				generalShipmentsEvent.setCreatedOn(LocalDateTime.now());
				generalShipmentsEvent.setCreatedBy(breakDownWorkingListModel.getLoggedInUser());

				// Invoke the event
				this.generalShipmentsEventProducer.publish(generalShipmentsEvent);

				// Raise event for post processing EAP shipments
				EAPShipmentsEvent eapShipmentsEvent = new EAPShipmentsEvent();
				eapShipmentsEvent.setFlightId(breakDownWorkingListModel.getFlightId());
				eapShipmentsEvent.setFlightKey(breakDownWorkingListModel.getFlightNumber());
				eapShipmentsEvent.setFlightDate(breakDownWorkingListModel.getFlightDate());
				eapShipmentsEvent.setTenantId(breakDownWorkingListModel.getTenantAirport());
				eapShipmentsEvent.setCreatedOn(LocalDateTime.now());
				eapShipmentsEvent.setCreatedBy(breakDownWorkingListModel.getLoggedInUser());

				// Invoke the event
				this.eapShipmentsEventProducer.publish(eapShipmentsEvent);

				// Raise event for post processing EAW shipments
				EAWShipmentsEvent eawShipmentsPayload = new EAWShipmentsEvent();
				eawShipmentsPayload.setFlightId(breakDownWorkingListModel.getFlightId());
				eawShipmentsPayload.setFlightKey(breakDownWorkingListModel.getFlightNumber());
				eawShipmentsPayload.setFlightDate(breakDownWorkingListModel.getFlightDate());
				eawShipmentsPayload.setTenantId(breakDownWorkingListModel.getTenantAirport());
				eawShipmentsPayload.setCreatedOn(LocalDateTime.now());
				eawShipmentsPayload.setCreatedBy(breakDownWorkingListModel.getLoggedInUser());

				// Invoke the event
				this.eawShipmentsEventProducer.publish(eawShipmentsPayload);
			}

		// Raise event for DataSync
		// check flightHandled in system
		if (breakDownWorkingListDAO.isDataSyncCREnabled() && rcfSent) {
			if (!CollectionUtils.isEmpty(breakDownWorkingListModel.getBreakDownWorkingListShipmentResult())) {
				for (BreakDownWorkingListShipmentResult shipmentData : breakDownWorkingListModel
						.getBreakDownWorkingListShipmentResult()) {
					if (!StringUtils.isEmpty(shipmentData.getDestination())
							&& !MultiTenantUtility.isTenantCityOrAirport(shipmentData.getDestination())
							&& breakDownWorkingListDAO.isFlighHandledInSystem(shipmentData.getShipmentNumber())) {

						// fetch inventory and send the message for each partsuffix.
						List<String> partSuffixs = breakDownWorkingListDAO.getpartSuffixForshipment(shipmentData);
						DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();
						eventPayload.setShipmentId(shipmentData.getShipmentId());
						eventPayload.setFlightId(breakDownWorkingListModel.getFlightId());
						eventPayload.setStatus(EventStatus.NEW.getStatus());
						eventPayload.setConfirmedAt(LocalDateTime.now());
						eventPayload.setConfirmedBy("BATCH");
						eventPayload.setCreatedBy("BATCH");
						eventPayload.setCreatedOn(LocalDateTime.now());
						eventPayload.setFunction("Data Sync Job");
						eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);
						if (CollectionUtils.isEmpty(partSuffixs)) {
							dataEventProducer.publish(eventPayload);
						} else {
							for (String suffix : partSuffixs) {
								eventPayload.setPartSuffix(suffix);
								dataEventProducer.publish(eventPayload);
							}
						}

					}
				}
			}
		}
		// Create Break Down Summary
		BreakDownSummary requestModel = new BreakDownSummary();
		requestModel.setFlightId(breakDownWorkingListModel.getFlightId());
		this.breakDownSummaryService.createBreakDownSummaryOnFlightComplete(requestModel);

		return breakDownWorkingListModel;
	}

	@Override
	public void breakdownWorkingListFlightCompleteEventInitialize(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException {
		// Check the flight completed status
		int isFlightCompleted = breakDownWorkingListDAO.isFlightCompleted(breakDownWorkingListModel);
		if (isFlightCompleted > 0) {

			// Raise ARM event
			InboundFlightCompleteStoreEvent flightCompleteEvent = new InboundFlightCompleteStoreEvent();
			flightCompleteEvent.setFlightId(breakDownWorkingListModel.getFlightId());
			flightCompleteEvent.setShipmentType("CARGO");
			flightCompleteEvent.setStatus(EventStatus.Type.NEW);
			flightCompleteEvent.setCreatedBy(breakDownWorkingListModel.getCreatedBy());
			flightCompleteEvent.setCreatedOn(LocalDateTime.now());
			flightCompleteProducer.publish(flightCompleteEvent);

			//
			for (BreakDownWorkingListShipmentResult breakDownWorkingListShipments : breakDownWorkingListModel
					.getBreakDownWorkingListShipmentResult()) {
				if (Objects.nonNull(breakDownWorkingListShipments)) {
					// IVRS Event Call
					ShipmentNotification shipmentNotification = new ShipmentNotification();
					shipmentNotification.setShipmentNumber(breakDownWorkingListShipments.getShipmentNumber());
					shipmentNotification.setShipmentDate(breakDownWorkingListShipments.getShipmentdate());
					ShipmentNotificationDetail notificationDetail = breakDownWorkingListDAO
							.getShipmentNotificationDetail(shipmentNotification);
					if (Objects.nonNull(notificationDetail)) {
						if ("TE".equalsIgnoreCase(notificationDetail.getContactTypeCode())) {
							shipmentNotification.setNotificationType("IVRS");
							eventNoftificationProducer.publish(shipmentNotification);
						} else if ("FX".equalsIgnoreCase(notificationDetail.getContactTypeCode())) {
							shipmentNotification.setNotificationType("FAX");
							eventNoftificationProducer.publish(shipmentNotification);
						} else if ("SM".equalsIgnoreCase(notificationDetail.getContactTypeCode())) {
							SMSEvent smsEvent = new SMSEvent();
							smsEvent.setNumber(
									BigInteger.valueOf(Long.parseLong(notificationDetail.getContactTypeDetail())));
							TemplateBO template = new TemplateBO();
							template.setTemplateName("SHIPMENT_NOTIFICATION_SMS");
							Map<String, String> mapParameters = new HashMap<>();
							mapParameters.put("ShipmentNumber", shipmentNotification.getShipmentNumber());
							mapParameters.put("Consignee", notificationDetail.getCustomerName());
							template.setTemplateParams(mapParameters);
							smsEvent.setTemplate(template);
							smsProducer.publish(smsEvent);
						} else if ("EM".equalsIgnoreCase(notificationDetail.getContactTypeCode())) {
							notificationDetail.setTenantId(MultiTenantUtility.getAirportCodeFromContext());
							// EMail code
							System.out.println("Breakdown WorkingList Service - Shipent Notification EM code - "
									+ shipmentNotification.getShipmentNumber());
							resendEmailNotification(notificationDetail, breakDownWorkingListModel.getFlightNumber(),
									breakDownWorkingListModel.getFlightDate());
						}
					}
				}
			}
		}
	}
	
	private void validateFlightIGMInformation(BreakDownWorkingListModel breakDownWorkingListModel)  throws CustomException  {
		Boolean international =this.breakDownWorkingListDAO.getFlightType(breakDownWorkingListModel);	
		String igmNumber =this.breakDownWorkingListDAO.getFlightIgmNumber(breakDownWorkingListModel);	
		// derive Domestic && International
		if (international && StringUtils.isEmpty(igmNumber)) {   
			throw new CustomException("import.warn115",null, "E");
		}
	}

	@Override
	public void sendLhRcfNfdReport(BreakDownWorkingListModel breakDownWorkingListModel) throws CustomException {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("flightId", breakDownWorkingListModel.getFlightId());
		reportParams.put("carrierCode", breakDownWorkingListModel.getCarrierCode());

		// get Mail ids which configured
		List<String> getMails = breakDownWorkingListDAO.getEmailsForLhCarrier(breakDownWorkingListModel);
		String[] mailIds = getMails.toArray(new String[getMails.size()]);

		ReportMailPayload reportMailPayload = new ReportMailPayload();
		reportMailPayload.setReportFormat(ReportFormat.XLS);
		reportMailPayload.setReportParams(reportParams);
		List<ReportMailPayload> reportPayload = new ArrayList<>();

		LocalDate today = breakDownWorkingListModel.getFlightDate();
		String dateFormat = today.format(DateTimeFormatter.ofPattern("ddMMMYYYY"));

		EMailEvent emailEvent1 = new EMailEvent();
		reportMailPayload.setReportName(LH_RCF_NFD);
		reportMailPayload
				.setFileName(LH_RCF_NFD + "_" + breakDownWorkingListModel.getFlightNumber() + "_" + dateFormat);
		emailEvent1.setMailToAddress(mailIds);
		emailEvent1.setMailSubject(LH_RCF_NFD + "_" + breakDownWorkingListModel.getFlightNumber() + "/" + dateFormat);
		emailEvent1.setMailBody(
				"PFA ," + LH_RCF_NFD + "_" + breakDownWorkingListModel.getFlightNumber() + "/" + dateFormat);
		emailEvent1.setNotifyAddress(null);
		reportPayload.add(reportMailPayload);
		reportMailService.sendReport(reportPayload, emailEvent1);
	}

	@Override
	public void validateDataAndTriggerEvents(SendTSMMessage requestModel) throws CustomException {
		for (SendTSMMessage model : requestModel.getShipments()) {

			if (!StringUtils.isEmpty(requestModel.getFlightKey()) && requestModel.getFlightDate() == null) {
				requestModel.addError("error.enter.flight.date.for.flightkey", "", ErrorType.ERROR,
						new String[] { requestModel.getFlightKey() });
				throw new CustomException("error.enter.flight.date.for.flightkey", "", ErrorType.ERROR,
						new String[] { requestModel.getFlightKey() });
			}
			this.breakDownWorkingListDAO.validateDataAndTriggerEvents(model);

			if (!StringUtils.isEmpty(requestModel.getFlightKey()) && model.getFlightId() == null) {
				throw new CustomException("NO_RECORDS_EXIST", requestModel.getFlightKey(), ErrorType.ERROR);
			}
			if (model.getShipmentId() == null) {
				requestModel.addError("NO_RECORDS_EXIST", requestModel.getShipmentNumber(), ErrorType.ERROR);
				throw new CustomException("NO_RECORDS_EXIST", requestModel.getShipmentNumber(), ErrorType.ERROR);
			}
			DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();

			eventPayload.setShipmentId(model.getShipmentId());
			eventPayload.setFlightId(model.getFlightId());
			eventPayload.setStatus(EventStatus.NEW.getStatus());
			eventPayload.setConfirmedAt(LocalDateTime.now());
			eventPayload.setConfirmedBy("BATCH");
			eventPayload.setCreatedBy("BATCH");
			eventPayload.setCreatedOn(LocalDateTime.now());
			eventPayload.setFunction("Data Sync Job");
			eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);
			eventPayload.setPartSuffix(model.getPartSuffx());
			dataEventProducer.publish(eventPayload);
		}
	}

	/**
	 * @param notificationDetail
	 * @param flightNumber
	 * @param flightDate
	 * @throws CustomException
	 */
	private void resendEmailNotification(ShipmentNotificationDetail notificationDetail, String flightNumber,
			LocalDate flightDate) throws CustomException {
		// CASE General shipments
		List<AwbNotificationShipment> notifyGeneralShipments = dao.getIVRSGeneralShipments(notificationDetail);
		resendEmail(notifyGeneralShipments, flightNumber, flightDate);
		// CASE EAW Shipments
		List<AwbNotificationShipment> notifyEAWShipments = dao.getIVRSEAWShipments(notificationDetail);
		resendEmail(notifyEAWShipments, flightNumber, flightDate);
		// CASE EAP Shipments
		List<AwbNotificationShipment> notifyEAPShipments = dao.getIVRSEAPShipments(notificationDetail);
		resendEmail(notifyEAPShipments, flightNumber, flightDate);
	}

	private void resendEmail(List<AwbNotificationShipment> notifyShipments, String flightNumber, LocalDate flightDate)
			throws CustomException {
		if (!CollectionUtils.isEmpty(notifyShipments)) {
			//
			for (AwbNotificationShipment notifyShipment : notifyShipments) {
				// If only when email addresses are present for each customer
				if (!StringUtils.isEmpty(notifyShipment.getEmailAddresses())) {
					// Set the base email template data
					EMailEvent emailEvent = new EMailEvent();
					// Set the email to
					String[] emaildAddresses = notifyShipment.getEmailAddresses().split(",");
					emailEvent.setMailToAddress(emaildAddresses);
					// No CC address
					emailEvent.setMailCC("");
					// Body of the message
					emailEvent.setMailBody("SHIPMENT ARRIVAL NOTICE - E-FREIGHT SHIPMENT - [DO NOT REPLY]");
					// Subject line of the message
					emailEvent.setMailSubject("SHIPMENT ARRIVAL NOTICE (IVRS) - E-FREIGHT SHIPMENT - [DO NOT REPLY]");
					// Email Template - Settings
					TemplateBO template = new TemplateBO();
					template.setTemplateName("E-FREIGHT SHIPMENT ARRIVAL NOTICE");
					// Parameters
					Map<String, String> mapParameters = new HashMap<>();
					mapParameters.put("FlightKey", notifyShipment.getFlightKey());
					mapParameters.put("Consignee", notifyShipment.getCustomerName());
					DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
							.appendPattern("ddMMMyyyy").toFormatter();
					mapParameters.put("ToDate", LocalDate.now().format(formatter).toString().toUpperCase());
					// For each customer build notifications on shipments
					if (!CollectionUtils.isEmpty(notifyShipment.getShipments())) {
						//
						List<MessageReferenceDetail> referenceDetails = new ArrayList<>();
						StringBuilder sb = new StringBuilder();
						sb.append("<html>");
						sb.append("<head>");
						sb.append("<style>");
						sb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
								+ "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
								+ "  border-bottom: 1px solid #ddd;\r\n" + "}");
						sb.append("</style>");
						sb.append("</head>");
						sb.append("<body>");
						sb.append("<div>");
						sb.append("<table>");
						sb.append("<thead>");
						sb.append("<th> AWB NO </th>");
						sb.append("<th> ORG </th>");
						sb.append("<th> DES </th>");
						sb.append("<th> PCS/WGT </th>");
						sb.append("<th> TTL PCS/WGT </th>");
						sb.append("<th> Flight/Date </th>");
						sb.append("<th> Collection Terminal </th>");
						sb.append("<th> Consignee </th>");
						sb.append("<th> DocumentPouchReceived </th>");
						sb.append("<th> Charges </th>");
						sb.append("</thead>");
						sb.append("<tbody>");
						// Iterate each shipment list for appending to email
						for (AwbNotificationShipment shipment : notifyShipment.getShipments()) {
							//
							MessageReferenceDetail reference = new MessageReferenceDetail();
							reference.setMessageReferenceNumber(shipment.getShipmentNumber());
							reference.setMessageReferenceDate(shipment.getShipmentDate());
							referenceDetails.add(reference);
							//
							sb.append("<tr>");
							sb.append("<td> " + shipment.getShipmentNumber() + " </td>");
							sb.append("<td> " + shipment.getOrigin() + " </td>");
							sb.append("<td> " + shipment.getDestination() + " </td>");
							sb.append("<td> " + shipment.getPieces() + "/" + shipment.getWeight() + " </td>");
							sb.append("<td> " + shipment.getShipmentPieces() + "/" + shipment.getShipmentWeight()
									+ " </td>");
							sb.append("<td> " + flightNumber + "/"
									+ flightDate.format(formatter).toString().toUpperCase() + " </td>");
							sb.append("<td> " + shipment.getTerminal() + " </td>");
							sb.append("<td> " + shipment.getConsigneeName() + " </td>");

							// Set the document received to "Y"/"N". Either Original/Pouch should have been
							// received for a shipment
							if (shipment.getDocumentReceived()) {
								sb.append("<td> Y </td>");
							} else {
								sb.append("<td> N </td>");
							}
							sb.append("</tr>");
						}
						sb.append("</tbody>");
						sb.append("</table>");
						sb.append("</div>");
						sb.append("</body>");
						sb.append("</html>");
						template.setTemplateParams(mapParameters);
						template.setTemplateTable(sb.toString());
						emailEvent.setTemplate(template);
						emailEvent.setMessageReferenceDetails(referenceDetails);
						// Send email
						emailPublisher.publish(emailEvent);
					}
				}
			}
		}
	}

	@Override
	public void reSendSegregationReport(BreakDownWorkingListModel requestModel) throws CustomException {
		SegregationReportEvent segregationReportEvent = new SegregationReportEvent();
		segregationReportEvent.setFlightId(requestModel.getFlightId());
		segregationReportEvent.setCreatedBy(requestModel.getLoggedInUser());
		segregationReportEvent.setCreatedOn(LocalDateTime.now());
		segregationReportEvent.setEventName(EventTypes.Names.SEGREGATION_REPORT_MESSAGE_EVENT);
		segregationReportEvent.setStatus(EventStatus.NEW.getStatus());
		segregationReportEventProducer.publish(segregationReportEvent);
	}

}