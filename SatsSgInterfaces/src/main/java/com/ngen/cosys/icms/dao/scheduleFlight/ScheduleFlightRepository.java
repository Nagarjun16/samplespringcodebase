/*******************************************************************************
 * Copyright (c) 2021 Coforge PVT LTD
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
package com.ngen.cosys.icms.dao.scheduleFlight;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlights;


public interface ScheduleFlightRepository {
	/**
	 * validation for airport existance
	 * @param queryMap
	 * @return
	 */
	public Integer validationForAirportExistance(Map<String, String> queryMap); 
	/**
	 * validation for air craft type
	 * @param queryMap
	 * @return
	 */
	public Integer validationForAirCraftType(Map<String, String> queryMap); 
	/**
	 * validation for overlap schedule
	 * @param flight
	 * @return
	 */
	public List<ScheduleFlightInfo> checkOverlapSchedule(ScheduleFlightInfo flight);
	/**
	 * validate record already exist
	 * @param flight
	 * @return
	 */
	public BigInteger checkBeforeInsertion(ScheduleFlightInfo flight);
	/**
	 * insert records in flight schedule periods
	 * @param flight
	 */
	public  void insertInFlightSchedulePeriodsTable(ScheduleFlightInfo flight);
	/**
	 * Insert schedule flight
	 * @param flight
	 */
	public void insertInScheduleFlightTables(ScheduleFlightInfo flight);
	/**
	 * Delete schedule flight
	 * @param flight
	 * @return
	 */
	public boolean deleteFromScheduleFlightTables(ScheduleFlightInfo flight);	
	/**
	 * validate service type exist or not
	 * @param serviceType
	 * @return
	 */
	public boolean validationForServiceType(String serviceType);
	/**
	 * validation for carrier code
	 * @param carrierCode
	 * @return
	 */
	public Integer validationForCarrierCode(String carrierCode);
	/**
	 * get schedule record count
	 * @param flight
	 * @return
	 */
	public int getScheduleRecordCount(BigInteger flightSchedulePeriodId);
	/**
	 * update frequency in schedule table
	 * @param flight
	 */
	public void updateFrequencyInExistingScheduleRecord(ScheduleFlightInfo flight);
	/**
	 * get Flight type for given service type
	 * @param serviceType
	 * @return
	 */
	public String getFlightType(String serviceType);
	/** 
	 * Insert Flight Schedule to Summary Table
	 * @param flight
	 */
	public void insertFlightSchedule(ScheduleFlights scheduleFlight);
}
