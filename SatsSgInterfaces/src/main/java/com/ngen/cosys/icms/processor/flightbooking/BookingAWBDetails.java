package com.ngen.cosys.icms.processor.flightbooking;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.ShipmentIdentifierDetails;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;

@Component
public class BookingAWBDetails {
	
	@Autowired
	private FlightBookingDao flightBookingDao;
	
	@Autowired
	ShipmentProcessorService shipmentProcessorService;
	
	@Autowired
	CommonUtil commonUtil;
	
	public ShipmentBookingDetails getAWBDetails(BookingDetails bookingDetails)
			throws CustomException, ParseException {
		String shipmentNumber =getShipmentNumber(bookingDetails.getShipmentIdentifierDetails());
		LocalDate shipmentDate=getShipmentDate(bookingDetails);
		//set AWBNumber and shipment date in bookingDetails model
		setAWBNumberAndShipmentDate(bookingDetails, shipmentNumber, shipmentDate);
		Map<String,Object> queryParam=new HashMap<>();
		queryParam.put(BookingConstant.SHIPMENT_NUMBER, shipmentNumber);
		queryParam.put(BookingConstant.SHIPMENT_DATE, shipmentDate);
		ShipmentBookingDetails shipmentBookingDetails = flightBookingDao.getExistingShipmentBooking(queryParam);
		return shipmentBookingDetails;
	}

	private void setAWBNumberAndShipmentDate(BookingDetails bookingDetails, String shipmentNumber,
			LocalDate shipmentDate) {
		bookingDetails.setAWBNumber(shipmentNumber);
		bookingDetails.setShipmentDate(shipmentDate);
	}
	
	private LocalDate getShipmentDate(BookingDetails bookingDetails) {
		String awbNumber=getShipmentNumber(bookingDetails.getShipmentIdentifierDetails());
		LocalDate shipmentDate= shipmentProcessorService.getShipmentDate(awbNumber);
		if (ObjectUtils.isEmpty(shipmentDate)) {
            shipmentDate = LocalDate.now();
         }
		return shipmentDate;
	}


	public String getShipmentNumber(ShipmentIdentifierDetails shipmentIdentifierDetails) {
		return String.valueOf(shipmentIdentifierDetails.getShipmentPrefix())
		+ String.valueOf(shipmentIdentifierDetails.getMasterDocumentNumber());
	}
}
