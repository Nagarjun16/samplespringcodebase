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

import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightAuto;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;

public interface OperationalFlightRepository {

	void insertFltOperationalFlight(OperationalFlightInfo flightInfo);

	int validationForServiceType(Map<String, String> queryMap);

	int validationForActiveArrivalFlightOverlapping(Map<String, String> queryMap);
	
	int validationForActiveDepartureFlightOverlapping(Map<String, String> queryMap);
	
	int validationForActiveFlight(OperationalFlightInfo flightInfo);
	
	int validationForCancelFlight(OperationalFlightInfo flightInfo);

	void deleteExistingCancelFlight(OperationalFlightInfo flightInfo);

	int updateFltOperativeFlight(OperationalFlightInfo flightInfo);

	int cancelFltOperationalFlight(OperationalFlightInfo flightInfo);

	int validateFlightBooking(OperationalFlightSegmentInfo segmentInfo);

	int validateAssignedULD(OperationalFlightSegmentInfo segmentInfo);

	int validateArivalManifest(OperationalFlightSegmentInfo segmentInfo);
	
	String validateEXPFlightDeparted(OperationalFlightSegmentInfo segmentInfo);

	String validateIMPFlightCompleted(OperationalFlightSegmentInfo segmentInfo);

	int validateDepartureManifest(OperationalFlightSegmentInfo segmentInfo);

	BigInteger fetchFlightId(OperationalFlightInfo flightInfo);

	void updateFlightRemark(OperationalFlightInfo flightInfo);

	int checkLegOverlapping(OperationalFlightInfo flightInfo);

	void insertFltOperationalFlightLeg(OperationalFlightInfo flightInfo);

	void insertFltOperationalFlightSegment(OperationalFlightInfo flightInfo);

	String fetchAircraftType(OperationalFlightInfo flightInfo);

	void deleteExistingFlightLegs(OperationalFlightLegInfo legInfo);

	void deleteExistingFlightSegments(OperationalFlightSegmentInfo segmentInfo);

	int validateFFMInfo(OperationalFlightSegmentInfo flightSegInfo);

	OperationalFlightLegInfo getLegDetails(OperationalFlightLegInfo legInfo);

	OperationalFlightLegInfo getSegmentDetails(OperationalFlightLegInfo legInfo);

	String getServiceType(String queryMap);

	void updateAutoComFlag(OperationalFlightInfo flightInfo);

	List<OperationalFlightAuto> checkForAutoCarrierWithDest(OperationalFlightLegInfo leg);

	List<OperationalFlightAuto> checkForAutoFlightWithDest(OperationalFlightLegInfo leg);

	List<OperationalFlightAuto> checkForAutoFlightWithCarrier(OperationalFlightLegInfo leg);

	List<OperationalFlightAuto> checkForAutoCarrier(OperationalFlightLegInfo leg);

	String checkLegOrder(OperationalFlightLegInfo operationalFlightLegInfo);

	OperationalFlightLegInfo getWorkedOnLegInfo(OperationalFlightLegInfo legInfo);

	void UpdateTime(OperationalFlightLegInfo legInfo);

	String validateIMPFlightDocumentVerified(OperationalFlightSegmentInfo operationalFlightSegmentInfo);
	

}
