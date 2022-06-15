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
package com.ngen.cosys.icms.processor;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.dao.operationFlight.OperationalFlightDao;
import com.ngen.cosys.icms.dao.scheduleFlight.ScheduleFlightDao;
import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightLegInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedule;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightScheduleLegDetails;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
@Component
public class BaseFlightProcessor {
	
private static final Logger LOGGER = LoggerFactory.getLogger(BaseFlightProcessor.class);
	
	@Autowired
	protected ScheduleFlightDao scheduleFlightDao;
	
	@Autowired
	protected OperationalFlightDao operationalFlightDao;	
	
	@Autowired
	private CommonUtil commonUtil;
	/**
	 * validations for flight number,start and end date
	 * @param inputFS
	 * @throws MessageParseException
	 * @throws ParseException 
	 * @throws ParseException 
	 */

	public void validationForFlightNumberAndDate(String flightNumber,String fromDate,String toDate) throws ParseException{
		LOGGER.info("Method Start ScheduleFlightProcessor-> getFlightInfo()-> inputFS :"+flightNumber);
		//Flight number validation
		if (flightNumber!=null && flightNumber.trim().length() == 5
				&& !flightNumber.trim().substring(flightNumber.trim().length() - 1).matches("[a-zA-Z]")) {
			throw new MessageParseException("Last chararcter of Flight No is not alphabet. " + flightNumber);
		}	
		 //start and end date validations
		if(fromDate!=null && !commonUtil.isValidDate(fromDate,ValidationConstant.XML_DATE_FORMAT)) {
			 throw new MessageParseException("Invalid Flight date "+fromDate);
		}
		if(toDate!=null && !commonUtil.isValidDate(toDate,ValidationConstant.XML_DATE_FORMAT)) {
			 throw new MessageParseException("Invalid Flight to date "+toDate);
		}
		if(fromDate!=null && toDate!=null) {
			Date dStart=commonUtil.dateParser(fromDate);
	        Date dEnd=commonUtil.dateParser(toDate);
	        if(dStart.compareTo(dEnd) > 0) {
	        	throw new MessageParseException("Start date must be before End date");
	        }
		}
        
		LOGGER.info("Method End ScheduleFlightProcessor-> getFlightInfo()-> scheduleFlightInfo :");
	}
	
	public void validateLegDetails(FlightSchedule flightSchedule){
		LOGGER.info("Method Start ScheduleFlightProcessor-> validateLegDates()-> flightSchedule :"+flightSchedule.toString());

		List<FlightScheduleLegDetails> flightScheduleLegList = flightSchedule.getFlightscheduleLegDetails();
		String previousDestination = "";
		for (FlightScheduleLegDetails flightScheduleLeg : flightScheduleLegList) {
			if(flightScheduleLeg.getLegOrigin().equals(flightScheduleLeg.getLegDestination())) {
				throw new MessageParseException("Leg origin and destination can't be same");
			}
			if(!previousDestination.isEmpty() && !flightScheduleLeg.getLegOrigin().equals(previousDestination)) {
				throw new MessageParseException("Previous Leg destination and next leg origin are not same");
			}
			previousDestination = flightScheduleLeg.getLegDestination();
		}
		LOGGER.info("Method End ScheduleFlightProcessor-> validateLegDates()");
        
	}
	
	/**
	 * Validate whether service type exist or not
	 * @param scheduleFlight
	 */
	public void validateServiceType(ScheduleFlightInfo scheduleFlight){
		LOGGER.info("Method Start ScheduleFlightProcessor-> validateServiceType()-> scheduleFlightInfo :"+scheduleFlight.toString());
		
		 boolean isValidFlightType = false;
		 if (scheduleFlight.getServiceType() == null && scheduleFlight.getFlightType() == null) {
				throw new MessageProcessingException("Flight Type and Service type can't be null");
		 }
		 if (!StringUtils.isEmpty(scheduleFlight.getServiceType())) {
			  isValidFlightType = this.scheduleFlightDao.validationForServiceType(scheduleFlight.getServiceType());
			  if (!isValidFlightType) {
				  throw new MessageProcessingException("Flight service type doesn't exist");
			  }
		 }else {
			 if(scheduleFlight.getFlightType() != null || StringUtils.isEmpty(scheduleFlight.getFlightType())) {
				 String serviceType=operationalFlightDao.getServiceType(scheduleFlight.getFlightType());
				 if(serviceType!=null) {
						scheduleFlight.setServiceType(serviceType);
						scheduleFlight.setFlightType(scheduleFlightDao.getFlightType(serviceType));
				}else {
					throw new MessageProcessingException("Flight type is not valid");
				}
			 }
		 } 
		  LOGGER.info("Method End ScheduleFlightProcessor-> validateServiceType()-> scheduleFlightInfo :");
	}
	
	/**
	 * validate air craft type , origin and destination exist or not
	 * @param scheduleFlight
	 */
	public void validateAirlineIdAndACType(ScheduleFlightInfo scheduleFlight){
		LOGGER.info("Method Start ScheduleFlightProcessor-> ValidateAirlineIdAndACType()-> scheduleFlightInfo :"+scheduleFlight.toString());
		List<ScheduleFlightLegInfo> legLst = scheduleFlight.getScheduleFlightLegList();
		for (ScheduleFlightLegInfo legs : legLst) {
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("code", legs.getOrigin());
			if (scheduleFlightDao.validationForAirportExistance(queryMap) == 0) {
				throw new MessageProcessingException("Origin doesn't exist");
			}
			queryMap.put("code", legs.getDestination());
			if ( scheduleFlightDao.validationForAirportExistance(queryMap) == 0) {
				throw new MessageProcessingException("Destination doesn't exist");
			}
			queryMap.put("aircraftCode", legs.getAircraftType());
			if (scheduleFlightDao.validationForAirCraftType(queryMap) == 0) {
				throw new MessageProcessingException("Air craft type doesn't exist");
			}
		}
		LOGGER.info("Method End ScheduleFlightProcessor-> ValidateAirlineIdAndACType()-> scheduleFlightInfo :");
	}
	
	public void validateCarrierCode(String carrierCode) {
		LOGGER.info("Method Start ScheduleFlightProcessor-> validateCarrierCode() -> carrierCode"+carrierCode); 
			if(scheduleFlightDao.validationForCarrierCode(carrierCode) == 0) {
				throw new MessageProcessingException("Carrier Code doesn't exist");
			}
		
		LOGGER.info("Method End ScheduleFlightProcessor-> validateCarrierCode()"); 
	}

	//validate Aircraft Type
	public void validateAircraftType(OperationalFlightInfo flightInfo) {
		LOGGER.info("inside validateAircraftType method ::");
		List<OperationalFlightLegInfo> flightLegList = flightInfo.getFlightLegInfo();
		for (OperationalFlightLegInfo legs : flightLegList) {
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put(ValidationConstant.AIRCRAFT_CODE, legs.getAircraftType());
			System.out.println("aircraftCode::" + legs.getAircraftType());
			if (scheduleFlightDao.validationForAirCraftType(queryMap) == 0) {
				throw new MessageProcessingException("Air craft type doesn't exist");
			}
		}
		LOGGER.info("end validateAircraftType method ::");

	}

	//validate First Arrival Point
	public void validateLegDestination(OperationalFlightInfo flightInfo) {
		LOGGER.info("inside validateLegDestination method::");
		List<OperationalFlightLegInfo> flightLegList = flightInfo.getFlightLegInfo();
		for (OperationalFlightLegInfo legs : flightLegList) {
			Map<String, String> queryMap = new HashMap<String, String>();
			System.out.println("First Arrival Point ::" + legs.getLegDestination());
			queryMap.put(ValidationConstant.CODE, legs.getLegDestination());
			if (scheduleFlightDao.validationForAirportExistance(queryMap) == 0) {
				throw new MessageProcessingException("First Arrival Point doesn't exist");
			}
		}
		LOGGER.info("end validateLegDestination method ::");
	}

	//validate loading Point
	public void validateLegOrigin(OperationalFlightInfo flightInfo) {
		LOGGER.info("start validateLegOrigin method ::");
		List<OperationalFlightLegInfo> flightLegList = flightInfo.getFlightLegInfo();
		for (OperationalFlightLegInfo legs : flightLegList) {
			Map<String, String> queryMap = new HashMap<String, String>();
			System.out.println("loading point ::" + legs.getLegOrigin());
			queryMap.put(ValidationConstant.CODE, legs.getLegOrigin());
			if (scheduleFlightDao.validationForAirportExistance(queryMap) == 0) {
				throw new MessageProcessingException("loading point doesn't exist");
			}
		}
		LOGGER.info("end validateLegOrigin method ::");

	}

	//validate FlightType and Service Type	
	public void validateServiceTypeAndFllightType(OperationalFlightInfo flightInfo) {
		LOGGER.info("Method Start validateServiceTypeAndFllightType->"+flightInfo.toString());
		
		 boolean isValidFlightType = false;
		 if (flightInfo.getServiceType() == null && flightInfo.getFlightType() == null) {
				throw new MessageProcessingException("Flight Type and Service type can't be null");
		 }
		 if (!StringUtils.isEmpty(flightInfo.getServiceType())) {
			  isValidFlightType = this.scheduleFlightDao.validationForServiceType(flightInfo.getServiceType());
			  if (!isValidFlightType) {
				  throw new MessageProcessingException("Flight service type doesn't exist");
			  }else {
				  flightInfo.setFlightType(scheduleFlightDao.getFlightType(flightInfo.getServiceType()));
			  }
		 }else {
			 if(flightInfo.getFlightType() != null || StringUtils.isEmpty(flightInfo.getFlightType())) {
				 String serviceType=operationalFlightDao.getServiceType(flightInfo.getFlightType());
				 if(serviceType!=null) {
					 flightInfo.setServiceType(serviceType);
					 flightInfo.setFlightType(scheduleFlightDao.getFlightType(serviceType));
				}else {
					throw new MessageProcessingException("Flight type is not valid");
				}
			 }
		 } 
		  LOGGER.info("Method End validateServiceTypeAndFllightType->  flightInfo :");
	}}
