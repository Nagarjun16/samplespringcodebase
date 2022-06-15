/**
 * Service implementation component for processing shipments on document
 * complete for a flight
 */
package com.ngen.cosys.impbd.shipment.verification.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.MessageReferenceDetail;
import com.ngen.cosys.events.payload.InboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDocumentCompleteStoreEvent;
import com.ngen.cosys.events.payload.SMSEvent;
import com.ngen.cosys.events.payload.ShipmentNotification;
import com.ngen.cosys.events.producer.InboundFlightCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentDocumentCompleteStoreEventProducer;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.producer.SendSMSEventProducer;
import com.ngen.cosys.events.producer.ShipmentNotificationEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.export.commonbooking.Dao.CommonBookingDao;
import com.ngen.cosys.export.commonbooking.model.CommonBooking;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.awb.notification.model.AwbNotificationShipment;
import com.ngen.cosys.impbd.events.dao.ImpBdEventDao;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.impbd.events.producer.EAPShipmentsEventProducer;
import com.ngen.cosys.impbd.events.producer.EAWShipmentsEventProducer;
import com.ngen.cosys.impbd.events.producer.GeneralShipmentsEventProducer;
import com.ngen.cosys.impbd.events.producer.SingaporeCustomsDataSyncProducer;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;
import com.ngen.cosys.impbd.printer.util.PrinterService;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.shipment.irregularity.service.ShipmentIrregularityService;
import com.ngen.cosys.impbd.shipment.remarks.constant.ShipmentRemarksType;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;
import com.ngen.cosys.impbd.shipment.remarks.service.ShipmentRemarksService;
import com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAO;
import com.ngen.cosys.impbd.shipment.verification.dao.DocumentVerificationDAOImpl;
import com.ngen.cosys.impbd.shipment.verification.model.BookingInfo;
import com.ngen.cosys.impbd.shipment.verification.model.DgRegulations;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationShipmentModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModelList;
import com.ngen.cosys.impbd.shipment.verification.model.SearchDGDeclations;
import com.ngen.cosys.impbd.shipment.verification.model.SearchRegulationDetails;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclaration;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclarationDetail;
import com.ngen.cosys.impbd.shipment.verification.model.UNIDOverpackDetails;
import com.ngen.cosys.impbd.workinglist.dao.BreakDownWorkingListDAO;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.inward.service.InwardServiceReportService;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.FlightInfo;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.validator.enums.ShipmentType;
import com.ngen.cosys.validator.utils.FlightHandlingSystemValidator;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DocumentVerificationServiceImpl implements DocumentVerificationService {

	@Autowired
	private DocumentVerificationDAO documentVerificationDAO;

	@Autowired
	private ShipmentVerificationService shipmentVeriService;

	@Autowired
	private ShipmentMasterService shipmentMasService;

	@Autowired
	private ShipmentRemarksService shipRemarksService;

	@Autowired
	private ShipmentIrregularityService shipmentIrregularityService;

	@Autowired
	private InwardServiceReportService inwardReportService;

	@Autowired
	InboundShipmentDocumentCompleteStoreEventProducer producer;

	@Autowired
	private InboundFlightCompleteStoreEventProducer flightCompleteProducer;

	@Autowired
	private SingaporeCustomsDataSyncProducer singaporeCustomsDataSyncProducer;

	@Autowired
	private EAPShipmentsEventProducer eapShipmentsEventProducer;

	@Autowired
	private EAWShipmentsEventProducer eawShipmentsEventProducer;

	@Autowired
	private GeneralShipmentsEventProducer generalShipmentsEventProducer;

	@Autowired
	private PrinterService printerService;

	@Autowired
	ShipmentNotificationEventProducer eventNoftificationProducer;

	@Autowired
	SendSMSEventProducer smsProducer;

	@Autowired
	SendEmailEventProducer emailPublisher;

	@Autowired
	private ImpBdEventDao dao;

	@Autowired
	private FlightHandlingSystemValidator handlingSystemValidator;
	
	@Autowired
	private CommonBookingService commonBookingService;
	
	@Autowired
	private CommonBookingDao commonBookingDao;
	
	@Autowired
	private BreakDownWorkingListDAO workinglistDao;
	

	@Override
	public DocumentVerificationFlightModel fetch(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		documentVerificationFlightModel = documentVerificationDAO.get(documentVerificationFlightModel);
		Integer docRecievedCount = 0;
		Integer eawbCount = 0;
		
		for (DocumentVerificationShipmentModel element : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {
			if (element.getAwbWeight() != null) {
				element.setAwbPieceWeight(element.getAwbPieces() + " / " + element.getAwbWeight());
			} else {
				element.setAwbPieceWeight(element.getAwbPieces() + "");
			}
			if (element.getManifestWeight() != null) {
				element.setManifestPiecesWeight(element.getManifestPieces() + " / " + element.getManifestWeight());
			} else {
				element.setManifestPiecesWeight(element.getManifestPieces() + "");
			}
			if ((!ObjectUtils.isEmpty(element.getDocRecieved()) && element.getDocRecieved())
					|| (!ObjectUtils.isEmpty(element.getCopyAwb()) && element.getCopyAwb())) {
				docRecievedCount++;
			}
			if (!StringUtils.isEmpty(element.getEawb()) && element.getEawb().equalsIgnoreCase("Y")) {
				eawbCount++;
			}
			if (((!StringUtils.isEmpty(element.getEawb()) && element.getEawb().equalsIgnoreCase("Y"))
					|| (!StringUtils.isEmpty(element.getEaw()) && element.getEaw().equalsIgnoreCase("Y"))
					|| (!StringUtils.isEmpty(element.getEap()) && element.getEap().equalsIgnoreCase("Y")))
					&& (!StringUtils.isEmpty(element.getFwb()) && element.getFwb().equalsIgnoreCase("Y"))
					&& (!ObjectUtils.isEmpty(element.getCopyAwb()) && !element.getCopyAwb())) {
				element.setDocRecieved(Boolean.TRUE);
			}


			if (!MultiTenantUtility.isTenantCityOrAirport(element.getOrigin())
					&& !MultiTenantUtility.isTenantCityOrAirport(element.getDestination())) {
				
				element.setSegOrign("TRANSHIPMENT");
			} else {
				element.setSegOrign("LOCAL");
			}
		}
		
		// record index
		int index = 1;

		// Filter and then set the index
		List<DocumentVerificationShipmentModel> localShipments = documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList().stream()
				.filter(i -> "LOCAL".equalsIgnoreCase(i.getSegOrign())).collect(Collectors.toList());

		for (DocumentVerificationShipmentModel l : localShipments) {
			l.setSerialNo(index);
			index++;
		}

		List<DocumentVerificationShipmentModel> transhipmentShipments = documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList().stream()
				.filter(i -> "TRANSHIPMENT".equalsIgnoreCase(i.getSegOrign())).collect(Collectors.toList());

		for (DocumentVerificationShipmentModel t : transhipmentShipments) {
			t.setSerialNo(index);
			index++;
		}
		
		documentVerificationFlightModel.setEawbCount(eawbCount);
		documentVerificationFlightModel.setDocRecievedCount(docRecievedCount);
		FlightInfo flightInfo = new FlightInfo();
		flightInfo.setFlightKey(documentVerificationFlightModel.getFlightNumber());
		flightInfo.setFlightDate(documentVerificationFlightModel.getFlightDate());
		flightInfo.setType("I");
		Boolean handlingSystem = handlingSystemValidator.isFlightHandlinginSystem(flightInfo);
		documentVerificationFlightModel.setHandlinginSystem(handlingSystem);
		return documentVerificationFlightModel;
	}

	@Override
	public DocumentVerificationFlightModel documentsOffload(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		List<DocumentVerificationShipmentModel> documentVerificationShipmentModelList = documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList();
		// insert into shipmentRemarks
		ShipmentRemarksModel remarks = new ShipmentRemarksModel();
		ArrivalManifestShipmentInfoModel arrivalManifestShipmentInfoModel = new ArrivalManifestShipmentInfoModel();
		remarks.setShipmentNumber(documentVerificationShipmentModelList.get(0).getShipmentNumber());
		remarks.setShipmentdate(documentVerificationShipmentModelList.get(0).getShipmentdate());
		remarks.setFlightId(documentVerificationShipmentModelList.get(0).getFlightId());
		remarks.setRemarkType(ShipmentRemarksType.SHIPMENT_OFFLOADED.getType());
		remarks.setShipmentRemarks(
				buildRemarks(documentVerificationFlightModel, documentVerificationShipmentModelList));
		shipRemarksService.createShipmentRemarks(remarks);
		// insert into Imp_InwardServiceReportShipmentDiscrepancy (inwardservicereport)
		InwardServiceReportModel inwardModel = new InwardServiceReportModel();
		InwardServiceReportShipmentDiscrepancyModel descripancyModel = new InwardServiceReportShipmentDiscrepancyModel();
		inwardModel.setFlightId(documentVerificationFlightModel.getFlightId());
		inwardModel.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		inwardModel.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		inwardModel.setBoardingPoint(documentVerificationShipmentModelList.get(0).getOrigin());
		inwardModel.setOffPoint(documentVerificationShipmentModelList.get(0).getDestination());
		descripancyModel.setDiscrepancyType("PHYSICAL");
		descripancyModel.setShipmentNumber(documentVerificationShipmentModelList.get(0).getShipmentNumber());
		descripancyModel.setShipmentdate(documentVerificationShipmentModelList.get(0).getShipmentdate());
		descripancyModel.setPiece(documentVerificationShipmentModelList.get(0).getAwbPieces());
		descripancyModel.setWeight(documentVerificationShipmentModelList.get(0).getAwbWeight());
		descripancyModel.setIrregularityType("OFLD");
		descripancyModel.setNatureOfGoodsDescription(documentVerificationShipmentModelList.get(0).getNatureOfGoods());
		descripancyModel.setOrigin(documentVerificationShipmentModelList.get(0).getOrigin());
		descripancyModel.setDestination(documentVerificationShipmentModelList.get(0).getDestination());
		descripancyModel.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		descripancyModel.setCreatedOn(documentVerificationFlightModel.getCreatedOn());
		descripancyModel.setPiece(documentVerificationShipmentModelList.get(0).getAwbPieces());
		descripancyModel
				.setRemarks(buildRemarks(documentVerificationFlightModel, documentVerificationShipmentModelList));
		documentVerificationDAO.insertOffloadInfoinInwardServiceRerort(inwardModel, descripancyModel);

		arrivalManifestShipmentInfoModel
				.setShipmentNumber(documentVerificationShipmentModelList.get(0).getShipmentNumber());
		arrivalManifestShipmentInfoModel
				.setShipmentdate(documentVerificationShipmentModelList.get(0).getShipmentdate());
		arrivalManifestShipmentInfoModel.setOffloadedFlag(true);
		arrivalManifestShipmentInfoModel
				.setOffloadReasonCode(documentVerificationShipmentModelList.get(0).getOffloadRemarksCode());
		documentVerificationDAO.offloadToArrivalManifest(arrivalManifestShipmentInfoModel);
		return documentVerificationFlightModel;
	}

	private String buildRemarks(DocumentVerificationFlightModel documentVerificationFlightModel,
			List<DocumentVerificationShipmentModel> documentVerificationShipmentModelList) {
		DateTimeFormatter date2 = DateTimeFormatter.ofPattern("ddMMMyyyy");
		LocalDate flightDate = documentVerificationFlightModel.getFlightDate();
		String formatDate2 = date2.format(flightDate);
		StringBuilder builder = new StringBuilder(new String());
		builder.append(documentVerificationShipmentModelList.get(0).getOffloadRemarksCode()).append("   ")
				.append(documentVerificationShipmentModelList.get(0).getAwbPieces()).append("-")
				.append(documentVerificationShipmentModelList.get(0).getAwbWeight()).append("   ")
				.append(documentVerificationFlightModel.getFlightNumber()).append("-").append(formatDate2);
		String remarks = builder.toString();
		if (remarks.length() > 65) {
			remarks = documentVerificationShipmentModelList.get(0).getOffloadRemarksCode();
		}

		return remarks;
	}

	@Override
	public DocumentVerificationFlightModel documentComplete(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		
        int unMatchCount=0;
        String shipments=null;
        		
		for (DocumentVerificationShipmentModel documentVerificationShipmentModel : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {
			documentVerificationShipmentModel.setDocumentCompletedAt(documentVerificationFlightModel.getModifiedOn());
			documentVerificationShipmentModel.setDocumentCompletedBy(documentVerificationFlightModel.getModifiedBy());
			
			//update Booking Pieces with AWB pieces if FFR Pieces not matching FFM pieces	

			if (!MultiTenantUtility.isTenantCityOrAirport(documentVerificationShipmentModel.getDestination())) {
				// booking update
				CommonBooking booking = new CommonBooking();
				booking.setShipmentNumber(documentVerificationShipmentModel.getShipmentNumber());
				booking.setShipmentDate(documentVerificationShipmentModel.getShipmentdate());
				
				// boolean check =commonBookingService.checkDocInOrAcceptanceFinalize(booking);
				
				// update booking data
				booking.setOrigin(documentVerificationShipmentModel.getOrigin());
				booking.setDestination(documentVerificationShipmentModel.getDestination());
				booking.setPieces(documentVerificationShipmentModel.getPiece());
				booking.setWeight(documentVerificationShipmentModel.getWeight());
				booking.setNog(documentVerificationShipmentModel.getNatureOfGoods());
				booking.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
				
				// 1. check booking exists
				BigInteger bookingId = commonBookingDao.checkBookingExists(booking);
				if (bookingId != null) {
					booking.setBookingId(bookingId.longValue());
					// check if pcs/ wt. is 0 set to null for not update case
					if (booking.getPieces() != null && booking.getPieces().compareTo(BigInteger.ZERO) == 0) {
						booking.setPieces(null);
					}
					if (booking.getWeight() != null && booking.getWeight().compareTo(BigDecimal.ZERO) == 0) {
						booking.setWeight(null);
					}
					// check part case
					boolean checkPartExists = commonBookingDao.checkPartCase(booking);
					if (checkPartExists) {
						// simply update AWB line in booking
						List<BookingInfo> partbookingInfoList = documentVerificationDAO.getPartBookingInfo(documentVerificationShipmentModel);

						if (!CollectionUtils.isEmpty(partbookingInfoList)
								&& partbookingInfoList.get(0).getTotalBookingPieces()
										.compareTo(documentVerificationShipmentModel.getManifestPieces()) != 0
								&& partbookingInfoList.get(0).getTotalBookingWeight()
										.compareTo(documentVerificationShipmentModel.getManifestWeight()) != 0) {
							++unMatchCount;
							if(shipments==null) {
								shipments=documentVerificationShipmentModel.getShipmentNumber();
							}else {
								shipments=shipments+","+documentVerificationShipmentModel.getShipmentNumber();
							}
						}
					} else {
						// update total booking in part as well as flight booking table
						commonBookingDao.updateBookingAWbline(booking);
						// update part Booking
						commonBookingDao.updatePartBookingPcsWt(booking);

						// update flightBooking
						commonBookingDao.updateFlightBookingPcsWt(booking);

					}
				}
				
			}
		}
		
		//check count of Shipments
		if(unMatchCount>0) {
			if(StringUtils.isEmpty(documentVerificationFlightModel.getFinalizeCheck()) 
					|| !documentVerificationFlightModel.getFinalizeCheck().equalsIgnoreCase("PROCEED")) {
				  throw new CustomException("DOC_BOOKING_UPDATE", "Booking", ErrorType.ERROR, new String[] { shipments });
				}
		}

		documentVerificationFlightModel.setCreatedBy(documentVerificationFlightModel.getLoggedInUser());
		documentVerificationFlightModel.setTerminal(documentVerificationFlightModel.getTerminal());

		// Check for first time and update the details
		if (!documentVerificationDAO.isChecksforFirstTime(documentVerificationFlightModel)) {
			documentVerificationDAO.updateDocumentCompleteFirstTimeStatus(documentVerificationFlightModel);
		} else {
			// If already completed once then do not change first time and user
			documentVerificationDAO.updateDocumentCompleteNextTimeStatus(documentVerificationFlightModel);
		}

		// discrepancy
		// Populate all irregularity information for the inward service report shipment
		List<ShipmentIrregularityModel> shipmentIrregularityModelList = documentVerificationDAO
				.getIrregularity(documentVerificationFlightModel);

		List<InwardServiceReportShipmentDiscrepancyModel> documentDiscrepancy = new ArrayList<>();

		for (ShipmentIrregularityModel t : shipmentIrregularityModelList) {
			if (!StringUtils.isEmpty(t.getCargoIrregularityCode())) {
				shipmentIrregularityService.createIrregularity(t);

				if ("CPAW".equalsIgnoreCase(t.getCargoIrregularityCode())) {
					ShipmentRemarksModel remarks = new ShipmentRemarksModel();
					remarks.setShipmentNumber(t.getShipmentNumber());
					remarks.setShipmentId(t.getShipmentId());
					remarks.setShipmentdate(t.getShipmentdate());
					remarks.setFlightId(t.getFlightId());
					remarks.setRemarkType("CPY");
					remarks.setShipmentRemarks(t.getIrregularityRemarks());
					remarks.setShipmentType(t.getShipmentType());
					shipRemarksService.createShipmentRemarks(remarks);
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
				serviceReportShipmentDiscrepancyModel.setShipmentType(t.getShipmentType());
				serviceReportShipmentDiscrepancyModel.setManual(Boolean.FALSE);
				serviceReportShipmentDiscrepancyModel.setTerminal(t.getTerminal());
				// Add it to list
				documentDiscrepancy.add(serviceReportShipmentDiscrepancyModel);
			}
		}

		InwardServiceReportModel inwardServiceReportModel = new InwardServiceReportModel();
		inwardServiceReportModel.setServiceReportFor(ShipmentType.Type.AWB);
		inwardServiceReportModel.setFlightId(documentVerificationFlightModel.getFlightId());
		inwardServiceReportModel.setPhysicalDiscrepancy(documentDiscrepancy);
		inwardReportService.createServiceReportOnFlightComplete(inwardServiceReportModel);

		// Check for break down complete
		boolean isBreakDownComplete = this.documentVerificationDAO.isBreakDownComplete(documentVerificationFlightModel);
		if (isBreakDownComplete) {
			// Update the flight complete
			this.documentVerificationDAO.updateFlightComplete(documentVerificationFlightModel);

			// Raise ARM event
			InboundFlightCompleteStoreEvent flightCompleteEvent = new InboundFlightCompleteStoreEvent();
			flightCompleteEvent.setFlightId(documentVerificationFlightModel.getFlightId());
			flightCompleteEvent.setShipmentType("CARGO");
			flightCompleteEvent.setStatus(EventStatus.Type.NEW);
			flightCompleteEvent.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
			flightCompleteEvent.setCreatedOn(LocalDateTime.now());
			flightCompleteProducer.publish(flightCompleteEvent);

			// Raise event for sync customs information
			SingaporeCustomsDataSyncEvent singaporeCustomsDataSyncEvent = new SingaporeCustomsDataSyncEvent();
			singaporeCustomsDataSyncEvent.setFlightId(documentVerificationFlightModel.getFlightId());
			singaporeCustomsDataSyncEvent.setFlightKey(documentVerificationFlightModel.getFlightNumber());
			singaporeCustomsDataSyncEvent.setFlightDate(documentVerificationFlightModel.getFlightDate());
			singaporeCustomsDataSyncEvent.setTenantId(documentVerificationFlightModel.getTenantAirport());
			singaporeCustomsDataSyncEvent.setEventType(CustomsEventTypes.Type.INBOUND_DOCUMENT_VERIFICATION);
			singaporeCustomsDataSyncEvent.setCreatedDate(LocalDateTime.now());
			singaporeCustomsDataSyncEvent.setCreatedBy(documentVerificationFlightModel.getLoggedInUser());

			// Invoke the event
			this.singaporeCustomsDataSyncProducer.publish(singaporeCustomsDataSyncEvent);

			// Raise event for post processing General Shipments
			GeneralShipmentsEvent generalShipmentsEvent = new GeneralShipmentsEvent();
			generalShipmentsEvent.setFlightId(documentVerificationFlightModel.getFlightId());
			generalShipmentsEvent.setFlightKey(documentVerificationFlightModel.getFlightNumber());
			generalShipmentsEvent.setFlightDate(documentVerificationFlightModel.getFlightDate());
			generalShipmentsEvent.setTenantId(documentVerificationFlightModel.getTenantAirport());
			generalShipmentsEvent.setCreatedOn(LocalDateTime.now());
			generalShipmentsEvent.setCreatedBy(documentVerificationFlightModel.getLoggedInUser());

			// Invoke the event
			this.generalShipmentsEventProducer.publish(generalShipmentsEvent);

			// Raise event for post processing EAP shipments
			EAPShipmentsEvent eapShipmentsEvent = new EAPShipmentsEvent();
			eapShipmentsEvent.setFlightId(documentVerificationFlightModel.getFlightId());
			eapShipmentsEvent.setFlightKey(documentVerificationFlightModel.getFlightNumber());
			eapShipmentsEvent.setFlightDate(documentVerificationFlightModel.getFlightDate());
			eapShipmentsEvent.setTenantId(documentVerificationFlightModel.getTenantAirport());
			eapShipmentsEvent.setCreatedOn(LocalDateTime.now());
			eapShipmentsEvent.setCreatedBy(documentVerificationFlightModel.getLoggedInUser());

			// Invoke the event
			this.eapShipmentsEventProducer.publish(eapShipmentsEvent);

			// Raise event for post processing EAW shipments
			EAWShipmentsEvent eawShipmentsPayload = new EAWShipmentsEvent();
			eawShipmentsPayload.setFlightId(documentVerificationFlightModel.getFlightId());
			eawShipmentsPayload.setFlightKey(documentVerificationFlightModel.getFlightNumber());
			eawShipmentsPayload.setFlightDate(documentVerificationFlightModel.getFlightDate());
			eawShipmentsPayload.setTenantId(documentVerificationFlightModel.getTenantAirport());
			eawShipmentsPayload.setCreatedOn(LocalDateTime.now());
			eawShipmentsPayload.setCreatedBy(documentVerificationFlightModel.getLoggedInUser());

			// Invoke the event
			this.eawShipmentsEventProducer.publish(eawShipmentsPayload);

			for (DocumentVerificationShipmentModel shipmentModel : documentVerificationFlightModel
					.getDocumentVerificationShipmentModelList()) {
				if (Objects.nonNull(shipmentModel)) {
					// IVRS Event Call
					ShipmentNotification shipmentNotification = new ShipmentNotification();
					shipmentNotification.setShipmentNumber(shipmentModel.getShipmentNumber());
					shipmentNotification.setShipmentDate(shipmentModel.getShipmentdate());
					ShipmentNotificationDetail notificationDetail = documentVerificationDAO
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
							System.out.println("Document Verification Service - Shipent Notification EM code - "
									+ shipmentNotification.getShipmentNumber());
							resendEmailNotification(notificationDetail,
									documentVerificationFlightModel.getFlightNumber(),
									documentVerificationFlightModel.getFlightDate());
						}
					}
				}
			}
		}

		// Raise event for document shipment complete event
		for (DocumentVerificationShipmentModel notirregularity : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {

			if (!ObjectUtils.isEmpty(notirregularity.getShipmentId())) {
				InboundShipmentDocumentCompleteStoreEvent event = new InboundShipmentDocumentCompleteStoreEvent();
				event.setShipmentId(notirregularity.getShipmentId());
				event.setFlightId(notirregularity.getFlightId());
				if (!ObjectUtils.isEmpty(notirregularity.getManifestPieces())) {
					event.setPieces(notirregularity.getManifestPieces());
					event.setWeight(notirregularity.getManifestWeight());
				} else {
					event.setPieces(notirregularity.getAwbPieces());
					event.setWeight(notirregularity.getAwbWeight());
				}
				event.setStatus(EventStatus.NEW.getStatus());
				event.setCompletedAt(LocalDateTime.now());
				event.setCompletedBy(documentVerificationFlightModel.getCreatedBy());
				event.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
				event.setCreatedOn(LocalDateTime.now());
				event.setLastModifiedBy(documentVerificationFlightModel.getModifiedBy());
				event.setLastModifiedOn(LocalDateTime.now());
				event.setFunction("Inbound Document Complete");
				event.setEventName(EventTypes.Names.INBOUBND_SHIPMENT_DOCUMENT_COMPLETE_EVENT);

				// Publish the event
				producer.publish(event);
			}
		}

		// CDH: Sort shipments in ascending order for printing purpose
		// Set the first 4 digit number from AWB Suffix
		documentVerificationFlightModel.getDocumentVerificationShipmentModelList().forEach(t -> {
           // Check if the ShipmentNumber is empty OR not if no extract first 4 digit number from AWB Suffix
           if (!StringUtils.isEmpty(t.getShipmentNumber())) {
              t.setFirstFourDigitsAfterPrefix(Integer
                    .valueOf(t.getShipmentNumber().substring(3, t.getShipmentNumber().length()-4)));
           }
        });

        // Sort the list
        List<DocumentVerificationShipmentModel> sortedShpInfo = documentVerificationFlightModel.getDocumentVerificationShipmentModelList().stream()
              .sorted(Comparator.comparingInt(DocumentVerificationShipmentModel::getFirstFourDigitsAfterPrefix)).collect(Collectors.toList());

		for (DocumentVerificationShipmentModel record : sortedShpInfo) {
			// Filter only Transhipment shipments
//		
			if (!MultiTenantUtility.isTenantCityOrAirport(record.getOrigin())
					&& !MultiTenantUtility.isTenantCityOrAirport(record.getDestination())
					&& (record.getDocRecieved() == true || record.getCopyAwb() == true)
					&& record.getTransferType() != "TT" && record.getTransferType() != "TTT") {

				if (record.getShipmentId() != null) {
//					int count = documentVerificationDAO.recordExistInCDH(record);
					if (StringUtils.isEmpty(record.getBarcode()) && !StringUtils.isEmpty(documentVerificationFlightModel.getPrinterName())) {
						// Printing AWB Barcode
						AWBPrintRequest request = new AWBPrintRequest();
						request.setAwbNumber(record.getShipmentNumber());
						request.setPrinterName(documentVerificationFlightModel.getPrinterName());
						this.printAwbBarcode(request);
						
						// Insert Data To Cdh_DocumentMaster
						CdhDocumentmaster modeldata = new CdhDocumentmaster();
						modeldata.setCopyno(0);
						modeldata.setCarriercode(record.getCarrierCode());
						modeldata.setDestination(record.getDestination());
						modeldata.setFlightoffpoint(record.getOffPoint());
						modeldata.setShipmentid(record.getShipmentId());
						modeldata.setShipmentNumber(record.getShipmentNumber());
						modeldata.setPrinterForAT(documentVerificationFlightModel.getPrinterForAT());
						shipmentMasService.updateCDHShipmetMasterData(modeldata);
					}
				}
			}
		}

		return documentVerificationFlightModel;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.service.
	 * DocumentVerificationService#updateFlightDelay(com.ngen.cosys.impbd.shipment.
	 * verification.model.DocumentVerificationFlightModel)
	 */
	@Override
	public DocumentVerificationFlightModel updateFlightDelay(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {

		for (DocumentVerificationShipmentModel documentVerificationShipmentModel : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {
			ShipmentRemarksModel remarks = new ShipmentRemarksModel();
			remarks.setShipmentNumber(documentVerificationShipmentModel.getShipmentNumber());
			remarks.setShipmentdate(documentVerificationShipmentModel.getShipmentdate());
			remarks.setFlightId(documentVerificationShipmentModel.getFlightId());
			remarks.setRemarkType(ShipmentRemarksType.FLIGHT_DELAY.getType());
			remarks.setShipmentRemarks("Flight Delayed");
			shipRemarksService.createShipmentRemarks(remarks);
		}
		return documentVerificationFlightModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.service.
	 * DocumentVerificationService#reOpenDocumentComplete(com.ngen.cosys.impbd.
	 * shipment.verification.model.DocumentVerificationFlightModel)
	 */
	@Override
	public void reOpenDocumentComplete(DocumentVerificationFlightModel documentVerificationFlightModel)
			throws CustomException {
		//Re-open the document
		documentVerificationDAO.updateDocumentCompleReopen(documentVerificationFlightModel);
		BreakDownWorkingListModel workListModel =new BreakDownWorkingListModel();
		workListModel.setFlightId(documentVerificationFlightModel.getFlightId());
		//Re-open the flight complete
		int isFlightCompleted = workinglistDao.isFlightCompleted(workListModel );
		if(isFlightCompleted > 0) {
			documentVerificationDAO.reopenFlightComplete(documentVerificationFlightModel);
		}
	}

	@Override
	public DocumentVerificationFlightModel saveDocumentVerification(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		
		//validate IGM for international Airline(AISATS)
		//check feature enabled
		if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.CustomFlightNumberRequiredForDocumentComplete.class)) {
			this.validateFlightIGMInformation(documentVerificationFlightModel);
		}
		
		for (DocumentVerificationShipmentModel documentVerificationShipmentModel : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {

			ShipmentMaster shipmentMaster = new ShipmentMaster();
			shipmentMaster.setShipmentType(ShipmentType.AWB.toString());
			shipmentMaster.setShipmentId(documentVerificationShipmentModel.getShipmentId());
			shipmentMaster.setShipmentdate(documentVerificationShipmentModel.getShipmentdate());
			shipmentMaster.setShipmentNumber(documentVerificationShipmentModel.getShipmentNumber());
			shipmentMaster.setRequestFlightId(documentVerificationShipmentModel.getFlightId());
			shipmentMaster.setOriginalAwb(documentVerificationShipmentModel.getDocRecieved());
			shipmentMaster.setPiece(documentVerificationShipmentModel.getAwbPieces());
			shipmentMaster.setWeight(documentVerificationShipmentModel.getAwbWeight());
			shipmentMaster.setOrigin(documentVerificationShipmentModel.getOrigin());
			shipmentMaster.setDestination(documentVerificationShipmentModel.getDestination());
			// Derive document received date time
			LocalDateTime dateATA = shipmentMasService.deriveDocumentReceivedDateTime(shipmentMaster);

			// Check for data update if any of the flag has been selected OR dg check list
			// info has been captured

			shipmentMaster.setPhotoCopy(documentVerificationShipmentModel.getCopyAwb());
			if (Objects.nonNull(dateATA)) {
				shipmentMaster.setModifiedOn(dateATA);
				if (documentVerificationShipmentModel.getDocRecieved()) {
					shipmentMaster.setDocumentReceivedOn(dateATA);
				}
				if (documentVerificationShipmentModel.getCopyAwb()) {
					shipmentMaster.setPhotoCopyReceivedOn(dateATA);
				}
				if (documentVerificationShipmentModel.getDocPouch()) {
					shipmentMaster.setDocumentPouchReceivedOn(dateATA);
				}
			}

			shipmentMaster.setCarrierCode(documentVerificationShipmentModel.getCarrierCode());
			shipmentMaster.setNatureOfGoodsDescription(documentVerificationShipmentModel.getNatureOfGoods());
			
			//update Booking Pieces with AWB pieces if FFR Pieces not matching FFM pieces	
			if (!MultiTenantUtility.isTenantCityOrAirport(documentVerificationShipmentModel.getDestination())) {
				// booking update 
		         CommonBooking booking= new CommonBooking();
		         booking.setShipmentNumber(shipmentMaster.getShipmentNumber());
		         booking.setShipmentDate(shipmentMaster.getShipmentdate());
		         boolean check =commonBookingService.checkDocInOrAcceptanceFinalize(booking);
		         if(!check) {
		        	 // update booking data
		        	 booking.setOrigin(shipmentMaster.getOrigin());
		        	 booking.setDestination(shipmentMaster.getDestination());
		        	 booking.setPieces(shipmentMaster.getPiece());
		        	 booking.setWeight(shipmentMaster.getWeight());
		        	 booking.setNog(shipmentMaster.getNatureOfGoodsDescription());
		        	 //booking.setWeightUnitCode(shipmentMaster.getWeightUnitCode());
		        	 booking.setCreatedBy(documentVerificationFlightModel.getCreatedBy());
		        	 commonBookingService.updateBookingMethod(booking);
		         }
			}

			// If shipment record has been created then conitnue creating the shipment
			// verification and Document Handling
			if (documentVerificationShipmentModel.getCopyAwb() || documentVerificationShipmentModel.getDocRecieved()) {

				this.shipmentMasService.createShipment(shipmentMaster);
				
				// Create shipment verification for shipment and flight
				ShipmentVerificationModel shipmentVerModel = new ShipmentVerificationModel();
				shipmentVerModel.setFlightNumber(documentVerificationFlightModel.getFlightNumber());
				shipmentVerModel.setFlightDate(documentVerificationFlightModel.getFlightDate());
				shipmentVerModel.setFlightId(documentVerificationShipmentModel.getFlightId());
				shipmentVerModel.setShipmentId(shipmentMaster.getShipmentId());
				shipmentVerModel.setDocumentPouchReceivedFlag(documentVerificationShipmentModel.getDocPouch());
				shipmentVerModel.setDocumentReceivedFlag(documentVerificationShipmentModel.getDocRecieved());
				shipmentVerModel.setPhotoCopyAwbFlag(documentVerificationShipmentModel.getCopyAwb());
				shipmentVerModel.setCheckListRequired(documentVerificationShipmentModel.getCheckListRequired());
				shipmentVerModel.setPhotoCopyReceivedOn(dateATA);
				shipmentVerModel.setShipmentNumber(documentVerificationShipmentModel.getShipmentNumber());
				shipmentVeriService.createShipmentVerification(shipmentVerModel);

				// Filter only Transhipment shipments
				if (documentVerificationShipmentModel.getSelectCheck()
						&& !MultiTenantUtility.isTenantCityOrAirport(documentVerificationShipmentModel.getOrigin())
						&& !MultiTenantUtility.isTenantCityOrAirport(documentVerificationShipmentModel.getDestination())
						&& (documentVerificationShipmentModel.getDocRecieved() == true
								|| documentVerificationShipmentModel.getCopyAwb() == true)) {

					if (documentVerificationShipmentModel.getShipmentId() != null) {
						// Printing AWB Barcode
						int count = documentVerificationDAO.recordExistInCDH(documentVerificationShipmentModel);
						if (count == 0) {
							AWBPrintRequest request = new AWBPrintRequest();
							request.setAwbNumber(documentVerificationShipmentModel.getShipmentNumber());
							request.setPrinterName(documentVerificationFlightModel.getPrinterName());
							this.printAwbBarcode(request);
						}
						CdhDocumentmaster modeldata = new CdhDocumentmaster();
						modeldata.setCopyno(0);
						modeldata.setCarriercode(documentVerificationShipmentModel.getCarrierCode());
						modeldata.setDestination(documentVerificationShipmentModel.getDestination());
						modeldata.setFlightoffpoint(documentVerificationShipmentModel.getOffPoint());
						modeldata.setShipmentid(documentVerificationShipmentModel.getShipmentId());

						// Insert/Update Data To Cdh_DocumentMaster
						shipmentMasService.updateCDHShipmetMasterData(modeldata);
					}
				}
			} else {
				this.documentVerificationDAO.deleteDataFromShipmentVerification(documentVerificationShipmentModel);
			}
			
	    }
		return documentVerificationFlightModel;
	}

	private void validateFlightIGMInformation(DocumentVerificationFlightModel documentVerificationFlightModel)  throws CustomException  {
		String domesticOrInternational =this.documentVerificationDAO.getFlightType(documentVerificationFlightModel);	                           
		// derive Domestic && International
		if ("INT".equalsIgnoreCase(domesticOrInternational) && StringUtils.isEmpty(documentVerificationFlightModel.getCustomsFlightNumber())) {   
		throw new CustomException("import.warn115",null, "E");
		}
	}
	

	@Override
	public DocumentVerificationFlightModel documentsOnHold(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		return documentVerificationDAO.onHoldShipments(documentVerificationFlightModel);
	}

	@Override
	public DocumentVerificationFlightModel updateShipmentRemarks(
			DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException {
		for (DocumentVerificationShipmentModel t : documentVerificationFlightModel
				.getDocumentVerificationShipmentModelList()) {
			ShipmentRemarksModel r = new ShipmentRemarksModel();
			r.setShipmentdate(t.getShipmentdate());
			r.setShipmentNumber(t.getShipmentNumber());
			r.setShipmentId(t.getShipmentId());
			r.setRemarkType(t.getRemarkType());
			r.setShipmentRemarks(t.getRemarks());
			r.setShipmentType(t.getShipmentType());
			this.shipRemarksService.createShipmentRemarks(r);
		}
		return documentVerificationFlightModel;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ShipperDeclaration create(ShipperDeclaration shippersDeclarationDetails) throws CustomException {
		EliElmDGDModel eliElmDGDModel = new EliElmDGDModel();
		eliElmDGDModel.setShipmentNumber(shippersDeclarationDetails.getShipmentNumber());
		EliElmDGDModelList bookingDoneForShipment = documentVerificationDAO.isBookingDoneForShipment(eliElmDGDModel);
		if (ObjectUtils.isEmpty(bookingDoneForShipment)) {
			throw new CustomException("impbd.booking.not.done", "Booking not done", "E");
		}
		return documentVerificationDAO.saveDgDeclarationsDetails(shippersDeclarationDetails);
	}

	@Override
	public List<ShipperDeclaration> find(SearchDGDeclations search) throws CustomException {
		return documentVerificationDAO.getDgdDetails(search);
	}

	@Override
	public ShipperDeclaration deleteDgdDetails(ShipperDeclaration shipperDeclaration) throws CustomException {
		List<UNIDOverpackDetails> unidDetails = new ArrayList<>();
		for (ShipperDeclarationDetail sdd : shipperDeclaration.getDeclarationDetails()) {
			sdd.getOverPackDetails().forEach(unidObj -> {
				unidObj.setExpDgShipperDeclarationId(sdd.getExpDgShipperDeclarationId());
				unidObj.setDgdReferenceNo(sdd.getDgdReferenceNo());
			});
			unidDetails = sdd.getOverPackDetails().stream()
					.filter(b -> Action.DELETE.toString().equalsIgnoreCase(b.getFlagCRUD()))
					.collect(Collectors.toList());
		}

		documentVerificationDAO.deleteUnidOverPackDetails(unidDetails);

		shipperDeclaration.getDeclarationDetails().forEach(b -> {
			b.setExpDgShipperDeclarationId(shipperDeclaration.getExpDgShipperDeclarationId());
			b.setDgdReferenceNo(shipperDeclaration.getDgdReferenceNo());
		});
		List<ShipperDeclarationDetail> dgdDetails = shipperDeclaration.getDeclarationDetails().stream()
				.filter(b -> Action.DELETE.toString().equalsIgnoreCase(b.getFlagCRUD())).collect(Collectors.toList());
		documentVerificationDAO.deleteShipperDeclarationDetails(dgdDetails);

		return shipperDeclaration;
	}

	@Override
	public List<DgRegulations> getDgdRegulationDetails(SearchRegulationDetails regId) throws CustomException {
		SearchRegulationDetails searchRegId;
		searchRegId = regId;
		List<DgRegulations> fetchList = documentVerificationDAO.getRegDetails(searchRegId);
		if (!CollectionUtils.isEmpty(fetchList)) {
			fetchList.forEach(dg -> {
				if (!CollectionUtils.isEmpty(dg.getDgDetails())) {
					dg.getDgDetails().forEach(fbd -> {
						if (fbd.getFbd().equalsIgnoreCase("0")) {
							fbd.setFbd("N");
						} else {
							fbd.setFbd("Y");
						}
					});
				}
			});
		}
		return fetchList;
	}

	@Override
	public Integer getOverPackSeqNo(SearchDGDeclations search) throws CustomException {
		return documentVerificationDAO.getSeqNO(search);
	}

	@Override
	public EliElmDGDModel createOrSaveEliElmData(EliElmDGDModel elmDGDModel) throws CustomException {
		return documentVerificationDAO.saveOrDelEliElm(elmDGDModel);
	}

	@Override
	public EliElmDGDModel deleteEliElmData(EliElmDGDModel elmDGDModel) throws CustomException {
		return documentVerificationDAO.deleteEliElmDetails(elmDGDModel);
	}

	@Override
	public EliElmDGDModel getEliElmData(EliElmDGDModel elmDGDModel) throws CustomException {
		return documentVerificationDAO.getEliElmDetails(elmDGDModel);
	}

	@Override
	public EliElmDGDModelList getEliElmRemark(EliElmDGDModelList elmDGDModel) throws CustomException {
		return documentVerificationDAO.getEliElmRemark(elmDGDModel);
	}

	public void printAwbBarcode(AWBPrintRequest request) {
		ReportRequest report = new ReportRequest();
		report.setRequestType(ReportRequestType.PRINT);
		report.setPrinterType(PrinterType.AWB_BARCODE);
		report.setQueueName(request.getPrinterName());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("awbNumBarCode", request.getAwbNumber());
		parameters.put("awbNumTextCode", request.getAwbNumber());
		report.setParameters(parameters);
		if (null != report.getQueueName()) {
			printerService.printReport(report);
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

}