/*******************************************************************************
 *  Copyright (c) 2021 Coforge Technologies PVT LTD
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
package com.ngen.cosys.icms.dao.operationFlight;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.icms.dao.operationFlight.OperationalFlightRepository;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightAuto;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;

@Service
public class OperationalFlightDaoImpl implements OperationalFlightDao {

	@Autowired
	private OperationalFlightRepository flightRepository;

	/**
	 * insert Flight/Legs/Segments Details
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	public void insertFltOperationalFlight(OperationalFlightInfo flightInfo) {
		this.flightRepository.insertFltOperationalFlight(flightInfo);
	}

	/**
	 * insert Flight Legs Details
	 */
	public void insertFltOperationalFlightLeg(OperationalFlightInfo flightInfo) {
		this.flightRepository.insertFltOperationalFlightLeg(flightInfo);
	}

	/**
	 * insert Flight Segments Details
	 */
	public void insertFltOperationalFlightSegment(OperationalFlightInfo flightInfo) {
		this.flightRepository.insertFltOperationalFlightSegment(flightInfo);
	}

	/**
	 * Validate Service Type
	 */
	@Override
	public int validationForServiceType(Map<String, String> queryMap) {
		return this.flightRepository.validationForServiceType(queryMap);
	}

	
	/**
	 * validate  Active Arrival Flight Overlapping
	 */
	@Override
	public int validationForActiveArrivalFlightOverlapping(Map<String, String> queryMap) {
		return this.flightRepository.validationForActiveArrivalFlightOverlapping(queryMap);
	}
	
	/**
	 * validate  Active Departure Flight Overlapping
	 */
	@Override
	public int validationForActiveDepartureFlightOverlapping(Map<String, String> queryMap) {
		return this.flightRepository.validationForActiveDepartureFlightOverlapping(queryMap);
	}


	/**
	 * Validate Active Flight
	 */
	@Override
	public int validationForActiveFlight(OperationalFlightInfo flightInfo) {
		return this.flightRepository.validationForActiveFlight(flightInfo);
	}
	
	/**
	 * Validate Cancel Flight
	 */
	@Override
	public int validationForCancelFlight(OperationalFlightInfo flightInfo) {
		return this.flightRepository.validationForCancelFlight(flightInfo);
	}

	/**
	 * Delete Existing Cancel flight
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	@Override
	public void deleteExistingCancelFlight(OperationalFlightInfo flightInfo) {
		this.flightRepository.deleteExistingCancelFlight(flightInfo);
	}

	/**
	 * update flight details
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	@Override
	public int updateFltOperativeFlight(OperationalFlightInfo flightInfo) {
		return this.flightRepository.updateFltOperativeFlight(flightInfo);
	}

	/**
	 * cancel Flight
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	public int cancelFltOperationalFlight(OperationalFlightInfo flightInfo) {
		return this.flightRepository.cancelFltOperationalFlight(flightInfo);
	}

	/**
	 * validate Flight booking
	 */
	@Override
	public int validateFlightBooking(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateFlightBooking(segmentInfo);
	}

	/**
	 * validate Assigned ULD
	 */
	@Override
	public int validateAssignedULD(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateAssignedULD(segmentInfo);
	}

	/**
	 * validate Arrival Manifest
	 */
	@Override
	public int validateArivalManifest(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateArivalManifest(segmentInfo);
	}
	
	/**
	 * Validate EXP flight departed or not
	 */
	@Override
	public String validateEXPFlightDeparted(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateEXPFlightDeparted(segmentInfo);
	}

	/**
	 * Validate IMP flight completed or not
	 */
	@Override
	public String validateIMPFlightCompleted(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateIMPFlightCompleted(segmentInfo);
	}

	/**
	 * Validate Departure manifest
	 */
	@Override
	public int validateDepartureManifest(OperationalFlightSegmentInfo segmentInfo) {
		return this.flightRepository.validateDepartureManifest(segmentInfo);
	}


	/**
	 * fetch flight Id
	 */
	@Override
	public BigInteger fetchFlightId(OperationalFlightInfo flightInfo) {
		return this.flightRepository.fetchFlightId(flightInfo);
	}

	/**
	 * update Flight Remark
	 */
	@Override
	public void updateFlightRemark(OperationalFlightInfo flightInfo) {
		this.flightRepository.updateFlightRemark(flightInfo);
	}

	/**
	 * validate leg overlapping
	 */
	@Override
	public int checkLegOverlapping(OperationalFlightInfo flightInfo) {
		return this.flightRepository.checkLegOverlapping(flightInfo);
	}

	/**
	 * validate aircraftType
	 */
	@Override
	public String fetchAircraftType(OperationalFlightInfo flightInfo) {
		return this.flightRepository.fetchAircraftType(flightInfo);
	}

	/**
	 * Delete Existing flight Legs
	 */
	@Override
	public void deleteExistingFlightLegs(OperationalFlightLegInfo legInfo) {
		this.flightRepository.deleteExistingFlightLegs(legInfo);
	}

	/**
	 * Delete Existing flight Segments
	 */
	@Override
	public void deleteExistingFlightSegments(OperationalFlightSegmentInfo segmentInfo) {
		this.flightRepository.deleteExistingFlightSegments(segmentInfo);
	}

	/**
	 * Validate FFM INfo
	 */
	@Override
	public int validateFFMInfo(OperationalFlightSegmentInfo flightSegInfo) {
		return this.flightRepository.validateFFMInfo(flightSegInfo);
	}

	/**
	 * Get existing Leg details
	 */
	@Override
	public OperationalFlightLegInfo getLegDetails(OperationalFlightLegInfo legInfo) {
		return this.flightRepository.getLegDetails(legInfo);
	}

	/**
	 * Get existing Segment details
	 */
	@Override
	public OperationalFlightLegInfo getSegmentDetails(OperationalFlightLegInfo legInfo) {
		return this.flightRepository.getSegmentDetails(legInfo);
	}

	/**
	 * Get ServiceType on the basis of flightType
	 */
	@Override
	public String getServiceType(String queryMap) {
		return this.flightRepository.getServiceType(queryMap);
	}

	@Override
	public void updateAutoComFlag(OperationalFlightInfo flightInfo) {
		this.flightRepository.updateAutoComFlag(flightInfo);
		
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoCarrierWithDest(OperationalFlightLegInfo leg) {
		return this.flightRepository.checkForAutoCarrierWithDest(leg);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoFlightWithDest(OperationalFlightLegInfo leg) {
		return this.flightRepository.checkForAutoFlightWithDest(leg);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoFlightWithCarrier(OperationalFlightLegInfo leg) {
		return this.flightRepository.checkForAutoFlightWithCarrier(leg);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoCarrier(OperationalFlightLegInfo leg) {
		return this.flightRepository.checkForAutoCarrier(leg);
	}

	@Override
	public String checkLegOrder(OperationalFlightLegInfo operationalFlightLegInfo) {
		return this.flightRepository.checkLegOrder(operationalFlightLegInfo);
	}

	@Override
	public OperationalFlightLegInfo getWorkedOnLegInfo(OperationalFlightLegInfo legInfo) {
		return this.flightRepository.getWorkedOnLegInfo(legInfo);
	}

	@Override
	public void UpdateTime(OperationalFlightLegInfo legInfo) {
		this.flightRepository.UpdateTime(legInfo);
	}

	@Override
	public String validateIMPFlightDocumentVerified(OperationalFlightSegmentInfo operationalFlightSegmentInfo) {
		return this.flightRepository.validateIMPFlightDocumentVerified(operationalFlightSegmentInfo);
	}

}
