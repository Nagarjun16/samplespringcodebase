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
package com.ngen.cosys.icms.dao.scheduleFlight;

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
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlights;


@Service
public class ScheduleFlightDaoImpl implements ScheduleFlightDao {

	@Autowired
	private ScheduleFlightRepository scheduleRepository;
	
	/**
	 * validation for airport existance
	 * @param queryMap
	 * @return
	 */
	@Override
	public Integer validationForAirportExistance(Map<String, String> queryMap) {
		return this.scheduleRepository.validationForAirportExistance(queryMap);
	}
	
	/**
	 * validation for overlap schedule
	 * @param queryMap
	 * @return
	 */
	@Override
	public Integer validationForAirCraftType(Map<String, String> queryMap) {
		return this.scheduleRepository.validationForAirCraftType(queryMap); 
	}
	
	/**
	 * validation for overlap schedule
	 * @param flight
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<ScheduleFlightInfo> checkOverlapSchedule(ScheduleFlightInfo flight) {
		return this.scheduleRepository.checkOverlapSchedule(flight);
	}
	
	/**
	 * validate record already exist
	 * @param flight
	 * @return
	 */
	@Override
	public BigInteger checkBeforeInsertion(ScheduleFlightInfo flight) {
		return this.scheduleRepository.checkBeforeInsertion(flight);
	}
	
	/**
	 * insert records in flight schedule periods
	 * @param flight
	 */
	@Override
	public void insertInFlightSchedulePeriodsTable(ScheduleFlightInfo flight) {
		this.scheduleRepository.insertInFlightSchedulePeriodsTable(flight);
		
	}
	
	/**
	 * validate service type exist or not
	 * @param serviceType
	 * @return
	 */
	@Override
	public boolean validationForServiceType(String serviceType) {
		return this.scheduleRepository.validationForServiceType(serviceType);
	}
	
	/**
	 * Insert schedule flight.
	 * @param flight
	 */
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT , eventName =  NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	public void insertInScheduleFlightTables(ScheduleFlightInfo flight) {
		BigInteger periodId = this.checkBeforeInsertion(flight);
		if (periodId != null && periodId.intValue() > 0) {
			flight.setFlightSchedulePeriodId(periodId);
			//get schedule record count from schedule table
			int count = this.scheduleRepository.getScheduleRecordCount(flight.getFlightSchedulePeriodId());
			flight.setScheduleSequenceNo(count+1);
			this.scheduleRepository.insertInScheduleFlightTables(flight);
		} else {
			flight.setScheduleSequenceNo(1);
			this.insertInFlightSchedulePeriodsTable(flight);
			this.scheduleRepository.insertInScheduleFlightTables(flight);
		}
		
	}
	
	/**
	 * Delete schedule flight
	 * @param flight
	 * @return
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT , eventName =  NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	@Override
	public boolean deleteFromScheduleFlightTables(ScheduleFlightInfo flight) {
		return this.scheduleRepository.deleteFromScheduleFlightTables(flight);
	}
	
	/**
	 * check whether carrier code exist or not
	 * @param carrierCode
	 * @return
	 */
	@Override
	public Integer validationForCarrierCode(String carrierCode) {
		return this.scheduleRepository.validationForCarrierCode(carrierCode);
	}
	/**
	 *update frequency
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT , eventName =  NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE)
	@Override
	public void updateFrequencyInExistingScheduleRecord(ScheduleFlightInfo flight) {
		this.scheduleRepository.updateFrequencyInExistingScheduleRecord(flight);
		
	}
	/**
	 * get Flight type for given service type
	 * @param serviceType
	 * @return
	 */
	@Override
	public String getFlightType(String serviceType) {
		return this.scheduleRepository.getFlightType(serviceType);
	}

	/**
	 * Insert Flight Schedule to summary table
	 * @param scheduleFlight
	 * @return
	 */
	@Override
	public void insertFlightSchedule(ScheduleFlights scheduleFlight) {
		 this.scheduleRepository.insertFlightSchedule(scheduleFlight);
	}
	 
}
