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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightAuto;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightHandlingArea;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
import com.ngen.cosys.icms.dao.AbstractBaseDAO;
import com.ngen.cosys.icms.exception.MessageParseException;

@Repository
public class OperationalFlightRepositoryImp extends AbstractBaseDAO implements OperationalFlightRepository {

	@Autowired
	@Qualifier("sqlSessionTemplate")

	private SqlSession sqlSessionASM;

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationalFlightRepositoryImp.class);

	/**
	 * insert Flight/Legs/Segments Details
	 */
	@Override
	public void insertFltOperationalFlight(OperationalFlightInfo flightInfo) {
		LOGGER.info("Method Start OperationalFlightRepositoryImp-> insertFltOperationalFlight()" + flightInfo);
		try {
			insertData("insertFltOperationalFlight", flightInfo, sqlSessionASM);
			flightInfo.getFlightLegInfo().forEach(e1 -> e1.setFlightId(flightInfo.getFlightId()));
			if (flightInfo.getFlightLegInfo().size() > 0) {
				insertData("insertFltOperationalFlightLegs", flightInfo.getFlightLegInfo(), sqlSessionASM);
			}
			flightInfo.getFlightSegmentInfo().forEach(t -> t.setFlightId(flightInfo.getFlightId()));
			if (flightInfo.getFlightSegmentInfo().size() > 0) {
				insertData("insertFltOperationalFlightSegments", flightInfo.getFlightSegmentInfo(), sqlSessionASM);
			}
			// insertData("insertFltOperativeFlightEvents", flightInfo, sqlSessionSSM);
			if (flightInfo.getFlightRemarks() != null) {
				List<OperationalFlightInfo> flightRemarks = setFlightRemark(flightInfo);
				insertData("insertFlightRemark", flightRemarks, sqlSessionASM);
			}

			if (flightInfo.getLoadingPoint().equals(ValidationConstant.PORT_SIN)) {
				insertData("insertinExpFlightEventsTable", flightInfo, sqlSessionASM);
				flightInfo.setSiType(ValidationConstant.IMPORT_FLIGHT);
			} else {
				insertData("insertInImpFlightEventsTable", flightInfo, sqlSessionASM);
				flightInfo.setSiType(ValidationConstant.EXPORT_FLIGHT);
			}
			insertData("insertIntoCustomsFlight", flightInfo, sqlSessionASM);
			// insertData("insertFltOperativeFlightExceptions", flightInfo, sqlSessionASM);

			List<String> boardPointList = flightInfo.getFlightLegInfo().stream()
					.map(leg -> leg.getLegOrigin()).collect(Collectors.toList());
			List<String> offPointList = flightInfo.getFlightLegInfo().stream()
					.map(leg -> leg.getLegDestination()).collect(Collectors.toList());
			if (boardPointList.contains(flightInfo.getTenantAirport())
					&& offPointList.contains(flightInfo.getTenantAirport())) {
				flightInfo.setFlightType("BOTH");
			} else if (boardPointList.contains(flightInfo.getTenantAirport())) {
				flightInfo.setFlightType("EXPORT");

			} else if (offPointList.contains(flightInfo.getTenantAirport())) {
				flightInfo.setFlightType("IMPORT");
			}
			this.insertInFlightHandlingArea(flightInfo);
		} catch (MessageProcessingException e) {
			LOGGER.error("Method End OperationalFlightRepositoryImp-> insertFltOperationalFlight()");
			throw e;
		}

	}

	private List<OperationalFlightInfo> setFlightRemark(OperationalFlightInfo flightInfo) {
		List<OperationalFlightInfo> remarkList = new ArrayList<OperationalFlightInfo>();
		String remarks = flightInfo.getFlightRemarks();
		if (remarks.length() < ValidationConstant.FLIGHT_REMARK_LENGTH) {
			if (remarks.length() > ValidationConstant.CHAR_LENGTH) {
				for (int i = 0; remarks.length() > ValidationConstant.CHAR_LENGTH; i++) {
					OperationalFlightInfo flightRemark = new OperationalFlightInfo();
					flightRemark.setFlightId(flightInfo.getFlightId());
					flightRemark.setFlightRemarks(remarks.substring(0, ValidationConstant.SUBSTRING_CHAR_LENGTH));
					remarkList.add(flightRemark);
					remarks = remarks.substring((i + 1) + ValidationConstant.SUBSTRING_CHAR_LENGTH, remarks.length());
				}
			}
			if (remarks.length() < ValidationConstant.CHAR_LENGTH) {
				OperationalFlightInfo flightRemark = new OperationalFlightInfo();
				flightRemark.setFlightId(flightInfo.getFlightId());
				flightRemark.setFlightRemarks(remarks);
				remarkList.add(flightRemark);
			}
		} else
		{
			throw new MessageParseException("Flight Remark should not exceed 300 characters");
		}
		return remarkList;
	}

	private void insertInFlightHandlingArea(OperationalFlightInfo flightInfo) {
		// getAirCraftBodyType
		String airCraftBodyType = fetchObject("sqlGetAircraftBodyType", flightInfo, sqlSessionASM);
		if (!StringUtils.isEmpty(airCraftBodyType)) {
			flightInfo.setAircraftBodyType(airCraftBodyType);
		} else {
			flightInfo.setAircraftBodyType("**");
		}
		// Derive the handling terminals for the flight configuration
		List<OperationalFlightHandlingArea> defaultHandligArea = driveHandlingArea(flightInfo);
		// Insert the handling terminals
		if (!CollectionUtils.isEmpty(defaultHandligArea)) {
			defaultHandligArea.forEach(t -> {
				t.setFlightId(flightInfo.getFlightId());
			});
		}
		if (!CollectionUtils.isEmpty(defaultHandligArea)) {
			insertData("insertFlightHandlingArea", defaultHandligArea, sqlSessionASM);
		}
	}

	private List<OperationalFlightHandlingArea> driveHandlingArea(OperationalFlightInfo flightInfo) {
		List<OperationalFlightHandlingArea> derivedHandlingArea = super.fetchList("sqlDeriveHandlingAreaForFlight",
				flightInfo, sqlSessionASM);
		List<String> haString = new ArrayList<>(); //
		List<OperationalFlightHandlingArea> defaultHandligArea = new ArrayList<>();
		int temp = 0;
		if (!CollectionUtils.isEmpty(derivedHandlingArea)) {
			for (OperationalFlightHandlingArea b : derivedHandlingArea) {
				haString.add(b.getTerminalCode());
				defaultHandligArea.add(b);
				temp++;
			}
			flightInfo.setHandlingArea(haString);
		} else if (temp == 0) {
			defaultHandligArea = super.fetchList("sqlDeriveAllTerminalForFlight", flightInfo, sqlSessionASM);
			for (OperationalFlightHandlingArea b : defaultHandligArea) {
				haString.add(b.getTerminalCode());
			}
			flightInfo.setHandlingArea(haString);
		}
		return defaultHandligArea;
	}

	/**
	 * insert Flight Legs Details
	 */
	@Override
	public void insertFltOperationalFlightLeg(OperationalFlightInfo flightInfo) {
		if (flightInfo.getFlightLegInfo().size() > 0) {
			insertData("insertFltOperationalFlightLegs", flightInfo.getFlightLegInfo(), sqlSessionASM);
		}
	}

	/**
	 * insert Flight Segments Details
	 */
	@Override
	public void insertFltOperationalFlightSegment(OperationalFlightInfo flightInfo) {
		if (flightInfo.getFlightSegmentInfo().size() > 0) {
			insertData("insertFltOperationalFlightSegments", flightInfo.getFlightSegmentInfo(), sqlSessionASM);
		}
	}

	/**
	 * update Flight Details
	 */
	@Override
	public int updateFltOperativeFlight(OperationalFlightInfo flightInfo) {
		return updateData("updateFltOperativeFlight", flightInfo, sqlSessionASM);
	}

	/**
	 * update Flight Remark
	 */
	@Override
	public void updateFlightRemark(OperationalFlightInfo flightInfo) {
		deleteData("deleteFlightRemarks", flightInfo, sqlSessionASM);
		List<OperationalFlightInfo> flightRemarks = setFlightRemark(flightInfo);
		insertList("insertFlightRemark", flightRemarks, sqlSessionASM);
	}

	/**
	 * Cancel Flight
	 */
	@Override
	public int cancelFltOperationalFlight(OperationalFlightInfo flightInfo) {
		updateData("cancleFlightInCustoms", flightInfo, sqlSessionASM);
		return updateData("cancelFltOperationalFlight", flightInfo, sqlSessionASM);
	}

	/**
	 * validate service Type
	 */
	@Override
	public int validationForServiceType(Map<String, String> queryMap) {
		return fetchObject("validationForServiceType", queryMap, sqlSessionASM);
	}

	/**
	 * Fetch flightId.
	 */
	@Override
	public BigInteger fetchFlightId(OperationalFlightInfo flightInfo) {
		return fetchObject("fetchFlightId", flightInfo, sqlSessionASM);
	}

	/**
	 * validate Active Arrival Flight Overlapping
	 */
	@Override
	public int validationForActiveArrivalFlightOverlapping(Map<String, String> queryMap) {
		return fetchObject("validationForActiveArrivalFlightOverlapping", queryMap, sqlSessionASM);
	}

	/**
	 * validate Active Departure Flight Overlapping
	 */
	@Override
	public int validationForActiveDepartureFlightOverlapping(Map<String, String> queryMap) {
		return fetchObject("validationForActiveDepartureFlightOverlapping", queryMap, sqlSessionASM);
	}

	/**
	 * validate Active flight
	 */
	@Override
	public int validationForActiveFlight(OperationalFlightInfo flightInfo) {
		return fetchObject("validationForActiveFlight", flightInfo, sqlSessionASM);
	}

	/**
	 * validate Cancel Flight
	 */
	@Override
	public int validationForCancelFlight(OperationalFlightInfo flightInfo) {
		return fetchObject("validationForCancelFlight", flightInfo, sqlSessionASM);
	}

	/**
	 * Delete Cancel Flight
	 */
	@Override
	public void deleteExistingCancelFlight(OperationalFlightInfo flightInfo) {
		flightInfo.getFlightLegInfo().forEach(e1 -> e1.setFlightId(flightInfo.getFlightId()));
		flightInfo.getFlightSegmentInfo().forEach(t -> t.setFlightId(flightInfo.getFlightId()));
		deleteData("deleteExistingFlight", flightInfo, sqlSessionASM);
	}

	/**
	 * Delete Leg Details
	 */
	@Override
	public void deleteExistingFlightLegs(OperationalFlightLegInfo legInfo) {
		if (legInfo != null) {
			deleteData("deleteExistingFlightLegs", legInfo, sqlSessionASM);
		}
	}

	/**
	 * Delete Segments Details
	 */
	@Override
	public void deleteExistingFlightSegments(OperationalFlightSegmentInfo segmentInfo) {
		if (segmentInfo != null) {
			deleteData("deleteExistingFlightSegments", segmentInfo, sqlSessionASM);
		}

	}

	/**
	 * validate Flight Booking
	 */
	@Override
	public int validateFlightBooking(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateFlightBooking", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate Assigned ULD
	 */
	@Override
	public int validateAssignedULD(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateAssignedULD", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate Arrival Manifest
	 */
	@Override
	public int validateArivalManifest(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateArivalManifest", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate EXP flight departed or not
	 */
	@Override
	public String validateEXPFlightDeparted(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateEXPFlightDeparted", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate EXP flight completed or not
	 */
	@Override
	public String validateIMPFlightCompleted(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateIMPFlightCompleted", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate Departure Manifest
	 */
	@Override
	public int validateDepartureManifest(OperationalFlightSegmentInfo segmentInfo) {
		return fetchObject("validateDepartureManifest", segmentInfo, sqlSessionASM);
	}

	/**
	 * Validate Leg Overlapping
	 */
	@Override
	public int checkLegOverlapping(OperationalFlightInfo flightInfo) {
		return fetchObject("checkLegOverlapping", flightInfo, sqlSessionASM);
	}

	/**
	 * Fetch Aircraft Type
	 */
	@Override
	public String fetchAircraftType(OperationalFlightInfo flightInfo) {
		return fetchObject("fetchAircraftType", flightInfo, sqlSessionASM);
	}

	/**
	 * Validate FFM Info
	 */
	@Override
	public int validateFFMInfo(OperationalFlightSegmentInfo flightSegInfo) {
		return fetchObject("validateFFMInfo", flightSegInfo, sqlSessionASM);
	}

	@Override
	public OperationalFlightLegInfo getLegDetails(OperationalFlightLegInfo legInfo) {
		return fetchObject("getLegDetails", legInfo, sqlSessionASM);
	}

	@Override
	public OperationalFlightLegInfo getSegmentDetails(OperationalFlightLegInfo legInfo) {
		return fetchObject("getSegmentDetails", legInfo, sqlSessionASM);
	}

	/**
	 * Get ServiceType on the basis of flightType
	 */
	@Override
	public String getServiceType(String flightType) {
		return fetchObject("getServiceType", flightType, sqlSessionASM);
	}

	@Override
	public void updateAutoComFlag(OperationalFlightInfo flightInfo) {
		updateData("updateAutoComFlag", flightInfo, sqlSessionASM);

	}

	@Override
	public List<OperationalFlightAuto> checkForAutoCarrierWithDest(OperationalFlightLegInfo leg) {
		return fetchList("checkForAutoCarrierWithDest", leg, sqlSessionASM);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoFlightWithDest(OperationalFlightLegInfo leg) {
		return fetchList("checkForAutoFlightWithDest", leg, sqlSessionASM);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoFlightWithCarrier(OperationalFlightLegInfo leg) {
		return fetchList("checkForAutoFlightWithCarrier", leg, sqlSessionASM);
	}

	@Override
	public List<OperationalFlightAuto> checkForAutoCarrier(OperationalFlightLegInfo leg) {
		return fetchList("checkForAutoCarrier", leg, sqlSessionASM);
	}

	@Override
	public String checkLegOrder(OperationalFlightLegInfo leg) {
		return fetchObject("checkLegOrder", leg, sqlSessionASM);
	}

	@Override
	public OperationalFlightLegInfo getWorkedOnLegInfo(OperationalFlightLegInfo legInfo) {
		return fetchObject("getWorkedOnLegInfo", legInfo, sqlSessionASM);
	}

	@Override
	public void UpdateTime(OperationalFlightLegInfo legInfo) {
		updateData("UpdateTime", legInfo, sqlSessionASM);
	}

	@Override
	public String validateIMPFlightDocumentVerified(OperationalFlightSegmentInfo operationalFlightSegmentInfo) {
		return fetchObject("validateIMPFlightDocumentVerified", operationalFlightSegmentInfo, sqlSessionASM);
	}

}
