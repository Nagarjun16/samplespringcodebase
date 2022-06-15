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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightLegInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightSegmentInfo;
import com.ngen.cosys.icms.schema.operationFlight.AirCraftdetails;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedule;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightScheduleLegDetails;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedulePublish;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSegmentDetails;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
import com.ngen.cosys.icms.exception.MessageParseException;

@Component
public class MapXmlToScheduleFlightInfoModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MapXmlToScheduleFlightInfoModel.class);
	@Autowired
	CommonUtil commonUtil;
	
	 /**
     * map xml model to schedule model
     * @param schedule
     * @return
	 * @throws ParseException 
     */
    public ScheduleFlightInfo createScheduleFlightInfoModel(FlightSchedulePublish schedule) throws ParseException{
    	LOGGER.info("Method Start ScheduleFlightProcessor-> mapXmlValuesToModel()-> schedule :"+schedule.toString());
    	ScheduleFlightInfo scheduleFlightInfo = new ScheduleFlightInfo();
    	FlightSchedule fs = schedule.getObjEntity().getPublishData().getFlightschedule();
    	String fromFormat = "dd-MMM-yyyy";
    	String toFormat = "dd-MM-yyyy";
    	//Set values in BO
    	scheduleFlightInfo.setCarrier(fs.getCarrierCode().trim());
    	scheduleFlightInfo.setFlightNo(commonUtil.validateFlightNumber(fs.getFlightscheduleNumber()));
    	fs.setFlightscheduleNumber(scheduleFlightInfo.getFlightNo());
    	String endDateStr = commonUtil.convertDateString(fs.getFlightscheduleToDate(),fromFormat,toFormat);
    	scheduleFlightInfo.setScheduleEndDate(commonUtil.convertStringToLocalDate(endDateStr,toFormat));
    	String startDateStr = commonUtil.convertDateString(fs.getFlightscheduleFromDate(),fromFormat,toFormat);
    	scheduleFlightInfo.setScheduleStartDate(commonUtil.convertStringToLocalDate(startDateStr,toFormat));
    	scheduleFlightInfo.setFlightKey(scheduleFlightInfo.getCarrier() + scheduleFlightInfo.getFlightNo());
    	scheduleFlightInfo.setFlightScheduleStatus(fs.getFlightscheduleStatus());
    	if(fs.getFlightscheduleRemarks() != null && fs.getFlightscheduleRemarks().length()>200) {
    			throw new MessageParseException("Schedule Remark should not exceed 200 characters");
    	}
    	scheduleFlightInfo.setFlightScheduleRemark(fs.getFlightscheduleRemarks());
    	scheduleFlightInfo.setFlightType(fs.getFlightType());
    	//set frequency
    	char[] frequencyChar = fs.getFlightScheduleFrequency().toCharArray();
    	validateFrequency(frequencyChar);
    	setFlightDayFlag(scheduleFlightInfo, frequencyChar);
    	
    	//flight leg details
    	mapLegDetails(scheduleFlightInfo, fs);
    	
    	//segment 
    	mapSegmentDetails(scheduleFlightInfo, fs);
    	
    	LOGGER.info("Method End ScheduleFlightProcessor-> mapXmlValuesToModel()-> scheduleFlightInfo :"+scheduleFlightInfo.toString());
    	return scheduleFlightInfo;
    }
    /**
     * validate frequency for duplicate character
     * @param frequency
     */
    private void validateFrequency(char[] frequency) {
    	List<Character> charList = new ArrayList<>();
    	for (char freq : frequency) {
			if(!charList.contains(freq)) {
				charList.add(freq);
			}else {
				throw new MessageParseException("Invalid schedule frequency");
			}
		}
    }

	/**
	 * map xml dto segment details to schedule flight 
	 * @param flightInfo
	 * @param flightSchedule
	 */
	private void mapSegmentDetails(ScheduleFlightInfo flightInfo, FlightSchedule flightSchedule) {
		LOGGER.info("Method Start ScheduleFlightProcessor-> mapSegmentDetails()-> schedule flight info :"+flightInfo.toString());
		List<ScheduleFlightSegmentInfo> segmentLst = new ArrayList<ScheduleFlightSegmentInfo>();
    	List<FlightSegmentDetails> xmlSegmentLst = flightSchedule.getFlightSegmentDetails();
    	int seg = 0;
    	for (FlightSegmentDetails xmlSegment : xmlSegmentLst) {
    		ScheduleFlightSegmentInfo segment = new ScheduleFlightSegmentInfo();
    		seg++;
    		segment.setFlightSegmentOrder(seg);
    		segment.setOrigin(xmlSegment.getSegmentOrigin());
    		segment.setDestination(xmlSegment.getSegmentDestination());
			segmentLst.add(segment);
		}
    	flightInfo.setScheduleFlightSegmentList(segmentLst);
    	LOGGER.info("Method End ScheduleFlightProcessor-> mapSegmentDetails()-> scheduleFlightInfo :"+flightInfo.toString());
	}

	/**
	 * Map xml leg details to schedule flight details
	 * @param flightInfo
	 * @param fs
	 * @throws ParseException 
	 */
	private void mapLegDetails(ScheduleFlightInfo flightInfo, FlightSchedule fs) throws ParseException {
		LOGGER.info("Method Start ScheduleFlightProcessor-> mapXmlValuesToModel()-> scheduleFlightInfo :"+flightInfo.toString());
		List<ScheduleFlightLegInfo> flightLegLst = new ArrayList<ScheduleFlightLegInfo>();
    	List<FlightScheduleLegDetails> xmlLegLst = fs.getFlightscheduleLegDetails();
    	int depDayChange = 0;
    	int arvDayChange = 0;
    	//String prvArrivalTime = xmlLegLst.get(0).getSTA();
    	//String prvDepTime =  xmlLegLst.get(0).getSTD();
    	int legCount = 0;
    	for (FlightScheduleLegDetails objLeg : xmlLegLst) {
    		ScheduleFlightLegInfo scheduleFlightLeg = new ScheduleFlightLegInfo();
    		legCount++;
    		scheduleFlightLeg.setFlightSegmentOrder(legCount);
    		scheduleFlightLeg.setOrigin(objLeg.getLegOrigin());
			scheduleFlightLeg.setDepartureTime(commonUtil.getTimeFromDateTimeString(objLeg.getSTD()));
			scheduleFlightLeg.setDestination(objLeg.getLegDestination());
			scheduleFlightLeg.setArrivalTime(commonUtil.getTimeFromDateTimeString(objLeg.getSTA()));
			
			//if(!prvArrivalTime.isEmpty()) {
			//	depDayChange = commonUtil.calculateDayChange(prvDepTime,objLeg.getSTD());
			//}
			if (StringUtils.isNumeric(objLeg.getDayChangeAtOrigin()) && StringUtils.isNumeric(objLeg.getDayChangeAtDestination())) {
				depDayChange = Integer.parseInt(objLeg.getDayChangeAtOrigin());
				scheduleFlightLeg.setDepartureDayChange(depDayChange);
				//prvDepTime = objLeg.getSTD();
				//arvDayChange = commonUtil.calculateDayChange(prvDepTime,objLeg.getSTA());
				arvDayChange = Integer.parseInt(objLeg.getDayChangeAtDestination());
				scheduleFlightLeg.setArrivalDayChange(arvDayChange);
				//prvArrivalTime = objLeg.getSTA();
			}
			
			
			scheduleFlightLeg.setSTAUTC(objLeg.getSTAUTC());
			scheduleFlightLeg.setSTDUTC(objLeg.getSTDUTC());
			
			//aircraft details
	    	AirCraftdetails aircraft = objLeg.getLegCapacityDetailsType().getAirCraftdetails();
	    	scheduleFlightLeg.setAircraftType(aircraft.getAirCraftTypeCode());
	    	scheduleFlightLeg.setServiceType(aircraft.getServiceType());
	    	flightLegLst.add(scheduleFlightLeg);
	    	flightInfo.setAircraftType(aircraft.getAirCraftTypeCode());
	    	
	    	flightInfo.setServiceType(aircraft.getServiceType());
		}
    	flightInfo.setScheduleFlightLegList(flightLegLst);
    	LOGGER.info("Method End ScheduleFlightProcessor-> mapLegDetails()-> scheduleFlightInfo :"+flightInfo.toString());
	}
	
	/**
	 * set frequency in appropriate variables
	 * @param flight
	 * @param days
	 * @throws MessageParseException
	 */
	private void setFlightDayFlag(ScheduleFlightInfo flight, char[] days) throws MessageParseException {
		for (char day : days) {
			switch (day) {
			case '1':
				flight.setFlightOnMon(true);
				break;
			case '2':
				flight.setFlightOnTue(true);
				break;
			case '3':
				flight.setFlightOnWed(true);
				break;
			case '4':
				flight.setFlightOnThu(true);
				break;
			case '5':
				flight.setFlightOnFri(true);
				break;
			case '6':
				flight.setFlightOnSat(true);
				break;
			case '7':
				flight.setFlightOnSun(true);
				break;
			default:
				throw new MessageProcessingException("Invalid schedule frequency");
				//break;
			}
		}
	}
}
