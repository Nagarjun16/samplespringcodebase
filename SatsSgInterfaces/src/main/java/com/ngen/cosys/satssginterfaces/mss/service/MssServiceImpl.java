
package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.mss.dao.InboundBreakdownAirmailInterfaceDAO;
import com.ngen.cosys.satssginterfaces.mss.dao.MssDAO;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpMailSearch;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpULD;
import com.ngen.cosys.satssginterfaces.mss.model.CommonLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentHouseAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentInventoryAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentShcAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.LoadedShipment;
import com.ngen.cosys.satssginterfaces.mss.model.MSSMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptance;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceRequest;
import com.ngen.cosys.satssginterfaces.mss.model.MailbagAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.MailbagAirmailInterfaceUtil;
import com.ngen.cosys.satssginterfaces.mss.model.MssMessageParentModel;
import com.ngen.cosys.satssginterfaces.mss.model.PreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.RequestPreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseErrorMessagesHeaderMss;
import com.ngen.cosys.satssginterfaces.mss.model.ResponseMssMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.Segment;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;
import com.ngen.cosys.satssginterfaces.mss.model.UldShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentInventory;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class MssServiceImpl implements MssService {
	@Autowired
	private MssDAO dao;

	@Autowired
	private ShipmentProcessorService shipmentProcessorService;

	@Autowired
	ShipmentMasterAirmailInterfaceService shipmentMasterService;

	@Autowired
	ShipmentVerificationAirlineInterfaceService shipmentVerificationService;

	@Autowired
	private InboundBreakdownAirmailInterfaceDAO inboundBreakDownDao;

	@Autowired
	private ShipmentInventoryAirmailInterfaceService shipmentInventoryService;

	@Autowired
	private MailExportAcceptanceService accpSvcObj;

	@Autowired
	private UldInfoService uldCommonService;

	@Autowired
	AirmailStatusEventProducer produce;

	@Autowired
	private MoveableLocationTypesValidator moveableLocationTypesValidator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssginterfaces.mss.service.MssService#
	 * preannouncementUldMessage(com.ngen.cosys.satssginterfaces.mss.model.
	 * RequestPreannouncementUldMessagesModel)
	 */
	@Override
	public MssMessageParentModel<List<PreannouncementUldMessagesModel>> preannouncementUldMessage(
			RequestPreannouncementUldMessagesModel model) throws CustomException {
		Random das = new Random();
		MssMessageParentModel<List<PreannouncementUldMessagesModel>> parentData = new MssMessageParentModel<>();
		List<PreannouncementUldMessagesModel> list = dao.preannouncementUldMessage(model);
		for (PreannouncementUldMessagesModel preannounceData : list) {
			preannounceData.setTransactionSequenceNumber(String.valueOf(das.nextInt(99999999)));
		}
		List<PreannouncementUldMessagesModel> addList = list.stream()
				.filter(obj -> (null == obj.getModifiedOn() || obj.getCreatedOn().equals(obj.getModifiedOn())))
				.collect(Collectors.toList());
		List<PreannouncementUldMessagesModel> updList = list.stream()
				.filter(obj -> null != obj.getModifiedOn() && obj.getModifiedOn().isAfter(obj.getCreatedOn()))
				.collect(Collectors.toList());
		list.clear();
		list.addAll(setValues(addList, "ADD"));
		list.addAll(setValues(updList, "UPD"));
		parentData.setMessageType("TGFMCPRE");
		parentData.setMessageRefNumber(BigInteger.valueOf(das.nextInt(9999999)));
		parentData.setMessageSendSyatem("RFDT");
		parentData.setMessageReceipentSytem("MSS");
		parentData.setData(list);
		parentData.setNumberDataElement(list.size());
		return parentData;
	}

	private List<PreannouncementUldMessagesModel> setValues(List<PreannouncementUldMessagesModel> list,
			String operation) {
		for (PreannouncementUldMessagesModel obj : list) {
			obj.setAction(operation);
		}
		return list;
	}

	@Override
	public List<ResponseErrorMessagesHeaderMss> responseHeader() throws CustomException {
		return dao.responseHeader();
	}

	@Override
	public Integer updateErrorResponseMessageinOutgoingMessage(BigInteger id) throws CustomException {
		return dao.updateErrorResponseMessageinOutgoingMessage(id);
	}

	@Override
	public RequestPreannouncementUldMessagesModel selectFlightKey(BigInteger flightId) throws CustomException {
		return dao.selectFlightKey(flightId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public MSSMailBagMovement messageTypeForTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException {
		List<String> allStatus = new ArrayList<>();
		requestModel.setCreatedBy("MSS");
		requestModel.setShipmentLocation(requestModel.getUldKey());
		requestModel.setWarehouseLocation(requestModel.getContainerlocation());
		
		//ResponseMssMailBagMovement result = dao.messageTypeTGFMMBSI(requestModel);
		requestModel.setUldKey(requestModel.getUldType().concat(requestModel.getUldNumber().concat(requestModel.getUldCarrier())));
		MailbagAirmailInterfaceModel mailbagModel = MailbagAirmailInterfaceUtil.getMailDetailsSplit(requestModel.getMailBagId());

		requestModel.setShipmentNumber(mailbagModel.getDispatchSeries());
		requestModel.setDestinationCity(mailbagModel.getDestinationAirport());
		requestModel.setDestination(mailbagModel.getDestinationAirport());
		requestModel.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
				.concat(mailbagModel.getDestinationAirport().concat(mailbagModel.getDestinationQualifier())));
		requestModel.setOriginCity(mailbagModel.getOriginCountry());
		requestModel.setOrigin(mailbagModel.getOriginAirport());
		requestModel.setOriginOfficeExchange(mailbagModel.getOriginCountry()
				.concat(mailbagModel.getOriginAirport().concat(mailbagModel.getOriginQualifier())));
		requestModel.setMailCategory(mailbagModel.getCategory());
		requestModel.setMailSubType(mailbagModel.getSubCategory());
		requestModel.setDispatchNo(mailbagModel.getDispatchNumber());
		requestModel.setReceptacleNumber(mailbagModel.getReceptableNumber());
		requestModel.setLastBag(mailbagModel.isLastBagIndicator());
		requestModel.setRegistered(mailbagModel.getRegisteredInsuredFlag().equalsIgnoreCase("1") ? true : false);
		requestModel.setPieces(BigInteger.ONE);
		requestModel.setWeight(mailbagModel.getWeight());
		requestModel.setDispatchYear(new BigInteger(mailbagModel.getYear()));
		
		// Set Shipment date for Dispatch Series
		LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(requestModel.getShipmentNumber());
		requestModel.setShipmentDate(shipmentDate);
		
		// Check whether Mailbag is accepted
		Integer mailBagAccepted = dao.checkIsMailBagAccepted(requestModel);
		if(ObjectUtils.isEmpty(mailBagAccepted)) {
			requestModel.addError("sats.mail.bag.not.acc.yet", requestModel.getMailBagId(), ErrorType.ERROR);
			requestModel.setValidMailBags(false);
		}
		
		// Check whether Mailbag is loaded
		Integer mailBagLoaded = dao.checkIsMailBagLoaded(requestModel);
		if(!ObjectUtils.isEmpty(mailBagLoaded)) {
			requestModel.addError("ALREADYLOADED", requestModel.getMailBagId(), ErrorType.ERROR);
			requestModel.setValidMailBags(false);
		}

		// Check for valid ULD
		MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
		movableLocationModel.setKey(requestModel.getUldKey());
		movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
		if(!ObjectUtils.isEmpty(movableLocationModel.getMessageList())) {
			requestModel.addError("ULDMODEL02", requestModel.getMailBagId(), ErrorType.ERROR);
			requestModel.setValidMailBags(false);
		}
		
		// Validate ULD is assign to flight and Flight Events
		// Get ULD, Segment and Flight Event details
		MSSMailBagMovement fltEvents = dao.getULDAndFLightEventsInfo(requestModel);
		if(ObjectUtils.isEmpty(fltEvents)) {
			requestModel.addError("uld.not.assigned.to.flight", requestModel.getMailBagId(), ErrorType.ERROR);
			requestModel.setValidMailBags(false);
		} else {
			if(fltEvents.getDlsCompleted()) {
				requestModel.addError("dls.is.already.finalized", requestModel.getMailBagId(), ErrorType.ERROR);
				requestModel.setValidMailBags(false);
			}
			if(fltEvents.getMailManifestCompleted()) {
				requestModel.addError("manifest.is.already.completed", requestModel.getMailBagId(), ErrorType.ERROR);
				requestModel.setValidMailBags(false);
			}
			
			// Check ULD with ContentCode 'M'
			if(!fltEvents.getContentCode().equalsIgnoreCase("M")) {
				requestModel.addError("maillyinglist.container.usedbycargo", requestModel.getMailBagId(), ErrorType.ERROR);
				requestModel.setValidMailBags(false);
			}
			requestModel.setManifestedFltId(fltEvents.getManifestedFltId());
			requestModel.setManifestedFlightKey(fltEvents.getManifestedFlightKey());
			requestModel.setManifestedFlightDate(fltEvents.getManifestedFlightDate());
		}
		
		if (requestModel.getValidMailBags()) {
			if (requestModel.getAction().equalsIgnoreCase("ADD")) {
				requestModel.setSegmentIdForAssignedTrolley(fltEvents.getManifestedFltSegmentId());
				allStatus.add("Load");
				requestModel.setMethodFor(allStatus);
			} 
			else if (requestModel.getAction().equalsIgnoreCase("UPD")) {
				MSSMailBagMovement object = dao.getULDAndFLightManifestInfo(requestModel);
				if(ObjectUtils.isEmpty(object)) {
					requestModel.addError("mail.not.loaded.yet", requestModel.getMailBagId(), ErrorType.ERROR);
					requestModel.setValidMailBags(false);
				} else {
					if(StringUtils.isEmpty(requestModel.getWarehouseLocation())) {
						requestModel.addError("sats.location.not.null", requestModel.getMailBagId(), ErrorType.ERROR);
						requestModel.setValidMailBags(false);
					}
					requestModel.setSegmentIdForAssignedTrolley(fltEvents.getManifestedFltSegmentId());
					requestModel.setFlightBoardPoint(object.getFlightBoardPoint());
					requestModel.setFlightOffPoint(object.getFlightOffPoint());
					requestModel.setAssUldTrolleyId(object.getAssUldTrolleyId());
					requestModel.setLoadedShipmentInfoId(object.getLoadedShipmentInfoId());
					requestModel.setShipmentId(object.getShipmentId());
					requestModel.setLoadedHousePieces(object.getLoadedHousePieces());
					requestModel.setLoadedHouseWeight(object.getLoadedHouseWeight());
					requestModel.setPieces(object.getPieces());
					requestModel.setWeight(object.getWeight());
					requestModel.setManifestShipmentHouseInfoId(object.getManifestShipmentHouseInfoId());
				}
				
				if (requestModel.getValidMailBags()) {
					allStatus.add("Unload");
					requestModel.setMethodFor(allStatus);
				}
			}
		}

		if (!requestModel.getMessageList().isEmpty()) {
			throw new CustomException();
		}
		return requestModel;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public MSSMailBagMovement messageTypeTGFMMBSI(MSSMailBagMovement requestModel) throws CustomException {
		List<String> allStatus = new ArrayList<>();
		requestModel.setCreatedBy(requestModel.getUser());
		ResponseMssMailBagMovement result = dao.messageTypeTGFMMBSI(requestModel);
		requestModel.setUldKey(
				requestModel.getUldType().concat(requestModel.getUldNumber().concat(requestModel.getUldCarrier())));
		MailbagAirmailInterfaceModel mailbagModel = MailbagAirmailInterfaceUtil
				.getMailDetailsSplit(requestModel.getMailBagId());

		requestModel.setShipmentNumber(mailbagModel.getDispatchSeries());
		requestModel.setDestinationCity(mailbagModel.getDestinationAirport());
		requestModel.setDestination(mailbagModel.getDestinationAirport());
		requestModel.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
				.concat(mailbagModel.getDestinationAirport().concat(mailbagModel.getDestinationQualifier())));
		requestModel.setOriginCity(mailbagModel.getOriginCountry());
		requestModel.setOrigin(mailbagModel.getOriginAirport());
		requestModel.setOriginOfficeExchange(mailbagModel.getOriginCountry()
				.concat(mailbagModel.getOriginAirport().concat(mailbagModel.getOriginQualifier())));
		requestModel.setMailCategory(mailbagModel.getCategory());
		requestModel.setMailSubType(mailbagModel.getSubCategory());
		requestModel.setDispatchNo(mailbagModel.getDispatchNumber());
		requestModel.setReceptacleNumber(mailbagModel.getReceptableNumber());
		requestModel.setLastBag(mailbagModel.isLastBagIndicator());
		requestModel.setRegistered(mailbagModel.getRegisteredInsuredFlag().equalsIgnoreCase("1") ? true : false);
		requestModel.setPieces(BigInteger.ONE);
		requestModel.setWeight(mailbagModel.getWeight());
		requestModel.setShipmentLocation(requestModel.getUldKey());
		requestModel.setWarehouseLocation(requestModel.getContainerlocation());
		requestModel.setDispatchYear(new BigInteger(mailbagModel.getYear()));

		// Set Shipment date for Dispatch Series
		LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(requestModel.getShipmentNumber());
		requestModel.setShipmentDate(shipmentDate);

		BigInteger segmentIdForAssignedTrolley = null;

		requestModel.setIncomingFlightKey(
				requestModel.getIncomimngFlightCarrier() + requestModel.getIncomimngFlightNumber());
		if (Optional.ofNullable(requestModel.getIncomingFlightKey()).isPresent()
				&& Optional.ofNullable(requestModel.getIncomimngFlightDate()).isPresent()) {
			requestModel.setIncomingFlightId(dao.getIncommingFlightId(requestModel));
			if (!StringUtils.isEmpty(requestModel.getIncomingFlightKey())
					&& !Optional.ofNullable(requestModel.getIncomingFlightId()).isPresent()) {
				requestModel.addError("invalid.incoming.flight", "", ErrorType.ERROR,new String[] {requestModel.getIncomingFlightKey(),requestModel.getIncomingFlightDate().toString()});
				requestModel.setValidMailBags(false);
			}
		}

		requestModel.setManifestedFlightKey(
				requestModel.getOutgoingFlightCarrier() + requestModel.getOutgoingFlightNumber());
		if (Optional.ofNullable(requestModel.getManifestedFlightKey()).isPresent()
				&& Optional.ofNullable(requestModel.getOutgoingFlightDate()).isPresent()) {
			requestModel.setManifestedFltId(dao.getOutgoingFlightId(requestModel));
			if (!StringUtils.isEmpty(requestModel.getManifestedFlightKey())
					&& !Optional.ofNullable(requestModel.getManifestedFltId()).isPresent()) {
				requestModel.addError("invalid.outgoing.flight", "", ErrorType.ERROR,new String[] {requestModel.getManifestedFlightKey(),requestModel.getManifestedFlightDate().toString()});
				requestModel.setValidMailBags(false);
			}
			/*
			 * Fetch segment-id based on ULD-Trolley & Flight
			 */
			segmentIdForAssignedTrolley = dao.getFLightSegmentIdForAssignedTrolley(requestModel);
		}

		if (Optional.ofNullable(requestModel.getIncomingFlightId()).isPresent()
				&& (!Optional.ofNullable(result).isPresent()
						|| !requestModel.getIncomingFlightId().equals(result.getIncomingFlightId()))) {
			if (MultiTenantUtility.isTenantAirport(requestModel.getOriginCity())) {
				requestModel.setValidMailBags(false);
				requestModel.addError("invalid.origin.of.mailbag", "", ErrorType.ERROR, new String[]{requestModel.getMailBagId()});
			}
			if (requestModel.getValidMailBags()) {
				allStatus.add("BreakDown");
				requestModel.setMethodFor(allStatus);
				// breakDown(requestModel);

			}

		}

		if (Optional.ofNullable(result).isPresent()
				&& (!requestModel.getUldKey().toString().equalsIgnoreCase(result.getShipmentLocation())
						|| !requestModel.getContainerlocation().equalsIgnoreCase(result.getWarehouseLocation()))) {
			// && result.getIncomingFlightId() != null) {
			if (requestModel.getValidMailBags() && requestModel.getAction().equalsIgnoreCase("UPD")) {
				allStatus.add("UpdateLocation");
				requestModel.setMethodFor(allStatus);
				// updateStoreLocationOfShipment(requestModel, result);
				// produceAirmailStatusEvents(result, requestModel);
			}

		}

		if (!StringUtils.isEmpty(requestModel.getOutgoingFlightCarrier())) {

			/*
			 * Acceptance details is not present & MSS provides Outgoing Carrier Accept
			 * mailbag
			 */
			if (MultiTenantUtility.isTenantAirport(requestModel.getDestination())) {
				requestModel.addError("invalid.des.of.mailbag", requestModel.getMailBagId(), ErrorType.ERROR);
				requestModel.setValidMailBags(false);
			}
			
			BigInteger customerId = dao.getCustIdForAgent(requestModel.getAgent());
			
			if(ObjectUtils.isEmpty(customerId)) {
				requestModel.addError("invalid.des.of.mailbag", requestModel.getMailBagId(), ErrorType.ERROR);
				requestModel.setValidMailBags(false);
			}
			
			if (requestModel.getValidMailBags()) {
				if (!Optional.ofNullable(result).isPresent() && requestModel.getAction().equalsIgnoreCase("ADD")) {
					allStatus.add("Acceptance");
					requestModel.setMethodFor(allStatus);
					// acceptmailbag(requestModel);
					// produceAirmailStatusEventAfterAcceptance(requestModel);
				} else {
					if (!requestModel.getOutgoingFlightCarrier().equalsIgnoreCase(result.getOutgoingFlightCarrier())) {
						requestModel.setShipmentId(result.getShipmentId());
						allStatus.add("UpdateLyingList");
						requestModel.setMethodFor(allStatus);
						// Updating the lying list with the new carrier
						// updateLyingList(requestModel);
					}
				}
			}

		}

		// set Carrier code
		if (!StringUtils.isEmpty(requestModel.getOutgoingFlightCarrier())) {
			requestModel.setCarrier(requestModel.getOutgoingFlightCarrier());
		} else if (!StringUtils.isEmpty(requestModel.getIncomimngFlightCarrier())) {
			requestModel.setCarrier(requestModel.getIncomimngFlightCarrier());
		} else {
			requestModel.setCarrier(null);
		}

		// set flight num
		if (null != requestModel.getManifestedFltId()) {
			requestModel.setFlightId(requestModel.getManifestedFltId());
		} else if (null != requestModel.getIncomingFlightId()) {
			requestModel.setFlightId(requestModel.getIncomingFlightId());
		} else {
			requestModel.setFlightId(null);
		}
		// booking for manifest flight
		if (null != requestModel.getManifestedFltId()) {
			allStatus.add("BookingMailBags");
			requestModel.setMethodFor(allStatus);
			// bookShipmentInfo(requestModel);
		}
		// insert xray data - pending status
		if (!StringUtils.isEmpty(requestModel.getXRayStatus())) {
			allStatus.add("Xray");
			requestModel.setMethodFor(allStatus);
			// insertXRAYData(requestModel, result);
		}

		if (!requestModel.getMessageList().isEmpty()) {
			throw new CustomException();
		}

		requestModel.setResponse(result);

		return requestModel;

	}

	@Override
	public void bookShipmentInfo(MSSMailBagMovement requestModel) throws CustomException {
		dao.bookShipmentInfo(requestModel);

	}

	@Override
	public void insertXRAYData(MSSMailBagMovement requestModel, ResponseMssMailBagMovement result)
			throws CustomException {
		if (result != null) {
			requestModel.setShipmentId(result.getShipmentId());
		} else {
			if (!Optional.ofNullable(requestModel.getShipmentId()).isPresent()) {
				requestModel.setShipmentId(dao.getShipmentId(requestModel));
			}
		}
		dao.insertXrayData(requestModel);

	}

	@Override
	public void updateStoreLocationOfShipment(MSSMailBagMovement requestModel, ResponseMssMailBagMovement result)
			throws CustomException {
		requestModel.setShipmentId(result.getShipmentId());
		requestModel.setShipmentInventoryId(result.getShipmentInventoryId());
		requestModel.setShipmentHouseId(result.getShipmentHouseId());
		requestModel.setShipmentInventoryHouseId(result.getShipmentInventoryHouseId());
		dao.updateStoreLocationOfShipment(requestModel);
	}

	@Override
	public void produceAirmailStatusEventAfterLoad(MSSMailBagMovement requestModel) {
		AirmailStatusEvent event = new AirmailStatusEvent();
		event.setSourceTriggerType("loadMailBagToContainer");
		event.setFlightId(requestModel.getManifestedFltId());
		event.setMailBag(requestModel.getMailBagId());
		event.setNextDestination(requestModel.getNextDestination());
		event.setCarrierCode(requestModel.getOutgoingFlightCarrier());
		event.setShipmentId(requestModel.getShipmentId());
		event.setManifestedUldKey(requestModel.getShipmentLocation());
		event.setStoreLocation(requestModel.getShipmentLocation());
		event.setCreatedBy(requestModel.getUser());
		if (requestModel.getCreatedOn() == null) {
			requestModel.setCreatedOn(LocalDateTime.now());
		} else {
			event.setCreatedOn(requestModel.getCreatedOn());
		}
		if (StringUtils.isEmpty(event.getTenantId())) {
			event.setTenantId(requestModel.getTenantAirport());
		}
		event.setStatus("CREATED");
		// produce.publish(event);
	}

	@Override
	public void produceAirmailStatusEventAfterAcceptance(MSSMailBagMovement requestModel) throws CustomException {
		AirmailStatusEvent event = new AirmailStatusEvent();
		event.setSourceTriggerType("EXPORTACCEPTANCE");
		event.setStatus("CREATED");
		event.setShipmentNumber(requestModel.getShipmentNumber());
		event.setCarrierCode(requestModel.getOutgoingFlightCarrier());
		// event.setTransferCarrierTo(obj.getOutgoingCarrier());
		event.setAgentId(requestModel.getAgentCode());
		event.setMailBag(requestModel.getMailBagId());
		event.setNextDestination(requestModel.getNextDestination());
		event.setStoreLocation(requestModel.getUldKey());
		event.setShipmentId(dao.getShipmentIdForMBMovement(requestModel));
		event.setStoreLocationType(dao.getStoreLocationType(requestModel.getShipmentLocation()));
		event.setTenantId(requestModel.getTenantAirport());
		if (requestModel.getCreatedOn() == null) {
			event.setCreatedOn(LocalDateTime.now());
		} else {
			event.setCreatedOn(requestModel.getCreatedOn());
		}

		event.setCreatedBy(requestModel.getUser());
		// produce.publish(event);

	}

	/**
	 * Will produce the CAMS events for RSN,EXP,ACP. Parameter
	 * MailBagInfo,StoreLocationInfo,FlightInfo
	 */
	@Override
	public void produceAirmailStatusEvents(ResponseMssMailBagMovement result, MSSMailBagMovement requestModel) {
		AirmailStatusEvent event = new AirmailStatusEvent();
		event.setSourceTriggerType("MAILBAGOVERVIEWUPDATELOCATION");
		event.setCarrierCode(result.getCarrier());
		if (null != result.getIncomingFlightId()) {
			event.setFlightId(result.getIncomingFlightId());
			event.setImportExportIndicator("I");
		}
		if (null != result.getManifestedFltId()) {
			event.setFlightId(result.getManifestedFltId());
			event.setImportExportIndicator("E");
		}
		if (MultiTenantUtility.isTranshipment(result.getOrigin(), result.getDestination())) {
			event.setImportExportIndicator("T");
		}
		event.setShipmentId(result.getShipmentId());
		event.setShipmentNumber(result.getShipmentNumber());
		event.setPieces(result.getPieces());
		event.setWeight(result.getWeight());
		event.setMailBag(result.getMailBagId());
		if (!StringUtils.isEmpty(requestModel.getShipmentLocation())) {
			event.setStoreLocation(requestModel.getShipmentLocation());
			if (event.getStoreLocation().length() >= 9 && event.getStoreLocation().length() <= 12) {
				event.setStoreLocationType("ULD");
			}
			if (event.getStoreLocation().length() <= 7) {
				event.setStoreLocationType("BULK");
			}
		}
		if (!StringUtils.isEmpty(result.getManifestULD())) {
			event.setManifestedUldKey(result.getManifestULD());
			if (event.getManifestedUldKey().length() >= 9 && event.getManifestedUldKey().length() <= 12) {
				event.setManifestedUldType("ULD");
			}
			if (event.getManifestedUldKey().length() <= 7) {
				event.setManifestedUldType("BULK");
			}
		}

		if (!StringUtils.isEmpty(result.getShipmentLocation())) {
			event.setPreviousStoreLocation(result.getShipmentLocation());
			if (event.getPreviousStoreLocation().length() >= 9 && event.getPreviousStoreLocation().length() <= 12) {
				event.setPreviousStoreLocationType("ULD");
			}
			if (event.getPreviousStoreLocation().length() <= 7) {
				event.setStoreLocationType("BULK");
			}
		}

		event.setTenantId(result.getTenantAirport());
		event.setCreatedBy(requestModel.getUser());
		event.setCreatedOn(LocalDateTime.now());
		event.setStatus("CREATED");
		// produce.publish(event);

	}

	@Override
	public void acceptmailbag(MSSMailBagMovement requestModel) throws CustomException {
		MailExportAcceptanceRequest acpRequest = new MailExportAcceptanceRequest();

		List<MailExportAcceptance> mailExportAcceptance = new ArrayList<>();
		acpRequest.setCarrierCode(requestModel.getOutgoingFlightCarrier());
		acpRequest.setAgentCode(requestModel.getAgent());
		acpRequest.setShipmentNumber(requestModel.getShipmentNumber());
		acpRequest.setUldNumber(requestModel.getUldKey());
		if (StringUtils.isEmpty(requestModel.getContainerlocation())) {
			acpRequest.setWarehouseLocation("TRUCKDOCK");
		} else {
			acpRequest.setWarehouseLocation(requestModel.getContainerlocation());
		}

		acpRequest.setCustomerId(dao.getCustIdForAgent(requestModel.getAgent()));
		acpRequest.setOriginOfficeExchange(requestModel.getOriginOfficeExchange());
		acpRequest.setDestinationOfficeExchange(requestModel.getDestinationOfficeExchange());
		acpRequest.setMailBagMailCategory(requestModel.getMailCategory());
		acpRequest.setMailBagMailSubcategory(requestModel.getMailSubType());
		acpRequest.setDnNumber(requestModel.getDispatchNo());
		acpRequest.setMailBagRegisteredIndicator(requestModel.getRegistered() ? 1 : 0);
		acpRequest.setUser(requestModel.getUser());
		MailExportAcceptance accpObj = new MailExportAcceptance();

		accpObj.setMailBagNumber(requestModel.getMailBagId());
		accpObj.setShipmentNumber(requestModel.getShipmentNumber());
		accpObj.setShipmentDate(requestModel.getShipmentDate());

		accpObj.setAgentCode(requestModel.getAgent());
		accpObj.setCarrierCode(requestModel.getOutgoingFlightCarrier());
		accpObj.setDestinationCity(requestModel.getDestinationCity());
		accpObj.setDestinationCountry(requestModel.getDestinationCountry());
		accpObj.setDestinationOfficeExchange(requestModel.getDestinationOfficeExchange());
		accpObj.setOriginOfficeExchange(requestModel.getOriginOfficeExchange());
		accpObj.setMailBagMailCategory(requestModel.getMailCategory());
		accpObj.setMailBagMailSubcategory(requestModel.getMailSubType());
		accpObj.setDispatchNumber(requestModel.getDispatchNo());
		accpObj.setYear(requestModel.getDispatchYear());
		accpObj.setReceptacleNumber(requestModel.getReceptacleNumber());
		accpObj.setLastBagIndicator(requestModel.getLastBag() ? 1 : 0);
		accpObj.setRegisteredIndicator(requestModel.getRegistered() ? 1 : 0);
		accpObj.setPieces(BigInteger.ONE);
		accpObj.setWeight(requestModel.getWeight());
		accpObj.setFlagCRUD(Action.CREATE.toString());
		accpObj.setNextDestination(requestModel.getMailBagNewDestination());

		// use mailbag processor
		accpObj.setEmbargoFlag(requestModel.getEmbargoFlag());

		accpObj.setLastBagIndicator(requestModel.getLastBag() ? 1 : 0);
		accpObj.setMailBagMailCategory(requestModel.getMailCategory());
		accpObj.setMailBagMailSubcategory(requestModel.getMailSubType());
		accpObj.setMailType(requestModel.getMailType());
		if (StringUtils.isEmpty(accpObj.getTerminal())) {
			accpObj.setTerminal("T1");
		}
		accpObj.setTenantId(requestModel.getTenantAirport());
		accpObj.setUldNumber(requestModel.getShipmentLocation());
		accpObj.setShipmentLocation(requestModel.getShipmentLocation() == "" ? null : requestModel.getShipmentLocation());
		accpObj.setUser(requestModel.getUser());
		accpObj.setWeight(requestModel.getWeight());
		mailExportAcceptance.add(accpObj);
		acpRequest.setMailExportAcceptance(mailExportAcceptance);
		acpRequest.setUldNumber(requestModel.getShipmentLocation() == "" ? null : requestModel.getShipmentLocation());
		acpRequest.setAgentCode(requestModel.getAgent());
		acpRequest.setTenantId(requestModel.getTenantAirport());
		acpRequest.setSearchMode("ByAgent");
		acpRequest.setFromMSS(true);
		if (StringUtils.isEmpty(acpRequest.getTerminal())) {
			acpRequest.setTerminal("T1");
		}
//		acpRequest.setMailBagWeight(requestModel.getMailBagWeight());
		
		String connectingUrl = dao.getConnectingUrls();
		//String connectingUrl = "http://localhost:9150";
		RestTemplate rest = CosysApplicationContext.getRestTemplate();
		rest.exchange(connectingUrl + "/expaccpt/api/export/acceptance/insertExportData", HttpMethod.POST,
				getMessagePayload(acpRequest, MediaType.APPLICATION_JSON), Object.class);
		
//		accpSvcObj.insertData(acpRequest);
	}

	@Override
	public void updateLyingList(MSSMailBagMovement requestModel) throws CustomException {
		dao.updateLyingList(requestModel);
	}

	@Override
	public void load(MSSMailBagMovement requestModel, BigInteger segmentId) throws CustomException {

		CommonLoadShipment commonLoadShipment = new CommonLoadShipment();
		commonLoadShipment.setFlightKey(requestModel.getManifestedFlightKey());
		commonLoadShipment.setFlightOriginDate(requestModel.getManifestedFlightDate());
		commonLoadShipment.setFlightId(requestModel.getManifestedFltId());
		commonLoadShipment.setFlightSegmentId(segmentId);
		commonLoadShipment.setMailBagFlag(true);

		List<BuildUpULD> uldList = new ArrayList<>();
		List<UldShipment> shipmentList = new ArrayList<>();
		BuildUpULD buildUpULD = new BuildUpULD();
		buildUpULD.setUldTrolleyNo(requestModel.getUldKey());
		List<BuildUpMailSearch> mailBagDetail = dao.fetchMailbagDetail(requestModel);
		mailBagDetail.forEach(mailBag -> {
			UldShipment uldShipment = new UldShipment();
			uldShipment.setShipmentNumber(mailBag.getShipmentNumber());
			uldShipment.setAssUldTrolleyNo(requestModel.getUldKey());
			uldShipment.setFlightKey(requestModel.getManifestedFlightKey());
			uldShipment.setFlightOriginDate(requestModel.getManifestedFlightDate());
			uldShipment.setSegment(mailBag.getFlightOffPoint());
			uldShipment.setShipmentId(mailBag.getShipmentId());
			if (Optional.ofNullable(mailBag.getSegmentId()).isPresent()) {
				uldShipment.setSegmentId(mailBag.getSegmentId());
				uldShipment.setFlightSegmentId(mailBag.getSegmentId());
			} else {
				uldShipment.setSegmentId(segmentId);
				uldShipment.setFlightSegmentId(segmentId);
			}
			uldShipment.setFlightId(requestModel.getManifestedFltId());
			uldShipment.setPhcIndicator(0);
			uldShipment.setMailBagList(mailBag.getMailBags());
			uldShipment.setLoadShipmentList(new ArrayList<LoadedShipment>());
			uldShipment.getLoadShipmentList().forEach(a -> {
				a.setFlightId(requestModel.getManifestedFltId());
			});
			uldShipment.setMailInd("MAIL");
			shipmentList.add(uldShipment);
		});
		buildUpULD.setShipmentList(shipmentList);
		buildUpULD.setFlightId(requestModel.getManifestedFltId());
//		buildUpULD.setWareHouseLocation(requestModel.getWarehouseLocation());
		uldList.add(buildUpULD);
		commonLoadShipment.setUldList(uldList);
		// loadShipmentService.insertLoadingData(commonLoadShipment);
		String connectingUrl = dao.getConnectingUrls();
//		String connectingUrl = "http://localhost:8380";
		RestTemplate rest = CosysApplicationContext.getRestTemplate();
		rest.exchange(connectingUrl + "/expbu/api/export/buildup/load-shipment/loadmailbags", HttpMethod.POST,
				getMessagePayload(commonLoadShipment, MediaType.APPLICATION_JSON), Object.class);
	}

	@Override
	public void unload(MSSMailBagMovement requestModel, BigInteger segmentId) throws CustomException {

		UnloadShipmentRequest unloadShipmentRequest = new UnloadShipmentRequest();
//		if (!StringUtils.isEmpty(requestModel.getStoreLocation()))
//			unloadShipmentRequest.setAssUldTrolleyNo(requestModel.getStoreLocation()); //
//		else
//			unloadShipmentRequest.setAssUldTrolleyNo(requestModel.getShipmentLocation()); //
		
		unloadShipmentRequest.setAssUldTrolleyNo(requestModel.getUldKey());

		Flight flight = new Flight();
		flight.setFlightKey(requestModel.getManifestedFlightKey());
		flight.setFlightId(requestModel.getManifestedFltId());
		unloadShipmentRequest.setFlight(flight);

		Segment segment = new Segment();
		segment.setFlightBoardPoint(requestModel.getFlightBoardPoint());
		segment.setFlightOffPoint(requestModel.getFlightOffPoint());
		segment.setSegmentId(segmentId);
		unloadShipmentRequest.setSegment(segment);

		UnloadShipment unloadShipments = new UnloadShipment();
		unloadShipments.setIsFromMailUnload(true);
		unloadShipments.setFlight(flight);
		unloadShipments.setSegment(segment);
		unloadShipments.setAssUldTrolleyId(requestModel.getAssUldTrolleyId());
		unloadShipments.setLoadedShipmentInfoId(requestModel.getLoadedShipmentInfoId());
		unloadShipments.setShipmentId(requestModel.getShipmentId());
		// ExportMailBagManifest getLoadedPiecesWeight = exportMailManifestDao.getLoadedPiecesWeight(e);
		unloadShipments.setLoadedPieces(requestModel.getPieces());
		unloadShipments.setLoadedWeight(requestModel.getWeight());
		unloadShipments.setAssUldTrolleyNumber(requestModel.getUldKey());  //
		unloadShipments.setManifestShipmentHouseInfoId(requestModel.getManifestShipmentHouseInfoId());
		
		unloadShipments.setIsFromMailUnload(true);
		unloadShipments.setShipmentType("MAIL");

		List<String> houses = new ArrayList<>();
		houses.add(requestModel.getMailBagId());
		unloadShipments.setHouseNumbers(houses);

		UnloadShipmentInventory shpmtInventoryList = new UnloadShipmentInventory();
		// This is what you need to patch with input entry from pop-up
		shpmtInventoryList.setShipmentLocation(requestModel.getUldKey()); //
		// This is what is entered in Warehouse location of popup
		shpmtInventoryList.setWarehouseLocation(requestModel.getWarehouseLocation());
		shpmtInventoryList.setFlight(flight);
		shpmtInventoryList.setLoadedShipmentInfoId(requestModel.getLoadedShipmentInfoId());
		// ExportMailBagManifest mail = exportMailManifestDao.getPiecesWeight(e);
		shpmtInventoryList.setPieces(requestModel.getLoadedHousePieces());
		shpmtInventoryList.setWeight(requestModel.getLoadedHouseWeight());
		shpmtInventoryList.setShipmentId(requestModel.getShipmentId());
		shpmtInventoryList.setHouseNumbers(houses);

		List<UnloadShipmentInventory> listUnloadShipmentInventory = new ArrayList<UnloadShipmentInventory>();
		listUnloadShipmentInventory.add(shpmtInventoryList);
		unloadShipments.setShpmtInventoryList(listUnloadShipmentInventory);

		List<UnloadShipment> listUnloadShipment = new ArrayList<UnloadShipment>();
		listUnloadShipment.add(unloadShipments);
		unloadShipmentRequest.setUnloadShipments(listUnloadShipment);
		unloadShipmentRequest.setUser("MSS");
		String connectingUrl = dao.getConnectingUrls();
//		String connectingUrl = "http://localhost:8380";
		RestTemplate rest = CosysApplicationContext.getRestTemplate();
		rest.exchange(connectingUrl + "/expbu/api/export/buildup/unload-shipment/updateShipmentFromMail",
				HttpMethod.POST, getMessagePayload(unloadShipmentRequest, MediaType.APPLICATION_JSON), Object.class);

	}

	protected HttpEntity<Object> getMessagePayload(Object payload, MediaType mediaType) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(mediaType));
		headers.setContentType(mediaType);
		return new HttpEntity<>(payload, headers);
	}

	@Override
	public void breakDown(MSSMailBagMovement requestModel) throws CustomException {

		MailbagAirmailInterfaceModel mailbagModel = MailbagAirmailInterfaceUtil
				.getMailDetailsSplit(requestModel.getMailBagId());
		requestModel.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
				+ mailbagModel.getDestinationAirport() + mailbagModel.getDestinationQualifier());
		requestModel.setDispatchNumber(mailbagModel.getDispatchNumber());
		requestModel.setDispatchYear(new BigInteger(mailbagModel.getYear()));
		requestModel.setOriginOfficeExchange(
				mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport() + mailbagModel.getOriginQualifier());
		requestModel.setMailCategory(mailbagModel.getCategory());
		requestModel.setMailSubType(mailbagModel.getSubCategory());
		requestModel.setPieces(BigInteger.ONE);
		requestModel.setWeight(mailbagModel.getWeight());
		requestModel.setLastBag(mailbagModel.isLastBagIndicator());
		requestModel.setRegistered(mailbagModel.getRegisteredInsuredFlag().equalsIgnoreCase("1") ? true : false);

		// 1. Add shipment information to Shipment Master
		ShipmentMasterAirmailInterface shipmentMaster = new ShipmentMasterAirmailInterface();
		shipmentMaster.setShipmentType(ShipmentType.Type.MAIL);
		shipmentMaster.setShipmentNumber(requestModel.getMailBagId().substring(0, 20));
		shipmentMaster.setCarrierCode(requestModel.getIncomimngFlightCarrier());
		shipmentMaster.setOriginOfficeExchange(requestModel.getOriginOfficeExchange());
		shipmentMaster.setDestinationOfficeExchange(requestModel.getDestinationOfficeExchange());
		shipmentMaster.setOrigin(mailbagModel.getOriginAirport());
		shipmentMaster.setDestination(mailbagModel.getDestinationAirport());
		shipmentMaster.setMailCategory(mailbagModel.getCategory());
		shipmentMaster.setMailSubCategory(mailbagModel.getSubCategory());

		// Get the shipment date
		shipmentMaster.setShipmentdate(requestModel.getShipmentDate());
		shipmentMasterService.createShipment(shipmentMaster);

		// 2. Add shipment information to Shipment Verification
		ShipmentVerificationAirmailInterfaceModel shipmentVerificationModel = new ShipmentVerificationAirmailInterfaceModel();
		shipmentVerificationModel.setFlightId(requestModel.getIncomingFlightId());
		shipmentVerificationModel.setShipmentId(shipmentMaster.getShipmentId());
		shipmentVerificationModel.setBreakDownPieces(requestModel.getPieces());
		shipmentVerificationModel.setBreakDownWeight(requestModel.getWeight());
		shipmentVerificationService.createShipmentVerification(shipmentVerificationModel);

		// 3. Add ULD/MT info to Break Down ULD/MT
		InboundBreakdownShipmentAirmailInterfaceModel inboundBreakDownShipmentModel = new InboundBreakdownShipmentAirmailInterfaceModel();
		inboundBreakDownShipmentModel.setShipmentdate(shipmentMaster.getShipmentdate());
		inboundBreakDownShipmentModel.setShipmentId(shipmentMaster.getShipmentId());
		inboundBreakDownShipmentModel.setShipmentNumber(shipmentMaster.getShipmentNumber());
		inboundBreakDownShipmentModel.setShipmentType(ShipmentType.Type.MAIL);
		inboundBreakDownShipmentModel.setFlightId(requestModel.getIncomingFlightId());
		inboundBreakDownShipmentModel
				.setShipmentVerificationId(shipmentVerificationModel.getImpShipmentVerificationId());
		inboundBreakDownShipmentModel.setUldNumber(requestModel.getUldNumber());
		// TODO: Need to check cargo pre-announcement function for break condition
		inboundBreakDownShipmentModel.setHandlingMode("BREAK");

		InboundBreakdownShipmentInventoryAirmailInterfaceModel inboundBreakDownShipmentInventoryModel = new InboundBreakdownShipmentInventoryAirmailInterfaceModel();
		inboundBreakDownShipmentInventoryModel.setShipmentId(shipmentMaster.getShipmentId());
		inboundBreakDownShipmentInventoryModel.setFlightId(requestModel.getIncomingFlightId());
		inboundBreakDownShipmentInventoryModel.setShipmentLocation(
				requestModel.getUldType() + requestModel.getUldNumber() + requestModel.getUldCarrier());
		inboundBreakDownShipmentInventoryModel.setWarehouseLocation(requestModel.getContainerlocation());
		inboundBreakDownShipmentInventoryModel.setPieces(requestModel.getPieces());
		inboundBreakDownShipmentInventoryModel.setWeight(requestModel.getWeight());

		List<InboundBreakdownShipmentInventoryAirmailInterfaceModel> inventory = new ArrayList<>();
		inventory.add(inboundBreakDownShipmentInventoryModel);
		inboundBreakDownShipmentModel.setInventory(inventory);

		// House
		InboundBreakdownShipmentHouseAirmailInterfaceModel inboundBreakdownShipmentHouseModel = new InboundBreakdownShipmentHouseAirmailInterfaceModel();
		inboundBreakdownShipmentHouseModel.setShipmentId(shipmentMaster.getShipmentId());
		inboundBreakdownShipmentHouseModel.setType(ShipmentType.Type.MAIL);
		inboundBreakdownShipmentHouseModel.setNumber(requestModel.getMailBagId());
		inboundBreakdownShipmentHouseModel.setPieces(requestModel.getPieces());
		inboundBreakdownShipmentHouseModel.setWeight(requestModel.getWeight());
		inboundBreakdownShipmentHouseModel.setOriginOfficeExchange(requestModel.getOriginOfficeExchange());
		inboundBreakdownShipmentHouseModel.setDestinationOfficeExchange(requestModel.getDestinationOfficeExchange());
		inboundBreakdownShipmentHouseModel.setMailCategory(requestModel.getMailCategory());
		inboundBreakdownShipmentHouseModel.setMailType(requestModel.getMailType());
		inboundBreakdownShipmentHouseModel.setMailSubType(requestModel.getMailSubType());
		inboundBreakdownShipmentHouseModel.setDispatchYear(requestModel.getDispatchYear());
		inboundBreakdownShipmentHouseModel.setDispatchNumber(requestModel.getDispatchNumber());
		inboundBreakdownShipmentHouseModel.setLastBag(requestModel.getLastBag());
		inboundBreakdownShipmentHouseModel.setRegistered(requestModel.getRegistered());
		inboundBreakdownShipmentHouseModel.setReceptacleNumber(requestModel.getReceptacleNumber());
		inboundBreakdownShipmentHouseModel.setNextDestination(requestModel.getNextDestination());
		inboundBreakdownShipmentHouseModel.setTransferCarrier(requestModel.getTransferCarrier());

		List<InboundBreakdownShipmentHouseAirmailInterfaceModel> house = new ArrayList<>();
		house.add(inboundBreakdownShipmentHouseModel);
		inboundBreakDownShipmentInventoryModel.setHouse(house);

		// SHC
		InboundBreakdownShipmentShcAirmailInterfaceModel inboundBreakdownShipmentShcModel = new InboundBreakdownShipmentShcAirmailInterfaceModel();
		// TODO Need to remove below hardcoding later.
		inboundBreakdownShipmentShcModel.setSpecialHandlingCode("MAL");

		List<InboundBreakdownShipmentShcAirmailInterfaceModel> shcs = new ArrayList<>();
		shcs.add(inboundBreakdownShipmentShcModel);
		inboundBreakDownShipmentInventoryModel.setShc(shcs);
		// set shc at shipment level
		inboundBreakDownShipmentModel.setShcs(shcs);

		this.createBreakDownTrolleyInfo(inboundBreakDownShipmentModel);

		// Shipment inventory
		InboundBreakdownAirmailInterfaceModel inboundBreakdownModel = new InboundBreakdownAirmailInterfaceModel();
		inboundBreakdownModel.setFlightId(requestModel.getIncomingFlightId());
		inboundBreakdownModel.setShipment(inboundBreakDownShipmentModel);
		shipmentInventoryService.createInventory(inboundBreakdownModel);
	}

	/**
	 * Method to create breakdown ULD/Trolley info
	 * 
	 * @param shipment
	 * @throws CustomException
	 */
	private void createBreakDownTrolleyInfo(InboundBreakdownShipmentAirmailInterfaceModel shipment)
			throws CustomException {
		InboundBreakdownShipmentAirmailInterfaceModel uldTrolley = this.inboundBreakDownDao
				.selectBreakDownULDTrolleyInfo(shipment);
		Optional<InboundBreakdownShipmentAirmailInterfaceModel> optUld = Optional.ofNullable(uldTrolley);
		BigInteger uldTrolleyId;
		if (!optUld.isPresent() || 0 == optUld.get().getId().intValue()) {
			this.inboundBreakDownDao.insertBreakDownULDTrolleyInfo(shipment);
			uldTrolleyId = shipment.getId();
		} else {
			inboundBreakDownDao.updateBreakDownULDTrolleyInfo(shipment);
			uldTrolleyId = optUld.get().getId();
		}
		// Add the inventory for a given ULD/Trolley
		createBreakDownInventory(shipment, uldTrolleyId);
	}

	/**
	 * Method to create breakdown storage info
	 * 
	 * @param shipment
	 * @param uldTrolleyId
	 * @throws CustomException
	 */
	private void createBreakDownInventory(InboundBreakdownShipmentAirmailInterfaceModel shipment,
			BigInteger uldTrolleyId) throws CustomException {
		for (InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory : shipment.getInventory()) {
			inventory.setPieces(BigInteger.ONE);
			inventory.setInboundBreakdownShipmentId(uldTrolleyId);
			InboundBreakdownShipmentInventoryAirmailInterfaceModel invtr = this.inboundBreakDownDao
					.selectInboundBreakdownShipmentInventoryModel(inventory);
			Optional<InboundBreakdownShipmentInventoryAirmailInterfaceModel> optinv = Optional.ofNullable(invtr);
			if (!optinv.isPresent() || 0 == optinv.get().getInventoryId().intValue()) {
				this.inboundBreakDownDao.insertBreakDownStorageInfo(inventory);
			} else if (optinv.isPresent()) {
				inventory.setInventoryId(optinv.get().getInventoryId());
				this.inboundBreakDownDao.updateBreakDownStorageInfo(inventory);
			}
			// Add Inventory specific SHC info
			createShcInfo(inventory);
			// Add Inventory specific house info
			createHouseInfo(inventory);
		}
	}

	/**
	 * Method to create SHC info for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	private void createShcInfo(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory)
			throws CustomException {
		for (InboundBreakdownShipmentShcAirmailInterfaceModel shc : inventory.getShc()) {
			shc.setShipmentInventoryId(inventory.getInventoryId());
			if (!this.inboundBreakDownDao.checkBreakDownStorageSHCInfo(shc)) {
				this.inboundBreakDownDao.insertBreakDownStorageSHCInfo(shc);
			} else {
				this.inboundBreakDownDao.updateBreakDownStorageSHCInfo(shc);
			}
		}
	}

	/**
	 * Method to create house info for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	private void createHouseInfo(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory)
			throws CustomException {
		for (InboundBreakdownShipmentHouseAirmailInterfaceModel house : inventory.getHouse()) {
			house.setShipmentInventoryId(inventory.getInventoryId());
			house.setPieces(BigInteger.ONE);
			if (!this.inboundBreakDownDao.checkBreakDownShipmentHouseModel(house)) {
				this.inboundBreakDownDao.insertBreakDownShipmentHouseModel(house);
			} else {
				this.inboundBreakDownDao.updateBreakDownShipmentHouseModel(house);
			}
		}
	}

	@Override
	public void messageTypeTGFMMBUL(MSSMailBagMovement requestModel) throws CustomException {

		requestModel.setShipmentNumber(requestModel.getMailBagId().substring(0, 20));
		LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(requestModel.getShipmentNumber());
		requestModel.setShipmentDate(shipmentDate);
		BigInteger shipmentId = dao.getShipmentIdForMBMovement(requestModel);
		if (Optional.ofNullable(shipmentId).isPresent()) {
			requestModel
					.setUldKey(requestModel.getUldType() + requestModel.getUldNumber() + requestModel.getUldCarrier());
			MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
			movableLocationModel.setKey(requestModel.getUldKey());
			movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
			requestModel.setShipmentId(shipmentId);
			requestModel.setWarehouseLocation(requestModel.getContainerlocation());
			dao.updateLocationForMBMovement(requestModel);

		} else {
			requestModel.addError("mailbag.is.not.yet.accepted", "", ErrorType.ERROR);
			throw new CustomException();
		}

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void messageTypeTGFMCULD(MSSMailBagMovement requestModel) throws CustomException {
		BigInteger segmentId = null;
		if (Optional.ofNullable(requestModel.getUldType()).isPresent()
				&& Optional.ofNullable(requestModel.getUldNumber()).isPresent()
				&& Optional.ofNullable(requestModel.getUldCarrier()).isPresent()) {
			requestModel.setContainerlocation(requestModel.getUldType() + requestModel.getUldNumber() + requestModel.getUldCarrier()); // Need to change
			requestModel.setUldKey(requestModel.getUldType() + requestModel.getUldNumber() + requestModel.getUldCarrier());
			if (Optional.ofNullable(requestModel.getOutgoingFlightNumber()).isPresent()
					&& Optional.ofNullable(requestModel.getOutgoingFlightDate()).isPresent()) {
				requestModel.setManifestedFlightKey(
						requestModel.getOutgoingFlightCarrier() + requestModel.getOutgoingFlightNumber());
				
				requestModel.setManifestedFltId(dao.getOutgoingFlightId(requestModel));
				if (!Optional.ofNullable(requestModel.getManifestedFltId()).isPresent()) {
				
					requestModel.addError("satsig.invalid.flight", "", ErrorType.ERROR,new String[]{requestModel.getManifestedFlightKey(),requestModel.getManifestedFlightDate().toString()});
					requestModel.setValidMailBags(false);
				}
				segmentId = dao.getFLightSegmentIdForexportFlight(requestModel);
				/*
				 * if (Optional.ofNullable(segmentId).isPresent()) { throw new
				 * CustomException("Already Assigned", "", ErrorType.ERROR); }
				 */
			}
			
			// Validate ULD is assign to flight
			// Get ULD, Segment and Flight Event details
			MSSMailBagMovement fltEvents = dao.getULDAndFLightEventsInfo(requestModel);
			if(!ObjectUtils.isEmpty(fltEvents)) {
				requestModel.addError("satsig.uld.assigned.to.flight", requestModel.getMailBagId(), ErrorType.ERROR,new String[] {fltEvents.getManifestedFlightKey(),fltEvents.getManifestedFlightDate().toString()});
				requestModel.setValidMailBags(false);
			} 

			BigInteger segmentCheck = dao.getFLightSegmentIdForAssignedTrolley(requestModel);
			Boolean boardPointCheck = checkUldInMove(requestModel);
			if (!boardPointCheck) {
				requestModel.setValidMailBags(false);
				requestModel.addError("des.is.not","", ErrorType.ERROR,new String[]{requestModel.getUldDestination(),requestModel.getManifestedFlightKey(),requestModel.getOutgoingFlightDate()});
			}
			// check content code of ULD
			String contentCode = dao.getContentCodeOfUld(requestModel.getContainerlocation());
			if (!StringUtils.isEmpty(contentCode) && !"M".equalsIgnoreCase(contentCode)) {
				requestModel.addError("uld.can.not.be.other.than.m", "", ErrorType.ERROR);
			}
			// check for DLS Completion. If it is completed then can't go ahead
			MSSMailBagMovement expFlightEventsData = dao.getExpFLightEventsInfo(requestModel);
			if (expFlightEventsData.getDlsCompleted()) {
			
				requestModel.setValidMailBags(false);
				requestModel.addError("dls.for.the.flight", "", ErrorType.ERROR,new String[] {requestModel.getManifestedFlightKey(),requestModel.getOutgoingFlightDate()});
			}

			if (expFlightEventsData.getDeparted()) {
				requestModel.setValidMailBags(false);
				
				requestModel.addError("flight.is.already.completed','Flight {0} / {1} is already completed.", "", ErrorType.ERROR,new String[] {requestModel.getManifestedFlightKey(),requestModel.getOutgoingFlightDate()});
			}
			if (!Optional.ofNullable(segmentCheck).isPresent() && Optional.ofNullable(segmentId).isPresent()
					&& !expFlightEventsData.getDeparted() && !expFlightEventsData.getDlsCompleted()
					&& boardPointCheck) {
				if (requestModel.getValidMailBags()) {
					if (dao.checkDataInUldMaster(requestModel) == 0) {
						UldInfoModel uldInfo = new UldInfoModel();
						uldInfo.setUldType(requestModel.getUldType());
						uldInfo.setUldNumber(requestModel.getUldNumber());
						uldInfo.setUldCarrierCode(requestModel.getUldCarrier());
						uldInfo.setUldKey(requestModel.getContainerlocation());
						uldInfo.setApronCargoLocation("CARGO");
						uldInfo.setContentCode("M");
						uldInfo.setUldStatus("INW");
						uldInfo.setUldConditionType("SER");
						uldInfo.setOutboundFlightId(requestModel.getManifestedFltId());
						uldCommonService.updateUldInfo(uldInfo);
					}
					AssignULD assUld = new AssignULD();
					ULDIInformationDetails uld = new ULDIInformationDetails();
					uld.setContentCode("M");
					ULDIInformationDetails configDetails = dao.getUldConfigDetails(requestModel);
					if (Optional.ofNullable(configDetails).isPresent()) {
						uld.setHeightCode(configDetails.getHeightCode());
						uld.setTareWeight(configDetails.getTareWeight());
					}
					uld.setHandlingArea("T1");
					uld.setPhcFlag(false);
					uld.setSegmentId(segmentId);
					uld.setUldTrolleyNo(requestModel.getContainerlocation());
					uld.setFlightId(requestModel.getManifestedFltId());
					assUld.setUld(uld);
					assUld.setFlightKey(requestModel.getManifestedFlightKey());
					String date = requestModel.getOutgoingFlightDate().substring(0, 4) + "-"
							+ requestModel.getOutgoingFlightDate().substring(4, 6) + "-"
							+ requestModel.getOutgoingFlightDate().substring(6);
					assUld.setFlightOriginDate(LocalDate.parse(date));
					assUld.setTenantId(requestModel.getTenantAirport());
					String connectingUrl = dao.getConnectingUrls();
//					String connectingUrl = "http://localhost:8380";
					RestTemplate rest = CosysApplicationContext.getRestTemplate();
					try {
						rest.exchange(connectingUrl + "/expbu/api/assignUld/addTrolley", HttpMethod.POST,
								getMessagePayload(assUld, MediaType.APPLICATION_JSON), Object.class);
						triggerAirmailStatusEvent(requestModel);
					} catch (Exception e) {
						System.err.println(e);
					}

				}
			}
		}
		if (!requestModel.getMessageList().isEmpty()) {
			throw new CustomException();
		}

	}

	private void triggerAirmailStatusEvent(MSSMailBagMovement requestModel) {
		AirmailStatusEvent event = new AirmailStatusEvent();
		event.setSourceTriggerType("MSSTGFMCULD");
		event.setMailBag(requestModel.getMailBagId());
		event.setFlightId(requestModel.getManifestedFltId());
		event.setCarrierCode(requestModel.getOutgoingFlightCarrier());
		event.setManifestedUldKey(requestModel.getContainerlocation());
		event.setContainerDestination(requestModel.getContainerlocation());
		event.setStatus("CREATED");
		event.setTenantId(TenantContext.getTenantId());
		event.setCreatedBy(requestModel.getUser());
		event.setCreatedOn(LocalDateTime.now());
		//produce.publish(event);

	}

	private boolean checkUldInMove(MSSMailBagMovement requestModel) throws CustomException {
		List<String> flightBoardsPoints = dao.getFlightBoardPointsForCheckDestination(requestModel);
		return !StringUtils.isEmpty(requestModel.getUldDestination())
				&& flightBoardsPoints.contains(requestModel.getUldDestination());
	}

   @Override
   public boolean checkMssIncomingIsActiveOrNot() throws CustomException {
      return dao.checkMssIncomingIsActiveOrNot();
   }
}