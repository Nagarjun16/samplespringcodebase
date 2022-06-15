package com.ngen.cosys.shipment.information.service;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.information.model.BookingShipmentDetails;
import com.ngen.cosys.shipment.information.model.BookingShipmentDetailsBuilder;
import com.ngen.cosys.shipment.information.model.ICMS.CancelBookingResponseType;

@Component
public class ICMSBookingApiService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ICMSBookingApiService.class);
	
	@Autowired
	BookingShipmentDetailsBuilder bookingShipmentDetailsBuilder;
	
	@Autowired
	BookingShipmentDetails bookingShipmentDetails;
	
	
	public void callICMSCancelBookingApi(BookingShipmentDetails bookingShipmentDetails) throws CustomException {
		LOGGER.debug("Method start IcmsBookingApiService->callSaveBookingApi->"+bookingShipmentDetails.toString());
		System.out.println("Method start IcmsBookingApiService->callICMSCancelBookingApi->"+bookingShipmentDetails.toString());
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			String cancelBookingRequest = new ObjectMapper().writeValueAsString(bookingShipmentDetails);
			System.out.println("save booking Json " + cancelBookingRequest);
			HttpEntity<String> request = new HttpEntity<>(cancelBookingRequest, headers);
			String url="http://localhost:9320/satssginterfaces/api/cmd/icms/message/outbound/cancelbooking";
			restTemplate.postForObject(url, request, CancelBookingResponseType.class);
		}catch(JsonProcessingException e){
			LOGGER.info("Unable to Call Icms service -> "+e.getMessage());
		}catch(Exception e) {
			LOGGER.info("Unable to Call Icms service -> "+e.getMessage());
		}
	}

}
