package com.ngen.cosys.platform.rfid.tracker.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.FlightBookingDelegate;
import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.FlightDelegate;
import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.FlightManifestedDelegate;
import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.ImportFFMJobDelegate;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchRequest;
import com.ngen.cosys.platform.rfid.tracker.interfaces.service.SearchCriteriaService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class RfidDummyController {
	@Autowired
	private FlightDelegate flightDelegate;

	@Autowired
	private FlightBookingDelegate flightBookingDelegate;

	@Autowired
	private FlightManifestedDelegate manifestedDelegate;

	@Autowired
	private ImportFFMJobDelegate importFFMJobDelegate;
	
	@Autowired
	private SearchCriteriaService searchCriteriaService;

	@Autowired
	ConnectorLoggerService logger;

	@ApiOperation("RFID Flights")
	@RequestMapping(value = "/getflights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getFlights() throws CustomException {
		flightDelegate.getFlights();
	}

	@ApiOperation("RFID Flight Booking")
	@RequestMapping(value = "/getflightbooking", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getFlightBooking() throws CustomException {
		flightBookingDelegate.getFlightBooking();
	}

	@ApiOperation("RFID Flight Manifest")
	@RequestMapping(value = "/getflightmanifested", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getFlightManifested() throws CustomException {
		manifestedDelegate.getFlightManifested();
	}

	@ApiOperation("RFID Flight FFM")
	@RequestMapping(value = "/getimportFFM", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getImportFFM() throws CustomException {
		importFFMJobDelegate.getImportFFM();
	}
	
	@ApiOperation("Check for RFID Applicable")
	@RequestMapping(value = "/trackerservice/rfidApplicable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<SearchRequest> getConfirmSearchCriteria(@RequestBody SearchRequest searchRequest) throws CustomException {
		BaseResponse<SearchRequest> response = new BaseResponse<SearchRequest>();
		searchRequest.setRfidApplicable(searchCriteriaService.getConfirmSearchCriteria(searchRequest));
		response.setData(searchRequest);
        response.setSuccess(true);
		return response;
	}


}
