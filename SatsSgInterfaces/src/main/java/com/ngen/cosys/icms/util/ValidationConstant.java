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
package com.ngen.cosys.icms.util;

import org.springframework.stereotype.Component;

/**
 *This class contains constant values
 */
@Component
public class ValidationConstant {

    public static final String SEMICOLON = "  ;  ";
    public static final String PROCESSED = "PROCESSED";
    public static final String REJECTED = "REJECTED";
    public static final String SENT = "SENT";
    public static final String ERROR = "ERROR";
    public static final String INVALID_REQUEST = "Invalid Request";
    public static final String PREANNOUCEMENT_TO_ICS = "PREANNOUNCEMENT DATA TO ICS CUSTOMS";
    public static final String CARGO = "C";
    public static final String COMBINATION = "CO";
    public static final String IMPORT_TRUCK = "IT";
    public static final String EXPORT_TRUCK = "ET";
    public static final String TRUCK = "T";
    public static final String FLIGHT_KEY = "flightKey";
    public static final String FLIGHT_NUMBER = "flightNumber";
    public static final String FLIGHT_DATE = "flightDate";
    public static final String FLIGHT_STATUS = "flightStatus";
    public static final String SERVICE_TYPE = "serviceType";
    public static final String FLIGHT_TYPE = "flightType";
    public static final String AIRCRAFT_TYPE = "aircraftType";
    public static final String AIRCRAFT_CODE = "aircraftCode";
    public static final String CODE = "code";
    public static final String PORT_SIN = "SIN";
    public static final String ACTIVE = "A";
    public static final String CANCEL = "D";
    public static final String STATUS_ACTIVE = "ACT";
    public static final String FLIGHT_ACTIVE_FLAG = "A";
    public static final String FLIGHT_CANCEL_FLAG = "D";
    public static final String XML_DATE_FORMAT = "dd-MMM-yyyy";
    public static final String XML_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
    public static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATEFORMAT = "yyyy-MM-dd";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_STA = "dateSTA";
    public static final String DATE_STD = "dateSTD";
    public static final String IMPORT_FLIGHT = "I";
    public static final String EXPORT_FLIGHT = "E";
    public static final String OPERATIONAL_FLIGHT = "operationalFlightPublish";
	public static final String STATUS_LIVE = "LIVE";
    public static final String STATUS_MODIFIED = "MODIFIED";
    public static final String STATUS_NOP = "NOP";
    public static final String STATUS_TBA = "TBA";
    public static final String STATUS_CAN = "CAN";
    public static final String STATUS_TBC = "TBC";
    public static final String STATUS_PUB = "PUB";
    public static final String FLIGHT_SCHEDULE = "FlightSchedulePublish";
    public static final String FLIGHT_BOOKING = "publishBookingDetails";
    public static final String FLIGHT_BOOKING_MESSAGE = "BookingDetails";
    public static final String CHANNEL_RECEIVED = "HTTP";
    public static final String SYSTEM = "ICMS";
    public static final String FLIGHT_SCHEDULE_TYPE = "flightSchedulePublish";
    public static final String ERROR_CODE = "1";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String FLIGHT_ON_MON = "flightOnMon";
    public static final String FLIGHT_ON_TUE = "flightOnTue";
    public static final String FLIGHT_ON_WED = "flightOnWed";
    public static final String FLIGHT_ON_THU = "flightOnThu";
    public static final String FLIGHT_ON_FRI = "flightOnFri";
    public static final String FLIGHT_ON_SAT = "flightOnSat";
    public static final String FLIGHT_ON_SUN = "flightOnSun";
	public static final String OPEN_FOR_BOOKING = "O";
	public static final String CLOSED_FOR_BOOKING = "C";
	public static final String OFF = "Off";
	public static final String ON = "On";
	public static final String N = "N";
	public static final int FLIGHT_REMARK_LENGTH = 300;
	public static final int SCHEDULE_FLIGHT_REMARK_LENGTH = 200;
	public static final int CHAR_LENGTH = 65;
	public static final int SUBSTRING_CHAR_LENGTH = 64;
	public static final String IS_EDI_SCREEN = "isediscreen";
	public static final String MANUAL = "MANUAL";
	public static final String LOGIN_USER = "loginuser";
	public static final String TRUE = "true";
	public static final String START_BRACKET = "(";
	public static final String END_BRACKET = ")";


}
