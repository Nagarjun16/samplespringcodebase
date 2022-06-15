package com.ngen.cosys.shipment.information.model;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO;

@Component
public class BookingShipmentDetailsBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingShipmentDetailsBuilder.class);
	
	@Autowired
	ShipmentInformationDAO shipmentInfoDAO;

	public Boolean checkCarrierGroup(List<ShipmentFlightModel> bookingFlightDetails) {
		LOGGER.info("method checkCarrierGroup start:  ", Level.INFO, bookingFlightDetails.toString(), true);
		Boolean callICMSApi = bookingFlightDetails.stream().anyMatch(
				p -> "SIN".equals(p.getBookingFlightBoardingPoint()) && shipmentInfoDAO.checkCarrierGroup(p.getCarrierCode()) == 1);
		return callICMSApi;
	}
	
	public BookingShipmentDetails buildCancelBookingModelForICMS(ShipmentInfoModel searchInfo) {
		BookingShipmentDetails bookingShipmentDetails = new BookingShipmentDetails();
		if(StringUtils.isEmpty(bookingShipmentDetails.getShipmentNumber())) {
			bookingShipmentDetails.setShipmentPrefix(searchInfo.getShipmentNumber().substring(0,3));
			bookingShipmentDetails.setMasterDocumentNumber(searchInfo.getShipmentNumber().substring(3,(searchInfo.getShipmentNumber().length())));
			bookingShipmentDetails.setShipmentOrigin(searchInfo.getOrigin());
			bookingShipmentDetails.setShipmentDestination(searchInfo.getDestination());
		}
		return bookingShipmentDetails;
	}
	
	
	

}
