/*******************************************************************************
 * Copyright (c) 2019 NIIT Technologies PVT LTD
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
package com.ngen.cosys.icms.util;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.ngen.cosys.icms.exception.MessageParseException;


/**
 * This class contains common methods
 */
@Component
public class CommonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * Validation for date format
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public boolean isValidDate(String dateStr,String dateFormat) throws ParseException{
		LOGGER.info("Method Start CommonUtil-> isValidDate()-> dateStr :"+dateStr+" date format:"+dateFormat);
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        LOGGER.info("Method End CommonUtil-> isValidDate()");
        return true;
    }
	
	/**
	 * convert date from one format to another
	 * @param dateStr
	 * @param oldDateFormat
	 * @param newDateFormat
	 * @return
	 */
	public String convertDateString(String dateStr,String oldDateFormat,String newDateFormat) {
		DateFormat df = new SimpleDateFormat(oldDateFormat);
		String formattedDate = "";
		try {
			Date date = df.parse(dateStr);
			formattedDate = new SimpleDateFormat(newDateFormat).format(date);
		}catch(ParseException e) {
			throw new MessageParseException("Invalid date format "+dateStr);
		}
		return formattedDate;
	}
	/**
	 * convert date string to local date
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public LocalDate convertStringToLocalDate(String dateStr,String format) throws IllegalArgumentException{
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDate.parse(dateStr, formatter);
		}
		catch(DateTimeParseException e){
			throw new MessageParseException("invalid date format "+dateStr);
		}
	}
	/**
	 * convert date String to local time
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public LocalDateTime convertStringToLocalTime(String dateStr,String format) throws DateTimeParseException,IllegalArgumentException  {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDateTime.parse(dateStr, formatter);
		}catch(DateTimeParseException e) {
			throw new MessageParseException("Invalid date format "+dateStr);
		}
	}
	
	/**
	 * check flight number 
	 * @param flightSchedulePublish
	 * @throws Exception
	 */
	public String validateFlightNumber(String flightNo) throws MessageParseException {
		LOGGER.info("Method Start CommonUtil-> validateFlightNumber() -> flight no:"+flightNo);
		
		if (!flightNo.isEmpty()) {
			String fltNo = flightNo.trim();
			if(fltNo.substring(fltNo.length() - 1).matches("[a-zA-Z]")) {
				if(fltNo.length() == 4) fltNo = "0" + fltNo;
				if (fltNo.length() == 3) fltNo = "00" + fltNo;
				if (fltNo.length() == 2) fltNo = "000" + fltNo;
				if (fltNo.length() == 1) fltNo = "0000" + fltNo;	
			}else {
				if (fltNo.length() == 3) fltNo = "0" + fltNo;
				if (fltNo.length() == 2) fltNo = "00" + fltNo;
				if (fltNo.length() == 1) fltNo = "000" + fltNo;
			}
			LOGGER.info("Method end CommonUtil-> validateFlightNumber() ->fltNo:"+fltNo);
			return fltNo;
		} 
		else {
			LOGGER.error("Invalid Flight Number.");
			throw new MessageParseException("Invalid Flight Number"); 
		}
	}
	/**
	 * calculate day change
	 * @param prvTimeStr
	 * @param timeStr
	 * @return
	 */
	public int calculateDayChange(String prvTimeStr,String timeStr) {
		LOGGER.info("Method Start CommonUtil-> calculateDayChange() -> prvTimeStr:"+prvTimeStr+" timeStr:"+timeStr);
    	int dayChange = 0;
    	LocalDate prvTime = convertStringToLocalDate(prvTimeStr, "dd-MMM-yyyy HH:mm:ss");
    	LocalDate time = convertStringToLocalDate(timeStr, "dd-MMM-yyyy HH:mm:ss");
    	dayChange = (int) ChronoUnit.DAYS.between(prvTime, time);
    	if (dayChange < 0 ) {
    		dayChange = 0;
    	}
    	LOGGER.info("Method End CommonUtil-> calculateDayChange() -> dayChange:"+dayChange);
    	return dayChange;
    }
	/**
	 * parse date
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date dateParser(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
       return sdf.parse(date);
	}
	
	 /**
	  * Get tag name from xml string
	  * @param requestPayload
	  * @return
	  * @throws FactoryConfigurationError
	  * @throws XMLStreamException
	  */
	public String getTagNamefromXmlString(String requestPayload) throws FactoryConfigurationError, XMLStreamException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		StringReader sr = new StringReader(requestPayload);
		XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(sr);
		xmlReader.nextTag();
		String tagName = xmlReader.getLocalName();
		LOGGER.info("Method End InboundIcmsService-> getTagNamefromXmlString()-> tagName :"+tagName);
		return tagName;
	}
	
	public String getTimeFromDateTimeString(String dateStr) throws ParseException{
		try {
			 DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		     Date dte = format.parse(dateStr);
		     DateFormat time = new SimpleDateFormat("HH:mm:ss");
		     return time.format(dte);
		}catch(ParseException e) {
			throw new MessageParseException("Invalid date format"+dateStr);
		}
	}  
	
	public String getTimeFromDateTime(String dateStr) throws ParseException{
		try {
			 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     Date dte = format.parse(dateStr);
		     DateFormat time = new SimpleDateFormat("HH:mm:ss");
		     return time.format(dte);
		}catch(ParseException e) {
			throw new MessageParseException("Invalid date format"+dateStr);
		}
	}  
	
}
