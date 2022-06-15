/**
 * 
 * HandoverInboundUldTrollyServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.common.validator.LoadingMoveableLocationValidator;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypeProcess;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.EnumCheck;
import com.ngen.cosys.impbd.constants.StatusEnum;
import com.ngen.cosys.impbd.dao.HandoverInboundUldTrollyDao;
import com.ngen.cosys.impbd.model.FlightEventsModel;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.impbd.model.HandoverRampCheckInModel;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.model.FlightValidateModel;

/**
 * This class takes care of the responsibilities related to the Handover Inbound
 * UldTrolly service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Service
public class HandoverInboundUldTrollyServiceImpl implements HandoverInboundUldTrollyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandoverInboundUldTrollyServiceImpl.class);

	@Autowired
	private LoadingMoveableLocationValidator loadingmoveablelocationvalidator;

	@Autowired
	private HandoverInboundUldTrollyDao handoverInboundUldTrollyDao;

	@Autowired
	private FlightValidationDao flightValidationDao;

	private static final String FETCH_FLIGHT_ID_ERROR = "TROLLEY4";
	private static final String FETCH_ULD_TROLLEY_ERROR = "TROLLEY3";
	private static final String FETCH_ULD_CHECK_ERROR = "ULDCHECK1";
	private static final String DUPLICATE_TROLLEY_NUMER = "Duplicate ULD/Trolley exist";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.HandoverInboundUldTrollyServiceImpl#
	 * fetchInboundTrolly(com.ngen. cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HandoverInboundTrolly> fetchInboundTrolly(FlightModel inboundTrolly) throws CustomException {

		// Flight level checks
		FlightValidateModel flight = new FlightValidateModel();
		flight.setFlightType(FlightType.IMPORT.getType());
		flight.setFlightKey(inboundTrolly.getFlightNumber());
		flight.setFlightDate(inboundTrolly.getFlightDate());
		flight.setTenantAirport(inboundTrolly.getTenantAirport());

		boolean isFlightExist = flightValidationDao.isFlightExist(flight, FlightType.IMPORT.getType());

		if (isFlightExist) {
			boolean isFlightArrived = flightValidationDao.validateArivalFlightForImport(flight,
					inboundTrolly.getTenantAirport());
			if (!isFlightArrived) {
				throw new CustomException("incoming.flight.arrivalCheck", null, "E");
			}
		}
		if (!isFlightExist) {
			boolean isFlightCancelled = flightValidationDao.isIncomingFlightCancelled(flight,
					inboundTrolly.getTenantAirport());
			if (isFlightCancelled) {
				throw new CustomException("incoming.flight.cancelled", null, "E");
			} else {
				throw new CustomException("invalid.import.flight", null, "E");
			}
		}

		List<HandoverInboundTrolly> inboundTrollyList = handoverInboundUldTrollyDao.fetchInboundTrolly(inboundTrolly);

		inboundTrollyList = inboundTrollyList.stream()
				.filter(obj -> !CollectionUtils.isEmpty(obj.getHandoverInboundContainerTrolly()) && StringUtils
						.hasText(obj.getHandoverInboundContainerTrolly().get(0).getContainerTrolleyNumber()))
				.collect(Collectors.toList());
		inboundTrollyList.forEach(obj -> obj.setCountofTrolleys(obj.getHandoverInboundContainerTrolly().size()));
		LOGGER.debug(com.ngen.cosys.framework.util.LoggerUtil.getLoggerMessage(this.getClass().getName(),
				"fetchInboundTrolly", Level.DEBUG, inboundTrolly, inboundTrollyList));
		return inboundTrollyList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.HandoverInboundUldTrollyServiceImpl#
	 * insertInboundTrolly(com. ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HandoverInboundTrolly> insertInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException {

		// checks for duplicate trolley number
		checkUldNumber(inboundTrolly);

		HandoverInboundTrolly handoverFlightId = handoverInboundUldTrollyDao.getFlightId(inboundTrolly);
		if (ObjectUtils.isEmpty(handoverFlightId)) {
			throw new CustomException(FETCH_FLIGHT_ID_ERROR, "", ErrorType.ERROR);
		}

		// Check for check in complete
		if (!ObjectUtils.isEmpty(handoverFlightId) && !ObjectUtils.isEmpty(handoverFlightId.getFlightId())) {
			boolean isCheckInCompleted = this.handoverInboundUldTrollyDao
					.isCheckInCompleted(handoverFlightId.getFlightId());
			if (isCheckInCompleted) {
				throw new CustomException("checkin.already.completed", "", ErrorType.ERROR);
			}
		}

		HandoverInboundTrolly tripDetails = handoverInboundUldTrollyDao.tripDetails(inboundTrolly);
		if (tripDetails == null) {
			inboundTrolly.setTripId(EnumCheck.TRIPID.toInteger());
		} else {
			inboundTrolly.setTripId(tripDetails.getTripId() + 1);
		}
		inboundTrolly.setFlightId(handoverFlightId.getFlightId());
		List<HandoverInboundTrolly> handoverData = handoverInboundUldTrollyDao.insertInboundTrolly(inboundTrolly);
		inboundTrolly = insertData(inboundTrolly);
		LOGGER.debug(com.ngen.cosys.framework.util.LoggerUtil.getLoggerMessage(this.getClass().getName(),
				"insertInboundTrolly", Level.DEBUG, inboundTrolly, null));
		return handoverData;

	}

	HandoverInboundTrolly checkUldNumber(HandoverInboundTrolly inboundTrolly) throws CustomException {
		for (HandoverInboundContainerTrolly trolley : inboundTrolly.getHandoverInboundContainerTrolly()) {
			MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
			requestModel.setKey(trolley.getContainerTrolleyNumber());
			requestModel.setFlightKey(inboundTrolly.getFlightNumber());
			requestModel.setFlightDate(inboundTrolly.getFlightDate());
			requestModel.setProcess(MoveableLocationTypeProcess.ProcessTypes.EXPORT);
			requestModel.setPropertyKey("uldTrolleyNo");
			requestModel.setContentCode(inboundTrolly.getContentCode());

			MoveableLocationTypeModel moveableLocationTypeModel = loadingmoveablelocationvalidator.split(requestModel);
			if (moveableLocationTypeModel.getTrolley()) {
				trolley.setUsedAsTrolley(true);
				trolley.setContentCode("C");
			} else {
				trolley.setContentCode("C");
			}
			// Check if the location is an dummy location then do not allow
			if (moveableLocationTypeModel.getDummyLocation()) {
				moveableLocationTypeModel.addError("data.invalid.moveable.location", "uldTrolleyNo", ErrorType.APP);
			}
			if (!moveableLocationTypeModel.getMessageList().isEmpty()) {
				throw new CustomException("data.invalid.moveable.location", "containerTrolleyNumber", ErrorType.APP);

			}
		}
		return inboundTrolly;

	}

	/**
	 * Checks for duplicate trolley number and throws exception
	 * 
	 * @param inboundTrolly
	 * @throws CustomException
	 */
	private void checkForDuplicateTrolleyNumber(HandoverInboundTrolly inboundTrolly) throws CustomException {
		List<String> trolleyNumberDuplicate = new ArrayList<>();
		boolean errorExists = false;
		if (!CollectionUtils.isEmpty(inboundTrolly.getHandoverInboundContainerTrolly())) {
			inboundTrolly.setHandoverInboundContainerTrolly(inboundTrolly.getHandoverInboundContainerTrolly().stream()
					.filter((trolley -> !(trolley.getFlagCRUD().equalsIgnoreCase(Action.READ.toString()))))
					.collect(Collectors.toList()));

			// check for duplicate from request
			inboundTrolly.getHandoverInboundContainerTrolly().stream()
					.forEach(trolley -> trolleyNumberDuplicate.add(trolley.getContainerTrolleyNumber()));
			Set<String> inboundTrollyForDuplicate = new HashSet<>(trolleyNumberDuplicate);
			if (!CollectionUtils.isEmpty(inboundTrollyForDuplicate)
					&& ((trolleyNumberDuplicate.size() != inboundTrollyForDuplicate.size()))) {
				throw new CustomException(DUPLICATE_TROLLEY_NUMER, "", ErrorType.ERROR);
			}
			// check for duplicate from db
			List<String> trolleyListSpecificToFlight = handoverInboundUldTrollyDao
					.searchContainerTrollyForDuplicate(inboundTrolly);

			if (!CollectionUtils.isEmpty(trolleyListSpecificToFlight)) {
				for (String trolleyNumber : inboundTrollyForDuplicate) {
					if (trolleyListSpecificToFlight.contains(trolleyNumber)) {
						errorExists = true;
						break;
					}
				}

				if (errorExists) {
					throw new CustomException(DUPLICATE_TROLLEY_NUMER, "", ErrorType.ERROR);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.HandoverInboundUldTrollyServiceImpl#
	 * addUpdateTrolly(com. ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HandoverInboundTrolly> addUpdateTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException {

		checkUldNumber(inboundTrolly);

		for (HandoverInboundContainerTrolly trolley : inboundTrolly.getHandoverInboundContainerTrolly()) {
			if (!trolley.getMessageList().isEmpty()) {
				throw new CustomException();
			}
		}

		checkForDuplicateTrolleyNumber(inboundTrolly);

		// Check for check in complete
		if (!ObjectUtils.isEmpty(inboundTrolly.getFlightId())) {
			boolean isCheckInCompleted = this.handoverInboundUldTrollyDao
					.isCheckInCompleted(inboundTrolly.getFlightId());
			if (isCheckInCompleted) {
				throw new CustomException("checkin.already.completed", "", ErrorType.ERROR);
			}
		}

		List<HandoverInboundTrolly> handoverData = handoverInboundUldTrollyDao.updateInboundTrolly(inboundTrolly);
		List<HandoverInboundContainerTrolly> insertListOfInbound = inboundTrolly.getHandoverInboundContainerTrolly()
				.stream().filter(trolley -> trolley.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(insertListOfInbound)) {
			inboundTrolly = insertData(inboundTrolly);
		}
		LOGGER.debug(com.ngen.cosys.framework.util.LoggerUtil.getLoggerMessage(this.getClass().getName(),
				"deleteTrollyNo", Level.DEBUG, inboundTrolly, null));
		return handoverData;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.service.HandoverInboundUldTrollyServiceImpl#
	 * deleteServiceNo(com. ngen.cosys.impbd.model.HandoverInboundTrolly)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HandoverInboundContainerTrolly> deleteTrollyNo(HandoverInboundContainerTrolly deleteTrollyNo)
			throws CustomException {
		List<HandoverInboundContainerTrolly> deleteTrollyNoList = handoverInboundUldTrollyDao
				.deleteTrollyNo(deleteTrollyNo);
		LOGGER.debug(com.ngen.cosys.framework.util.LoggerUtil.getLoggerMessage(this.getClass().getName(),
				"deleteTrollyNo", Level.DEBUG, deleteTrollyNo, null));
		return deleteTrollyNoList;
	}

	/**
	 * Method to Save Handover Inbound data with Trolly Number details
	 * 
	 * @param HandoverInboundTrolly
	 *            request
	 * @param HandoverInboundContainerTrolly
	 *            data.
	 * @param Handover
	 *            Inbound data
	 * @return Trolly Number.
	 * @throws CustomException
	 */
	public HandoverInboundTrolly insertData(HandoverInboundTrolly inboundTrolly) throws CustomException {
		List<String> trolleyNoList = new ArrayList<>();
		HandoverInboundTrolly timeDate = handoverInboundUldTrollyDao.getData(inboundTrolly);
		HandoverInboundContainerTrolly handoverInbound = new HandoverInboundContainerTrolly();
		HandoverInboundContainerTrolly handoverInboundTrolley = new HandoverInboundContainerTrolly();
		HandoverRampCheckInModel rampchCheckIn = new HandoverRampCheckInModel();
		FlightEventsModel flightEvents = new FlightEventsModel();
		flightEvents.setFlightId(inboundTrolly.getFlightId());
		FlightEventsModel events = handoverInboundUldTrollyDao.checkFligtId(flightEvents);
		if (events != null && events.getFirstULDTowedBy() != null) {
			if (events.getLastULDTowedBy() != null && flightEvents.getLastULDTowedAt() != null) {
				flightEvents.setLastULDTowedBy(inboundTrolly.getHandedOverBy());
				flightEvents.setLastULDTowedAt(timeDate.getStartedAt());
				handoverInboundUldTrollyDao.updatetractorData(flightEvents);
			} else {
				flightEvents.setLastULDTowedBy(inboundTrolly.getHandedOverBy());
				flightEvents.setLastULDTowedAt(timeDate.getStartedAt());
				handoverInboundUldTrollyDao.updatetractorData(flightEvents);
			}
		} else {
			flightEvents.setFirstULDTowedBy(inboundTrolly.getHandedOverBy());
			flightEvents.setFirstULDTowedAt(timeDate.getStartedAt());
			flightEvents.setLastULDTowedBy(inboundTrolly.getHandedOverBy());
			flightEvents.setLastULDTowedAt(timeDate.getCompletedAt());
			handoverInboundUldTrollyDao.updatetractorId(flightEvents);
		}

		for (HandoverInboundContainerTrolly inboundUldTrollys : inboundTrolly.getHandoverInboundContainerTrolly()) {
			handoverInboundTrolley.setContainerTrolleyNumber(inboundUldTrollys.getContainerTrolleyNumber());
			if (trolleyNoList.contains(inboundUldTrollys.getContainerTrolleyNumber())) {
				throw new CustomException(FETCH_ULD_TROLLEY_ERROR, "", ErrorType.ERROR);
			}
			trolleyNoList.add(inboundUldTrollys.getContainerTrolleyNumber());
		}
		for (HandoverInboundContainerTrolly inboundUldTrolly : inboundTrolly.getHandoverInboundContainerTrolly()) {

			handoverInbound.setImpHandOverId(inboundTrolly.getImpHandOverId());
			handoverInbound.setContainerTrolleyNumber(inboundUldTrolly.getContainerTrolleyNumber());
			handoverInbound.setUsedAsTrolley(inboundUldTrolly.getUsedAsTrolley());
			handoverInbound.setCreatedBy(StatusEnum.USERSTATUS.toString());
			handoverInbound.setFlightId(inboundTrolly.getFlightId());

			HandoverInboundContainerTrolly contentCodeSubgroup = handoverInboundUldTrollyDao
					.searchContentCode(handoverInbound);
			HandoverInboundContainerTrolly contentCode = handoverInboundUldTrollyDao
					.searchContentCodeType(contentCodeSubgroup);
			if (contentCode != null && (!contentCode.getApronCargoLocation().equalsIgnoreCase("C"))) {
				throw new CustomException(FETCH_ULD_CHECK_ERROR, inboundUldTrolly.getContainerTrolleyNumber(),
						ErrorType.ERROR);
			}
			List<HandoverInboundContainerTrolly> handoverList = handoverInboundUldTrollyDao
					.searchContainerTrollyId(handoverInbound);
			if (CollectionUtils.isEmpty(handoverList)) {
				handoverInbound = handoverInboundUldTrollyDao.insertContainerTrolly(handoverInbound);
			} else {
				handoverInbound = handoverInboundUldTrollyDao.updateContainerTrolly(handoverInbound);
			}
			rampchCheckIn.setFlightId(inboundTrolly.getFlightId());
			rampchCheckIn.setUldNumber(inboundUldTrolly.getContainerTrolleyNumber());
			rampchCheckIn.setContentCode(inboundUldTrolly.getContentCode());
			rampchCheckIn.setUsedAsTrolley(inboundUldTrolly.getUsedAsTrolley());
			List<HandoverRampCheckInModel> rampCheckInList = handoverInboundUldTrollyDao
					.searchRampCheckInId(rampchCheckIn);
			if (CollectionUtils.isEmpty(rampCheckInList)) {
				rampchCheckIn = handoverInboundUldTrollyDao.insertRampCheckIn(rampchCheckIn);
			} else {
				rampchCheckIn = handoverInboundUldTrollyDao.updateRampCheckIn(rampchCheckIn);
			}
		}
		return inboundTrolly;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.agent.service.ExportPrelodgeServiceImpl#editInboundTrolly(com.
	 * ngen. cosys.agent.model.ExportPrelodgShipment)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HandoverInboundTrolly> editInboundTrolly(HandoverInboundTrolly editTrollyData) throws CustomException {
		List<HandoverInboundTrolly> editInboundTrollydata = handoverInboundUldTrollyDao
				.editInboundTrolly(editTrollyData);
		LOGGER.debug(com.ngen.cosys.framework.util.LoggerUtil.getLoggerMessage(this.getClass().getName(),
				"editInboundTrolly", Level.DEBUG, editTrollyData, null));
		return editInboundTrollydata;
	}

}