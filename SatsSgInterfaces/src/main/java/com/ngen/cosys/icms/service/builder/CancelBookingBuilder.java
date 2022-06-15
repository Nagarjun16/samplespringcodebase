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
package com.ngen.cosys.icms.service.builder;


import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.model.bookingicms.BookingShipmentDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingFilterType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingRequestData;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ShipmentIdentifierDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;

/**
 *This class convert Json object to Booking shipment model 
 *Map booking shipment model to Cancel booking request model and response model
 *
 */
@Component
public class CancelBookingBuilder {
	/**
	 * convert to cancel booking request model from input json string
	 * @param bookingDetails
	 * @return
	 */
	public CancelBookingRequestType createCancelRequest(BookingShipmentDetails bookingDetails) {
		System.out.println(bookingDetails.toString());
		CancelBookingRequestType cancelBookingRequestType= new CancelBookingRequestType();
		
		CancelBookingRequestData cancelBookingRequestData=new CancelBookingRequestData(); 
		cancelBookingRequestData.setRequestID(String.valueOf(System.nanoTime()));
		
		
		MessageHeaderType messageHeaderType=new MessageHeaderType();
		messageHeaderType.setMessageType(bookingDetails.getMessageType());
		messageHeaderType.setSourceSystem(bookingDetails.getSourceSystem());
		messageHeaderType.setUserId(bookingDetails.getUserId());
		cancelBookingRequestType.setMessageHeader(messageHeaderType);
		
		BookingFilterType bookingFilterType = new BookingFilterType();
		//bookingFilterType.setUbrNumber(bookingDetails.getUbrNumber());
		bookingFilterType.setOrigin(bookingDetails.getShipmentOrigin());
		bookingFilterType.setDestination(bookingDetails.getShipmentDestination());
		//bookingFilterType.setAgentCode(bookingDetails.getAgentCode());
		
		ShipmentIdentifierDetailType shipmentIdentifierDetailType = new ShipmentIdentifierDetailType();
		shipmentIdentifierDetailType.setShipmentPrefix(bookingDetails.getShipmentPrefix());
		shipmentIdentifierDetailType.setMasterDocumentNumber(bookingDetails.getMasterDocumentNumber());
		bookingFilterType.setShipmentIdentifierDetails(shipmentIdentifierDetailType);
		cancelBookingRequestData.setBookingDetailsFilter(bookingFilterType);
		
		cancelBookingRequestType.setRequestData(cancelBookingRequestData);
		
		return cancelBookingRequestType;
	}
	
	/**
	 * convert to cancel Booking response model from json string
	 * @param responseXml
	 * @return
	 * @throws JAXBException 
	 */
	public CancelBookingResponseType createCancelResposne(String responseXml) throws JAXBException {
		// Marshal XML to JaxbObject
		// Map JaxbObject to JSON object
		System.out.println("CancelBookingBuilder -> createCancelResposne method start -> response xml :"+responseXml);
		StringReader strReader = new StringReader(responseXml);
		JAXBContext jaxbContext = JAXBContext.newInstance(CancelBookingResponseType.class);  
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();	
		CancelBookingResponseType cancelBookingResponse = (CancelBookingResponseType) unmarshal.unmarshal(strReader); 
		System.out.println("cancelBookingBuilder -> createCancelResposne method End -> cancelBookingResponse :"+cancelBookingResponse.toString());
		return cancelBookingResponse;
	}
	
	
	
	
	

}
