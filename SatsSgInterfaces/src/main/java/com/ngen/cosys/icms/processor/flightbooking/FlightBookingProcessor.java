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

package com.ngen.cosys.icms.processor.flightbooking;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.ngen.cosys.export.booking.model.SHC;;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.exception.MessageParseException;
import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.schema.flightbooking.*;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

@Component
public class FlightBookingProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBookingProcessor.class);

	@Autowired
	PublishBookingDetails publishBookingDetails;

	@Autowired
	ICMSResponseModel icmsResponse;

	@Autowired
	private FlightBookingDao flightBookingDao;

	@Autowired
	private BookingShipmentValidation bookingShipmentValidation;

	@Autowired
	private BookingFlightValidation bookingFlightValidation;

	@Autowired
	private PartBookingHelper partBookingHelper; 

	@Autowired
	private BookingInsertHelper bookingInsertHelper;
	
	@Autowired
	BookingAWBDetails bookingAWbDetails;
	
	@Autowired
	BookingDetails bookingDetails;

	@Autowired
	CommonUtil commonUtil;
	
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public ICMSResponseModel processBookingPublishMessage(PublishBookingDetails publishBookingDetails)
			throws SQLException, MessageProcessingException, CustomException, ParseException {
		LOGGER.debug("Method start processBookingPublishMessage method ::publishBookingDetails->"+publishBookingDetails.toString());
		
		bookingDetails = publishBookingDetails.getPublishData().getBookingDetails();
		
		checkBlackListShipmentNumber(bookingDetails);
		List<BookingFlightDetails> flightList=bookingDetails.getBookingFlightDetails();
		String shipmentType = getShipmentType(bookingDetails);
		ShipmentBookingDetails existingShipmentBookingDetails = bookingAWbDetails.getAWBDetails(bookingDetails);
		if(existingShipmentBookingDetails!=null) {
			LOGGER.info("Method start processBookingPublishMessage method -> existingShipmentBookingDetails -> "+ existingShipmentBookingDetails.toString());
			LOGGER.info(existingShipmentBookingDetails.toString());
		}
		
		flightList = bookingFlightValidation.validateFlightBooking(bookingDetails,existingShipmentBookingDetails);
		flightBookingDao.insertIntermediateBookingDetails(bookingDetails,flightList); 
		if(!bookingDetails.getIsShipmentCancelled()) {
			bookingShipmentValidation.validateShipmentBooking(bookingDetails, existingShipmentBookingDetails);
			
			ShipmentBookingDetails shipmentBookingDetails = partBookingHelper.processPartBooking(shipmentType,bookingDetails);
			
			bookingInsertHelper.processAWB(bookingDetails, shipmentBookingDetails, shipmentType);
			bookingInsertHelper.processTTFlag(shipmentType, shipmentBookingDetails);
			
			insertBookingDetails(existingShipmentBookingDetails, shipmentBookingDetails);
			
		}

		LOGGER.info("End processFlightBookingMessage method ::");
		icmsResponse.setHttpStatus(HttpStatus.OK);
		// Save incoming message as success
		return icmsResponse;
	}

	
	private void checkBlackListShipmentNumber(BookingDetails bookingDetails) {
		if(bookingDetails.getShipmentIdentifierDetails()!=null && bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix()!=null && !bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix().isEmpty() && bookingDetails.getShipmentIdentifierDetails()!=null && bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber()!=null && !bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber().isEmpty() ) {
			String prefix=String.valueOf(bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix());
			String documentNumber=String.valueOf(bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber());
			if(flightBookingDao.isBlacklistShipmentNumber(prefix+documentNumber)) {
				throw new MessageProcessingException("This Shipment is blacklist");
			}
		}else {
			throw new MessageProcessingException("Invalid Shipment Prefix /Shipment Number.");
		}
	}


	public void insertBookingDetails(ShipmentBookingDetails existingShipmentBookingDetails,
			ShipmentBookingDetails shipmentBookingDetails) {
		//flightBookingDao.insertIntermediateBookingDetails(bookingDetails,flightList); 
		bookingInsertHelper.insertBooking(shipmentBookingDetails, existingShipmentBookingDetails);
	}

	/**
	 * This method contains business validations
	 * 
	 * @param flightSchedulePublish
	 * @return
	 * @throws MessageParseException
	 * @throws ParseException
	 * @throws ParseException
	 */

	public boolean xsdValidations(PublishBookingDetails publishBookingDetails)
			throws MessageParsingException, ParseException {
//    	FlightOperation flightOperation = operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation();
		// XML parsing validations

//	        try {
//		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.XML_NS_PREFIX);
//	            Schema schema = factory.newSchema(new File("CapacityBookingTypes.xsd"));
//	            Validator validator = schema.newValidator();
//	            validator.validate(new StreamSource(new File(xmlPath)));
//	        } catch (IOException | SAXException e) {
//	            System.out.println("Exception: "+e.getMessage());
//	            return false;
//	        }
		return true;
	}

	public PublishBookingDetails businessValidations(PublishBookingDetails publishBookingDetails)
			throws MessageParsingException, ParseException {
//    	FlightOperation flightOperation = operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation();
		// XML parsing validations

		return publishBookingDetails;
	}



	
	/**
	 * @param bookingDetails
	 * @return
	 */
	private String getShipmentType(BookingDetails bookingDetails) {
		String shipmentType="";
		String origin = bookingDetails.getShipmentDetails().getShipmentOrigin();
		String destination = bookingDetails.getShipmentDetails().getShipmentDestination();
		String tenantPort = BookingConstant.TENANT_SINGAPORE;
		
		// Set the variable for identifying type of shipment
		if (origin != null && !origin.isEmpty()) {
			if (!origin.equalsIgnoreCase(tenantPort) && !destination.equalsIgnoreCase(tenantPort)) {// Transshipment
				shipmentType = BookingConstant.TRANSSHIPMENT;
			} else if (origin.equalsIgnoreCase(tenantPort)) { // Export
				shipmentType = BookingConstant.EXPORTSHIPMENT;
			} else if (destination.equalsIgnoreCase(tenantPort)) {// Import
				shipmentType = BookingConstant.IMPORTSHIPMENT;
			}	
		} else {
			throw new MessageParseException("Invalid Shipment Origin");
		}
		LOGGER.info("Method End getShipmentType() -> shipmentType ->"+shipmentType);
		return shipmentType;

	}

}
