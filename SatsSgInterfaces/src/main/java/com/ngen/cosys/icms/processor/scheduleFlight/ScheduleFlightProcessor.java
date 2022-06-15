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
package com.ngen.cosys.icms.processor.scheduleFlight;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlights;
import com.ngen.cosys.icms.processor.BaseFlightProcessor;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedule;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedulePublish;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;



/**
 *This class contains business logics and validations for schedule flight messages
 */

@Component
public class ScheduleFlightProcessor extends BaseFlightProcessor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleFlightProcessor.class);
	
	@Autowired
	CommonUtil commonUtil;
	
    /**
     * process xml model to schedule flight model,validate and insert it to db
     * @param inputSchdle
     * @return
     * @throws MessageProcessingException,Exception 
     */
	@Transactional
    public ICMSResponseModel processScheduleFlightMessage(ScheduleFlightInfo scheduleFlightInfo) {
    	LOGGER.info("Method Start ScheduleFlightProcessor-> processIncomingMessage()-> FlightSchedulePublish :"+scheduleFlightInfo.toString());
    	ICMSResponseModel icmsResponse = new ICMSResponseModel();
    	try { 	
    		//Business validation
    		validateAirlineIdAndACType(scheduleFlightInfo); //aircraft validation
    		validateServiceType(scheduleFlightInfo);
    		validateCarrierCode(scheduleFlightInfo.getCarrier());
		    List<ScheduleFlightInfo> overlapInfo = validateForOverlappingSchedule(scheduleFlightInfo);//overlapping
		    //for status LIVE/NOP/TBA/MODIFIED/PUB 
		    if(ValidationConstant.STATUS_LIVE.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus()) || ValidationConstant.STATUS_NOP.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus()) 
		    		|| ValidationConstant.STATUS_TBA.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus()) || ValidationConstant.STATUS_MODIFIED.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus()) 
		    		|| ValidationConstant.STATUS_PUB.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus())) {
		    	for (ScheduleFlightInfo overlapObj : overlapInfo) {
				    this.scheduleFlightDao.deleteFromScheduleFlightTables(overlapObj); //delete record
				}		
				this.scheduleFlightDao.insertInScheduleFlightTables(scheduleFlightInfo);
				this.insertFlightSchedules(scheduleFlightInfo);
		    }
		    else if(ValidationConstant.STATUS_CAN.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus()) || ValidationConstant.STATUS_TBC.equalsIgnoreCase(scheduleFlightInfo.getFlightScheduleStatus())) {
		    	Map<String,Boolean> overlapInfoFrequency = mergeScheduleFrequencyWithSamePeriodId(overlapInfo);
			    for (ScheduleFlightInfo overlapFlight : overlapInfo) {
				    	if ((overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_MON) == scheduleFlightInfo.isFlightOnMon())
				    				&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_TUE) == scheduleFlightInfo.isFlightOnTue())
				    				&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_WED) == scheduleFlightInfo.isFlightOnWed())
									&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_THU) == scheduleFlightInfo.isFlightOnThu())
									&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_FRI) == scheduleFlightInfo.isFlightOnFri())
									&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_SAT) == scheduleFlightInfo.isFlightOnSat())
									&& (overlapInfoFrequency.get(ValidationConstant.FLIGHT_ON_SUN) == scheduleFlightInfo.isFlightOnSun()))
				    				{
				    					this.scheduleFlightDao.deleteFromScheduleFlightTables(overlapFlight);
				    					
									}
				    	else {
					    	//update the frequency in overlap record
				    		setScheduleValuesForUpdate(scheduleFlightInfo, overlapFlight);
				    		this.scheduleFlightDao.updateFrequencyInExistingScheduleRecord(overlapFlight);
				    	}
				    	 this.insertFlightSchedules(scheduleFlightInfo);
				 }
			   
		    }  
		    else {
		    	throw new MessageProcessingException("Flight Schedule status doesn't exist");
		    }
    	}
    	catch(MessageProcessingException e) {
    		LOGGER.error("Method End InboundIcmsService-> processIncomingMessage()-> Exception"+e.getMessage());
    		icmsResponse.setHttpStatus(HttpStatus.CONFLICT);
    		icmsResponse.setErrorMessage(e.getMessage());
        	return icmsResponse;
    	}
    	LOGGER.info("Method End ScheduleFlightProcessor-> processIncomingMessage()-> ScheduleFlightInfo :"+scheduleFlightInfo.toString());
    	icmsResponse.setHttpStatus(HttpStatus.OK);
        return icmsResponse;
    }
	

	/**
	 * @param overlapInfo
	 */
	private Map<String,Boolean> mergeScheduleFrequencyWithSamePeriodId(List<ScheduleFlightInfo> overlapInfo) {
		Map<String,Boolean> overlapFrequency = new HashMap<>();
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_MON, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_TUE, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_WED, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_THU, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_FRI, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_SAT, false);
		overlapFrequency.put(ValidationConstant.FLIGHT_ON_SUN, false);
		for (ScheduleFlightInfo overlap : overlapInfo) {
			if(overlap.isFlightOnMon()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_MON, true);
			if(overlap.isFlightOnTue()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_TUE, true);
			if(overlap.isFlightOnWed()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_WED, true);
			if(overlap.isFlightOnThu()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_THU, true);
			if(overlap.isFlightOnFri()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_FRI, true);
			if(overlap.isFlightOnSat()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_SAT, true);
			if(overlap.isFlightOnSun()) overlapFrequency.put(ValidationConstant.FLIGHT_ON_SUN, true);
		}
		return overlapFrequency;
	}
	
	/**
	 * @param scheduleFlightInfo
	 * @param overlapFlight
	 */
	private void setScheduleValuesForUpdate(ScheduleFlightInfo scheduleFlightInfo, ScheduleFlightInfo overlapFlight) {
		if(scheduleFlightInfo.isFlightOnMon() == overlapFlight.isFlightOnMon())overlapFlight.setFlightOnMon(false);
		if(scheduleFlightInfo.isFlightOnTue() == overlapFlight.isFlightOnTue())overlapFlight.setFlightOnTue(false);
		if(scheduleFlightInfo.isFlightOnWed() == overlapFlight.isFlightOnWed())overlapFlight.setFlightOnWed(false);
		if(scheduleFlightInfo.isFlightOnThu() == overlapFlight.isFlightOnThu())overlapFlight.setFlightOnThu(false);
		if(scheduleFlightInfo.isFlightOnFri() == overlapFlight.isFlightOnFri())overlapFlight.setFlightOnFri(false);
		if(scheduleFlightInfo.isFlightOnSat() == overlapFlight.isFlightOnSat())overlapFlight.setFlightOnSat(false);
		if(scheduleFlightInfo.isFlightOnSun() == overlapFlight.isFlightOnSun())overlapFlight.setFlightOnSun(false);
	}
    
    /**
     * This method contains business validations
     * @param flightSchedulePublish
     * @return
     * @throws MessageParseException 
     * @throws ParseException 
     * @throws MessageParsingException 
     */
    public FlightSchedulePublish businessValidations(FlightSchedulePublish flightSchedulePublish) throws MessageParseException, ParseException, MessageParsingException {
    	FlightSchedule flightSchedule = flightSchedulePublish.getObjEntity().getPublishData().getFlightschedule();
    	//XML parsing validations
    	validationForFlightNumberAndDate(flightSchedule.getFlightscheduleNumber(),flightSchedule.getFlightscheduleFromDate(),flightSchedule.getFlightscheduleToDate());
    	validateLegDetails(flightSchedule);
    	return flightSchedulePublish;
    }
   
    /**
	 * chek whether overlapping records exist or not
	 * @param scheduleFlight
	 * @return
	 */
	private List<ScheduleFlightInfo> validateForOverlappingSchedule(ScheduleFlightInfo scheduleFlight) {
		List<ScheduleFlightInfo> overlappingFlightInfo = this.scheduleFlightDao.checkOverlapSchedule(scheduleFlight);
		LOGGER.info("Method End ScheduleFlightProcessor-> validateForOverlappingSchedule()-> overlappingFlightInfo :"+overlappingFlightInfo.size());
		return overlappingFlightInfo;
	}
	
	/**
	 * Insert Schedule Flight into ICMS ScheduleFlight Table
	 * @param ScheduleFlights
	 */
	private void insertFlightSchedules(ScheduleFlightInfo scheduleFlightInfo)  {
		ScheduleFlights scheduleFlights = new ScheduleFlights();
		StringBuilder frequency = new StringBuilder();
		if (scheduleFlightInfo.isFlightOnMon()) {
			frequency.append("1");
		}
		if (scheduleFlightInfo.isFlightOnTue()) {
			frequency.append("2");
		}
		if (scheduleFlightInfo.isFlightOnWed()) {
			frequency.append("3");
		}
		if (scheduleFlightInfo.isFlightOnThu()) {
			frequency.append("4");
		}
		if (scheduleFlightInfo.isFlightOnFri()) {
			frequency.append("5");
		}
		if (scheduleFlightInfo.isFlightOnSat()) {
			frequency.append("6");
		}
		if (scheduleFlightInfo.isFlightOnSun()) {
			frequency.append("7");
		}
		scheduleFlights.setCarrierCode(scheduleFlightInfo.getCarrier());
		scheduleFlights.setFlightNumber(scheduleFlightInfo.getFlightNo());
		scheduleFlights.setFrequency(frequency.toString());
		scheduleFlights.setEffectiveFromDate(scheduleFlightInfo.getScheduleStartDate());
		scheduleFlights.setEffectiveToDate(scheduleFlightInfo.getScheduleEndDate());
		if ("CAN".equals(scheduleFlightInfo.getFlightScheduleStatus())) {
			scheduleFlights.setStatus(scheduleFlightInfo.getFlightScheduleStatus());
		} else {
			scheduleFlights.setStatus(null);
		}
		scheduleFlightDao.insertFlightSchedule(scheduleFlights);
	}
	
	private void updateFlightSchedules(ScheduleFlightInfo scheduleFlightInfo) {
		
	}
	
	
    
}
