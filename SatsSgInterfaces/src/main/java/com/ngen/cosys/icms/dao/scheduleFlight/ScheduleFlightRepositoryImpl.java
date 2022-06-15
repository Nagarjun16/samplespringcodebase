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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.icms.dao.AbstractBaseDAO;
import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightSegmentInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlights;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;


@Repository
public class ScheduleFlightRepositoryImpl extends AbstractBaseDAO implements ScheduleFlightRepository{
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleFlightRepositoryImpl.class);
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionSSM;
	
	/**
	 * validation for airport existance
	 * @param queryMap
	 * @return
	 */
	@Override
	public Integer validationForAirportExistance(Map<String, String> obj) {
		return fetchObject("checkForAirportExistance", obj, sqlSessionSSM);
	}
	
	/**
	 * validation for air craft type
	 * @param queryMap
	 * @return
	 */
	@Override
	public Integer validationForAirCraftType(Map<String, String> queryMap) {
		return fetchObject("checkAirCraftType", queryMap, sqlSessionSSM);
	}
	
	/**
	 * validation for overlap schedule
	 * @param flight
	 * @return
	 */
	@Override
	public List<ScheduleFlightInfo> checkOverlapSchedule(ScheduleFlightInfo flight) {
		return fetchList("checkOverlapSchedule", flight, sqlSessionSSM);
	}
	
	/**
	 * validate record already exist
	 * @param flight
	 * @return
	 */
	@Override
	public BigInteger checkBeforeInsertion(ScheduleFlightInfo flight) {
		return fetchObject("checkBeforeInsertion", flight, sqlSessionSSM);
	}
	
	/**
	 * insert records in flight schedule periods
	 * @param flight
	 */
	@Override
	public void insertInFlightSchedulePeriodsTable(ScheduleFlightInfo flight) {
		LOGGER.info("Method Start ScheduleFlightRepositoryImpl-> insertInFlightSchedulePeriodsTable() ->flight"+flight.toString());
		insertData("insertFltOperativeFlightSchedulePeriods", flight, sqlSessionSSM);
	}
	
	/**
	 * validate service type exist or not
	 * @param serviceType
	 * @return
	 */
	@Override
	public boolean validationForServiceType(String serviceType) {
		return this.fetchObject("sqlCheckFlightTypeSSM", serviceType,sqlSessionSSM);
	}
	
	/**
	 * Insert schedule flight
	 * @param flight
	 * @return
	 */
	@Override
	public void insertInScheduleFlightTables(ScheduleFlightInfo flight){
		try {
			LOGGER.info("Method Start ScheduleFlightRepositoryImpl-> insertInScheduleFlightTables() ->flight"+flight.toString());
			List<ScheduleFlightSegmentInfo> flightSegment = flight.getScheduleFlightSegmentList();
			
			//get schedule record count
			
			insertData("insertFltOperativeFlightSchedules", flight, sqlSessionSSM);
			flight.getScheduleFlightLegList().forEach(e1 -> e1.setFlightScheduleId(flight.getFlightScheduleId()));
			insertList("insertFltOperativeFlightSchedulelegs", flight.getScheduleFlightLegList(), sqlSessionSSM);
			flightSegment.forEach(t->t.setFlightScheduleId(flight.getFlightScheduleId()));
			insertData("insertFltOperativeFlightScheduleSegments", flightSegment, sqlSessionSSM);
		
			if (flight.getFlightScheduleRemark() != null) {
				List<ScheduleFlightInfo> remarkList=setFlightSchRemark(flight);
				insertList("insertRemarksInFactInSchedule", remarkList, sqlSessionSSM);
			}
			
		}catch(MessageProcessingException e) {
			LOGGER.info("Method End ScheduleFlightRepositoryImpl-> insertInScheduleFlightTables()");
			throw e;
		}
		
	}

	private List<ScheduleFlightInfo> setFlightSchRemark(ScheduleFlightInfo flightInfo) {
		List<ScheduleFlightInfo> remarkList = new ArrayList<ScheduleFlightInfo>();
		String remarks = flightInfo.getFlightScheduleRemark();
		if (remarks.length() < ValidationConstant.SCHEDULE_FLIGHT_REMARK_LENGTH) {
			if (remarks.length() > ValidationConstant.CHAR_LENGTH) {
				for (int i = 0; remarks.length() > ValidationConstant.CHAR_LENGTH; i++) {
					ScheduleFlightInfo flightRemark = new ScheduleFlightInfo();
					flightRemark.setFlightScheduleId(flightInfo.getFlightScheduleId());
					flightRemark.setFlightScheduleRemark(remarks.substring(0, ValidationConstant.SUBSTRING_CHAR_LENGTH));
					remarkList.add(flightRemark);
					remarks = remarks.substring((i + 1) + ValidationConstant.SUBSTRING_CHAR_LENGTH, remarks.length());
				}
			} if (remarks.length() < ValidationConstant.CHAR_LENGTH) {
				ScheduleFlightInfo flightSchRemark = new ScheduleFlightInfo();
				flightSchRemark.setFlightScheduleId(flightInfo.getFlightScheduleId());
				flightSchRemark.setFlightScheduleRemark(remarks);
				remarkList.add(flightSchRemark);
			}
		} else {
			throw new MessageParseException("Schedule Remark should not exceed 200 characters");
		}
		return remarkList;
	}

	/**
	 * Delete schedule flight.
	 *
	 * @param flight
	 * @return the integer
	 */
	@Override
	public boolean deleteFromScheduleFlightTables(ScheduleFlightInfo flight) {
		LOGGER.info("Method Start ScheduleFlightRepositoryImpl-> deleteFromScheduleFlightTables() ->flight "+flight.toString());
		try {
			int isFactExist = fetchObject("checkForScheduleFactExistance", flight.getFlightScheduleId(), sqlSessionSSM);
			if(isFactExist>0) {
				deleteData("deleteFltOperativeScheduleFacts",flight,sqlSessionSSM);
			}
			int segCount = deleteData("deleteFltOperativeFlightScheduleSegments", flight, sqlSessionSSM);
			int legCount = deleteData("deleteFltOperativeFlightScheduleLegs", flight, sqlSessionSSM);
			int scheduleCount = deleteData("deleteFltOperativeFlightSchedules", flight, sqlSessionSSM);
			int periodCount = deleteData("deleteFltOperativeFlightSchedulePeriods", flight, sqlSessionSSM);
			
			LOGGER.info("Method ScheduleFlightRepositoryImpl-> deleteFromScheduleFlightTables() ->segCount "+segCount+" legCount "+legCount+" scheduleCount "+scheduleCount+" periodCount "+periodCount);
			if(segCount>0 && legCount>0 && scheduleCount>0 && periodCount>0) return true;
			else return false;
		}catch(MessageProcessingException e) {
			e.printStackTrace();
			LOGGER.info("Method Start ScheduleFlightRepositoryImpl-> deleteFromScheduleFlightTables() -> Error while deleting flight schedule records"+e.getMessage());
			throw e;
		}
	}
	/**
	 * check whether carrier code exist or not
	 * @param carrierCode
	 * @return
	 */
	@Override
	public Integer validationForCarrierCode(String carrierCode) {
		return fetchObject("checkForCarrierCode", carrierCode, sqlSessionSSM);
	}
	/**
	 * get schedule record count
	 * @param flight
	 * @return
	 */
	@Override
	public int getScheduleRecordCount(BigInteger flightSchedulePeriodId) {	
		return fetchObject("getCountOfScheduleTableRecords", flightSchedulePeriodId, sqlSessionSSM);
	}

	@Override
	public void updateFrequencyInExistingScheduleRecord(ScheduleFlightInfo flight) {
		updateData("updateFrequencyInScheduleTable", flight, sqlSessionSSM);
		
	}

	@Override
	public String getFlightType(String serviceType) {
		return fetchObject("getFlightType",serviceType,sqlSessionSSM);
	}

	@Override
	public void insertFlightSchedule(ScheduleFlights scheduleFlight) {
		 insertData("insertFlightSchedule", scheduleFlight, sqlSessionSSM);
	}
}
