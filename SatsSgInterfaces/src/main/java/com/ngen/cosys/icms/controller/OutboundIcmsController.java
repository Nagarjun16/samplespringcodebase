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
package com.ngen.cosys.icms.controller;


/**
 *This controller takes care of the outbound requests from COSYS system to ICMS for eg:validateBooking, saveBooking, CancelBooking
 */
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;

import com.ngen.cosys.icms.model.bookingicms.BookingShipmentDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingResponseType;
import com.ngen.cosys.icms.service.FlightBookingService;
import com.ngen.cosys.icms.util.SoapServiceUtil;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@NgenCosysAppInfraAnnotation(path = "/api/cmd")
public class OutboundIcmsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundIcmsController.class);

	@Autowired
	FlightBookingService flightBookingService;

	@Autowired
	BookingShipmentDetails bookingDetails;
	
	@Autowired
	SoapServiceUtil soapServiceUtil;

	/**
	 * Process validateBooking COSYS to ICMS messages for
	 * 
	 * @param requestPayload
	 * @param request
	 * @return
	 * @throws JAXBException 
	 * @throws ParseException 
	 */
	
	
	@PostMapping(path = "icms/message/outbound/validatebooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ValidateBookingResponseType> validateBooking(@RequestBody BookingShipmentDetails bookingDetails,
			HttpServletRequest request) throws ParseException, JAXBException {
		LOGGER.debug("Method Start OutboundIcmsController-> validateBooking()-> bookingDetails :"
				+ bookingDetails.toString());
		
		ValidateBookingResponseType validateBookingResponse = flightBookingService.validateBooking(bookingDetails);
		LOGGER.info("Method End OutboundIcmsController-> validateBooking(), http Response : {}",
				validateBookingResponse.getResponseDetails().getStatus());
		return new ResponseEntity<>(validateBookingResponse, HttpStatus.ACCEPTED);

	}
	/**
	 * Process saveBooking COSYS to ICMS messages for
	 * 
	 * @param requestPayload
	 * @param request
	 * @return
	 */
	@PostMapping(path = "icms/message/outbound/savebooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveBookingDetailsResponseType> saveBookingDetails(@RequestBody BookingShipmentDetails bookingDetails,
			HttpServletRequest request) {
		LOGGER.debug("Method Start OutboundIcmsController-> saveBookingDetails()-> bookingDetails :"
				+ bookingDetails.toString());
		SaveBookingDetailsResponseType saveBookingResponse = flightBookingService.saveBookingDetails(bookingDetails);
		return new ResponseEntity<>(saveBookingResponse,HttpStatus.ACCEPTED);

	}
	/**
	 * Process cancelBooking COSYS to ICMS messages for
	 * 
	 * @param requestPayload
	 * @param request
	 * @return
	 */
	@PostMapping(path = "icms/message/outbound/cancelbooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CancelBookingResponseType> cancelBookingDetails(@RequestBody BookingShipmentDetails bookingDetails,
			HttpServletRequest request) {
		LOGGER.debug("Method Start OutboundIcmsController-> cancelBookingDetails()-> bookingDetails :"
				+ bookingDetails.toString());
		CancelBookingResponseType cancelBookingResponse = flightBookingService.cancelBookingDetails(bookingDetails);
		LOGGER.info("Method End OutboundIcmsController-> cancelBookingDetails(), http Response : {}",
				cancelBookingResponse.getResponseDetails().getStatus());
		return new ResponseEntity<>(cancelBookingResponse, HttpStatus.OK);

	}
	/**
	 * Test COSYS to ICMS messages as per Action
	 * 
	 * @param requestPayload
	 * @param request
	 * @return
	 */
	@PostMapping(path = "icms/message/outbound/testbooking/{action}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> testBookingDetails(@RequestBody String bookingDetails,@PathVariable ("action") String action,
			HttpServletRequest request) {
		LOGGER.info("Method Start OutboundIcmsController-> testBookingDetails()-> bookingDetails :"
				+ bookingDetails.toString()+"Action="+action);
		String responseXml="";
		try {
			responseXml = soapServiceUtil.callSoapService(bookingDetails,action);
		} catch (UnsupportedOperationException | SOAPException | IOException  e) {
			LOGGER.error("Exception at OutboundIcmsController-> testBookingDetails()",e);
		}
		LOGGER.info("Method End OutboundIcmsController-> testBookingDetails(), responseXML:",
				responseXml);
		return new ResponseEntity<>(responseXml, HttpStatus.OK);

	}
	@PostMapping(path = "icms/message/outbound/testbooking2//{action}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> testBookingDetails2(@RequestBody String bookingDetails,@PathVariable ("action") String action,
			HttpServletRequest request) {
		LOGGER.info("Method Start OutboundIcmsController-> testBookingDetails()-> bookingDetails :"
				+ bookingDetails.toString()+"Action="+action);
		String responseXml="";
		try {
			responseXml = soapServiceUtil.postSOAPXML(bookingDetails,action);
		} catch (Exception  e) {
			LOGGER.error("Exception at OutboundIcmsController-> testBookingDetails()",e);
		}
		LOGGER.info("Method End OutboundIcmsController-> testBookingDetails(), responseXML:",
				responseXml);
		return new ResponseEntity<>(responseXml, HttpStatus.OK);

	}

}
