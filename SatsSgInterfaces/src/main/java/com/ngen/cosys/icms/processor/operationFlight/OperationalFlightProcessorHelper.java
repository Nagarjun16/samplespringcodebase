/*******************************************************************************
 * Copyright (c) 2021 Coforge Technologies PVT LTD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ngen.cosys.icms.processor.operationFlight;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.dao.operationFlight.OperationalFlightDao;
import com.ngen.cosys.icms.dao.scheduleFlight.ScheduleFlightDao;
import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightAuto;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

/**
 * This class used for business validations
 */

@Component
public class OperationalFlightProcessorHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationalFlightProcessorHelper.class);

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private OperationalFlightDao operationalFlightDao;

	@Autowired
	private ScheduleFlightDao scheduleFlightDao;

	// validate Active flight Overlapping
	public Boolean validateActiveFlightOverlapping(OperationalFlightInfo flightInfo) throws ParseException {
		LOGGER.info("start vaidateActiveFlightOverlapping method ::");
		Boolean flag = false;
		if (operationalFlightDao.validationForActiveFlight(flightInfo) == 1) {
			flag = true;
		}
		LOGGER.info("end vaidateActiveFlightOverlapping method ::");
		return flag;
	}

	// validate Flight with same STA/STD exist or not
	public Boolean validateFlightOverlapping(OperationalFlightInfo flightInfo) throws ParseException {
		LOGGER.info("start validateFlightOverlapping method ::");
		Boolean flag = false;
		String fromFormat = ValidationConstant.XML_DATE_FORMAT;
		String toFormat = ValidationConstant.DATEFORMAT;
		for (OperationalFlightLegInfo OperationalFlightLegInfo : flightInfo.getFlightLegInfo()) {
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put(ValidationConstant.FLIGHT_KEY, flightInfo.getFlightKey());

			if (OperationalFlightLegInfo.getLegOrigin().equals(ValidationConstant.PORT_SIN)) {
				queryMap.put(ValidationConstant.DATE_STD,
						commonUtil.convertDateString(OperationalFlightLegInfo.getDateSTA(), fromFormat, toFormat));
				if (operationalFlightDao.validationForActiveArrivalFlightOverlapping(queryMap) == 1) {
					throw new MessageProcessingException("Flight with same STD already exist");
				}
			} else if (OperationalFlightLegInfo.getLegDestination().equals(ValidationConstant.PORT_SIN)) {
				queryMap.put(ValidationConstant.DATE_STA,
						commonUtil.convertDateString(OperationalFlightLegInfo.getDateSTA(), fromFormat, toFormat));
				if (operationalFlightDao.validationForActiveDepartureFlightOverlapping(queryMap) == 1) {
					throw new MessageProcessingException("Flight with same STA already exist");
				}
			}
			flag = true;
		}

		LOGGER.info("end vaidateActiveFlightOverlapping method ::");
		return flag;
	}

	// update Flight/Legs/segments
	// @Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void updateFltOperationalFlight(OperationalFlightInfo flightInfo) throws ParseException {
		LOGGER.info("start updateFltOperationalFlight method ::");
		checkForAutoFlight(flightInfo);
		BigInteger flightId = operationalFlightDao.fetchFlightId(flightInfo);
		if (flightId != null) {
			flightInfo.setFlightId(flightId);
			flightInfo.getFlightLegInfo().forEach(e1 -> e1.setFlightId(flightInfo.getFlightId()));
			flightInfo.getFlightSegmentInfo().forEach(e1 -> e1.setFlightId(flightInfo.getFlightId()));
		}
		String aircraftType = operationalFlightDao.fetchAircraftType(flightInfo);
		OperationalFlightInfo flightDetails = new OperationalFlightInfo();
		List<OperationalFlightLegInfo> legInfoList = new ArrayList<OperationalFlightLegInfo>();
		List<OperationalFlightSegmentInfo> segmentInfoList = new ArrayList<OperationalFlightSegmentInfo>();
		// set updated leg details
		this.updateLegDetails(flightInfo, aircraftType, flightDetails, legInfoList);

		// set updated segment details
		this.updateSegmentDetails(flightInfo, flightDetails, segmentInfoList);

		// set updated Service Type
		flightDetails.setServiceType(flightInfo.getServiceType());
		// flightDetails.setFlightDate(flightInfo.getFlightDate());
		flightDetails.setClosedForBooking(flightInfo.getClosedForBooking());
		flightDetails.setFlightId(flightInfo.getFlightId());
		flightDetails.setFlightAutoCompleteFlag(flightInfo.getFlightAutoCompleteFlag());
		this.operationalFlightDao.updateFltOperativeFlight(flightDetails);

		// set updated Flight Remarks
		if (flightInfo.getFlightRemarks() != null) {
			flightDetails.setFlightRemarks(flightInfo.getFlightRemarks());
			this.operationalFlightDao.updateFlightRemark(flightDetails);
		}
		LOGGER.info("end updateFltOperationalFlight method ::");
	}

	private void updateLegDetails(OperationalFlightInfo flightInfo, String aircraftType,
			OperationalFlightInfo flightDetails, List<OperationalFlightLegInfo> legInfoList) throws ParseException {
		LOGGER.info("Start updateLegDetails method ::");
		for (OperationalFlightLegInfo legInfo : flightInfo.getFlightLegInfo()) {
			if (ValidationConstant.PORT_SIN.equalsIgnoreCase(legInfo.getLegDestination())) {
				updateIncomingSINLeg(flightInfo, legInfoList, legInfo);
			} else if (ValidationConstant.PORT_SIN.equalsIgnoreCase(legInfo.getLegOrigin())) {
				updateOutgoingSINLeg(flightInfo, legInfoList, legInfo);
			} else {
				updateOtherLeg(legInfoList, legInfo);
			}

			flightDetails.setFlightLegInfo(legInfoList);
		}
		this.checkLegOrderBeforeInserting(legInfoList);
		this.operationalFlightDao.insertFltOperationalFlightLeg(flightDetails);
		LOGGER.info("End updateLegDetails method ::");
	}

	private void updateOtherLeg(List<OperationalFlightLegInfo> legInfoList, OperationalFlightLegInfo legInfo) {
		legInfoList.add(legInfo);
		legInfo.setIsDeleteLeg(false);
		this.operationalFlightDao.deleteExistingFlightLegs(legInfo);
	}

	private void updateOutgoingSINLeg(OperationalFlightInfo flightInfo, List<OperationalFlightLegInfo> legInfoList,
			OperationalFlightLegInfo legInfo) throws ParseException {
		int count = 0;
		for (OperationalFlightSegmentInfo operationalFlightsegmentInfo : flightInfo.getFlightSegmentInfo()) {
			if (ValidationConstant.PORT_SIN.equalsIgnoreCase(operationalFlightsegmentInfo.getSegmentOrigin())) {
				if (count == 0) {
					if (validateWorkedOnFlight(operationalFlightsegmentInfo,flightInfo)) {
						LOGGER.info("leg is worked on : " + legInfo);
						validateUpdationOnWorkedOnFlightLeg(legInfo, flightInfo);
						LOGGER.info("Not deleting worked on Leg : " + legInfo);
					} else {
						legInfoList.add(legInfo);
						legInfo.setIsDeleteLeg(true);
						LOGGER.info("Deleting Existing Leg: " + legInfo);
						this.operationalFlightDao.deleteExistingFlightLegs(legInfo);
					}
					count++;
				}
			}
		}
	}

	private void updateIncomingSINLeg(OperationalFlightInfo flightInfo, List<OperationalFlightLegInfo> legInfoList,
			OperationalFlightLegInfo legInfo) throws ParseException {
		int count = 0;
		for (OperationalFlightSegmentInfo operationalFlightsegmentInfo : flightInfo.getFlightSegmentInfo()) {
			if (ValidationConstant.PORT_SIN.equalsIgnoreCase(operationalFlightsegmentInfo.getSegmentDestination())) {
				if (count == 0) {
					if (validateWorkedOnFlight(operationalFlightsegmentInfo,flightInfo)
							|| validateFFMInfo(operationalFlightsegmentInfo)) {
						LOGGER.info("leg is worked on : " + legInfo);
						validateUpdationOnWorkedOnFlightLeg(legInfo, flightInfo);
						LOGGER.info("Not deleting worked on Leg : " + legInfo);
					} else {
						legInfoList.add(legInfo);
						legInfo.setIsDeleteLeg(true);
						LOGGER.info("Deleting Existing Leg: " + legInfo);
						this.operationalFlightDao.deleteExistingFlightLegs(legInfo);
					}
					count++;
				}
			}
		}
	}

	private Boolean validateFFMInfo(OperationalFlightSegmentInfo flightSegInfo) {
		Boolean flag = false;
		if (operationalFlightDao.validateFFMInfo(flightSegInfo) == 0) {
			return true;
		}
		return flag;
	}

	private void validateUpdationOnWorkedOnFlightLeg(OperationalFlightLegInfo legInfo, OperationalFlightInfo flightInfo)
			throws ParseException {
		OperationalFlightLegInfo legDetails = operationalFlightDao.getWorkedOnLegInfo(legInfo);
		OperationalFlightInfo flightDetails = new OperationalFlightInfo();
		if (updateLegTime(legInfo, legDetails)) {
			operationalFlightDao.UpdateTime(legInfo);

			flightDetails.setServiceType(flightInfo.getServiceType());
			flightDetails.setClosedForBooking(flightInfo.getClosedForBooking());
			flightDetails.setFlightId(flightInfo.getFlightId());
			flightDetails.setFlightAutoCompleteFlag(flightInfo.getFlightAutoCompleteFlag());
			this.operationalFlightDao.updateFltOperativeFlight(flightDetails);
			if (flightInfo.getFlightRemarks() != null) {
				flightDetails.setFlightRemarks(flightInfo.getFlightRemarks());
				this.operationalFlightDao.updateFlightRemark(flightDetails);
			}
		}
		if (checkLegUpdatedDateAndTenant(legInfo, legDetails)) {
			throw new MessageParseException("Flight is Worked On");
		}
	}

	private Boolean updateLegTime(OperationalFlightLegInfo legInfo, OperationalFlightLegInfo legDetails)
			throws ParseException {
		String format = ValidationConstant.DB_DATE_TIME_FORMAT;
		String toFormat = ValidationConstant.DATEFORMAT;
		String newSTATime = commonUtil.getTimeFromDateTimeString(legInfo.getDateSTA());
		String newSTDTime = commonUtil.getTimeFromDateTimeString(legInfo.getDateSTD());
		String previousSTATime = commonUtil.getTimeFromDateTime(legDetails.getDateSTA());
		String previousSTDTime = commonUtil.getTimeFromDateTime(legDetails.getDateSTD());
		legInfo.setStaTime(newSTATime);
		legInfo.setStdTime(newSTDTime);
		legInfo.setStaDate(commonUtil.convertDateString(legDetails.getDateSTA(), format, toFormat));
		legInfo.setStdDate(commonUtil.convertDateString(legDetails.getDateSTD(), format, toFormat));
		Boolean flag = false;
		if (!newSTATime.equals(previousSTATime) || !newSTDTime.equals(previousSTDTime)
				|| !legInfo.getAircraftType().equals(legDetails.getAircraftType())) {
			flag = true;
		}
		return flag;
	}

	private Boolean checkLegUpdatedDateAndTenant(OperationalFlightLegInfo legInfo, OperationalFlightLegInfo legDetails)
			throws ParseException {
		String fromFormat = ValidationConstant.XML_DATETIME_FORMAT;
		String format = ValidationConstant.DB_DATE_TIME_FORMAT;
		String toFormat = ValidationConstant.DATEFORMAT;
		String newSTA = commonUtil.convertDateString(legInfo.getDateSTA(), fromFormat, toFormat);
		String newSTD = commonUtil.convertDateString(legInfo.getDateSTD(), fromFormat, toFormat);
		String previousSTA = commonUtil.convertDateString(legDetails.getDateSTA(), format, toFormat);
		String previousSTD = commonUtil.convertDateString(legDetails.getDateSTD(), format, toFormat);
		Boolean flag = false;
		if (!legDetails.getLegDestination().equals(legInfo.getLegDestination())
				|| !legDetails.getLegOrigin().equals(legInfo.getLegOrigin()) || !previousSTA.equals(newSTA)
				|| !previousSTD.equals(newSTD)) {
			flag = true;
		}
		return flag;
	}

	private void checkLegOrderBeforeInserting(List<OperationalFlightLegInfo> legInfoList) {
		for (OperationalFlightLegInfo legInfo : legInfoList) {
			String legOffpoint = operationalFlightDao.checkLegOrder(legInfo);
			if (legOffpoint != null && !legOffpoint.equals(legInfo.getLegOrigin())) {
				throw new MessageProcessingException("Flight is Worked On");
			}
		}

	}

	private void updateSegmentDetails(OperationalFlightInfo flightInfo, OperationalFlightInfo flightDetails,
			List<OperationalFlightSegmentInfo> segmentInfoList) {
		LOGGER.info("Start updateSegmentDetails method ::");
		for (OperationalFlightSegmentInfo segmentInfo : flightInfo.getFlightSegmentInfo()) {
			if (ValidationConstant.PORT_SIN.equalsIgnoreCase(segmentInfo.getSegmentOrigin())) {
				if (validateWorkedOnFlight(segmentInfo,flightInfo)) {
					LOGGER.info("Not deleting worked on Segment : " + segmentInfo);
				} else {
					segmentInfoList.add(segmentInfo);
					segmentInfo.setIsDeleteSegment(true);
					LOGGER.info("Deleting Existing Segment: " + segmentInfo);
					this.operationalFlightDao.deleteExistingFlightSegments(segmentInfo);
				}
			} else if (ValidationConstant.PORT_SIN.equalsIgnoreCase(segmentInfo.getSegmentDestination())) {
				if (validateWorkedOnFlight(segmentInfo,flightInfo)) {
					LOGGER.info("Not deleting worked on Segment : " + segmentInfo);
				} else {
					segmentInfoList.add(segmentInfo);
					segmentInfo.setIsDeleteSegment(true);
					System.out.println("Deleting Existing Segment: " + segmentInfo);
					this.operationalFlightDao.deleteExistingFlightSegments(segmentInfo);
				}
			} else {
				segmentInfoList.add(segmentInfo);
				segmentInfo.setIsDeleteSegment(false);
				LOGGER.info("Deleting Existing Segment : " + segmentInfo);
				this.operationalFlightDao.deleteExistingFlightSegments(segmentInfo);
			}

			flightDetails.setFlightSegmentInfo(segmentInfoList);
		}
		this.operationalFlightDao.insertFltOperationalFlightSegment(flightDetails);
		LOGGER.info("End updateSegmentDetails method ::");
	}

	// validate Cancel flight Overlapping
	public Boolean validateCancelFlightOverlapping(OperationalFlightInfo flightInfo) {
		LOGGER.info("start vaidateCancelFlightOverlapping method ::");
		Boolean flag = false;
		BigInteger flightId = operationalFlightDao.fetchFlightId(flightInfo);
		if (flightId != null) {
			flightInfo.setFlightId(flightId);
		}
		if (operationalFlightDao.validationForCancelFlight(flightInfo) == 1) {
			flag = true;
		}
		LOGGER.info("end vaidateCancelFlightOverlapping method ::");
		return flag;
	}

	// validate Leg order
	public Boolean validateLegOrder(List<OperationalFlightLegInfo> list) {
		Boolean flag = false;
		LOGGER.info("start validateLegOrder method ::");
		String previousDestination = "";
		for (OperationalFlightLegInfo operationalFlightLegInfo : list) {
			if (!previousDestination.isEmpty()
					&& !operationalFlightLegInfo.getLegOrigin().equals(previousDestination)) {
				throw new MessageParseException("Previous Leg destination and next leg origin are not same");
			}
			previousDestination = operationalFlightLegInfo.getLegDestination();
			if (operationalFlightLegInfo.getLegOrigin().equals(operationalFlightLegInfo.getLegDestination())) {
				throw new MessageParseException("Leg origin and leg destination can't be same");
			}
		}
		LOGGER.info("end validateLegOrder method ::");
		return flag;
	}

	// validate worked-on condition
	public Boolean validateWorkedOnFlight(OperationalFlightSegmentInfo segmentInfo, OperationalFlightInfo flightInfo) {
		LOGGER.info("start validateWorkedOnFlight method ::");
		Boolean flag = false;
		int isValidateManifest = 0;
		segmentInfo.setFlightKey(flightInfo.getFlightKey());
		segmentInfo.setFlightDate(flightInfo.getFlightDate().toString());
		int isValidateFlightBooking = this.operationalFlightDao.validateFlightBooking(segmentInfo);
		int isValidateAssignedULD = this.operationalFlightDao.validateAssignedULD(segmentInfo);
		if (ValidationConstant.PORT_SIN.equalsIgnoreCase(segmentInfo.getSegmentOrigin())) {
			isValidateManifest = this.operationalFlightDao.validateDepartureManifest(segmentInfo);
		} else if (ValidationConstant.PORT_SIN.equalsIgnoreCase(segmentInfo.getSegmentDestination())) {
			isValidateManifest = this.operationalFlightDao.validateArivalManifest(segmentInfo);
		}
		String isValidateEXPFlightDeparted = this.operationalFlightDao.validateEXPFlightDeparted(segmentInfo);
		String isValidateIMPFlightCompleted = this.operationalFlightDao.validateIMPFlightCompleted(segmentInfo);
		LOGGER.info("isValidateFlightBooking : " + isValidateFlightBooking + ", isValidateAssignedULD : "
				+ isValidateAssignedULD + ", isValidateManifest : " + isValidateManifest
				+ ", isValidateEXPFlightDeparted : " + isValidateEXPFlightDeparted + ", isValidateIMPFlightCompleted : "
				+ isValidateIMPFlightCompleted);
		if (isValidateFlightBooking > 0 || isValidateAssignedULD > 0 || isValidateManifest > 0
				|| isValidateEXPFlightDeparted != null || isValidateIMPFlightCompleted != null) {
			flag = true;
		}
		LOGGER.info("End validateWorkedOnFlight method ::");
		return flag;
	}

	// validate First Arrival Point
	public void validateLegDestination(OperationalFlightInfo flightInfo) {
		LOGGER.info("inside validateLegDestination method::");
		List<OperationalFlightLegInfo> flightLegList = flightInfo.getFlightLegInfo();
		for (OperationalFlightLegInfo legs : flightLegList) {
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put(ValidationConstant.CODE, legs.getLegDestination());
			if (scheduleFlightDao.validationForAirportExistance(queryMap) == 0) {
				throw new MessageProcessingException("First Arrival Point doesn't exist");
			}
		}
		LOGGER.info("end validateLegDestination method ::");
	}

	public boolean isValidWorkedOnCondition(OperationalFlightInfo flightInfo) {
		Boolean flag = true;
		flightInfo.getFlightSegmentInfo().forEach(e1 -> e1.setFlightId(operationalFlightDao.fetchFlightId(flightInfo)));
		for (OperationalFlightSegmentInfo segmentinfo : flightInfo.getFlightSegmentInfo()) {
			if (!validateWorkedOnFlight(segmentinfo,flightInfo)) {
				return false;
			}
		}
		return flag;
	}

	public void checkForAutoFlight(OperationalFlightInfo flightInfo) {
		flightInfo.setFlightAutoCompleteFlag(ValidationConstant.ZERO);
		for (OperationalFlightLegInfo leg : flightInfo.getFlightLegInfo()) {
			if (!MultiTenantUtility.isTenantAirport(leg.getLegOrigin())) {
				this.operationalFlightDao.updateAutoComFlag(flightInfo);
			} else {
				checkAutoFlightCompleteFlagForExport(flightInfo);
				this.operationalFlightDao.updateAutoComFlag(flightInfo);
			}
		}
	}

	private void checkAutoFlightCompleteFlagForExport(OperationalFlightInfo flightInfo) {
		List<OperationalFlightAuto> autoflightlistCarrier = new ArrayList<OperationalFlightAuto>();
		List<OperationalFlightAuto> autoflightlistCarrierWithflt = new ArrayList<OperationalFlightAuto>();
		List<OperationalFlightAuto> autoflightlistCarrierWithDest = new ArrayList<OperationalFlightAuto>();
		List<OperationalFlightAuto> autoflightlistCarrierFltWithDest = new ArrayList<OperationalFlightAuto>();
		for (OperationalFlightLegInfo leg1 : flightInfo.getFlightLegInfo()) {
			leg1.setFlightKey(flightInfo.getFlightKey());
			leg1.setFlightNumber(flightInfo.getFlightNo());
			leg1.setCarrier(flightInfo.getCarrier());
			if (MultiTenantUtility.isTenantAirport(leg1.getLegOrigin())) {
				autoflightlistCarrier = this.operationalFlightDao.checkForAutoCarrier(leg1);
				autoflightlistCarrierWithflt = this.operationalFlightDao.checkForAutoFlightWithCarrier(leg1);
				autoflightlistCarrierWithDest = this.operationalFlightDao.checkForAutoFlightWithDest(leg1);
				autoflightlistCarrierFltWithDest = this.operationalFlightDao.checkForAutoCarrierWithDest(leg1);
			}
			LOGGER.info("autoflightlistCarrier : " + autoflightlistCarrier + ", autoflightlistCarrierWithflt : "
					+ autoflightlistCarrierWithflt + ", autoflightlistCarrierWithDest : "
					+ autoflightlistCarrierWithDest + ", autoflightlistCarrierFltWithDest : "
					+ autoflightlistCarrierFltWithDest);

		}
		setAutoCompleteFlagForExport(flightInfo, autoflightlistCarrierWithDest, autoflightlistCarrierFltWithDest,
				autoflightlistCarrier, autoflightlistCarrierWithflt);
	}

	private void setAutoCompleteFlagForExport(OperationalFlightInfo flightInfo,
			List<OperationalFlightAuto> autoflightlistCarrierWithDest,
			List<OperationalFlightAuto> autoflightlistCarrierFltWithDest,
			List<OperationalFlightAuto> autoflightlistCarrier,
			List<OperationalFlightAuto> autoflightlistCarrierWithflt) {
		if (autoflightlistCarrier != null && !autoflightlistCarrier.isEmpty()) {
			flightInfo.setFlightAutoCompleteFlag(ValidationConstant.ONE);
		} else if (autoflightlistCarrierWithflt != null && !autoflightlistCarrierWithflt.isEmpty()) {
			flightInfo.setFlightAutoCompleteFlag(ValidationConstant.ONE);
		} else if (autoflightlistCarrierWithDest != null && !autoflightlistCarrierWithDest.isEmpty()) {
			flightInfo.setFlightAutoCompleteFlag(ValidationConstant.ONE);
		} else if (autoflightlistCarrierFltWithDest != null && !autoflightlistCarrierFltWithDest.isEmpty()) {
			flightInfo.setFlightAutoCompleteFlag(ValidationConstant.ONE);
		}
	}

}
