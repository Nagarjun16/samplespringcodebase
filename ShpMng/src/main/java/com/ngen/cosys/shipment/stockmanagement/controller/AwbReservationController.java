package com.ngen.cosys.shipment.stockmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservation;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;
import com.ngen.cosys.shipment.stockmanagement.service.AwbReservationService;

import io.swagger.annotations.ApiOperation;

/**
 * @author NIIT Technologies
 *
 */
@NgenCosysAppInfraAnnotation
public class AwbReservationController {

	private static final String EXCEPTION = "Exception Happened ... ";

	private static final Logger lOgger = LoggerFactory.getLogger(AwbReservationController.class);

	@Autowired
	private AwbReservationService awbReservationService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation("search next awb for awb reservation")
	@RequestMapping(value = "/api/stockmanagment/awbReservation/search/nextawbNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<AwbReservationSearch> searchnextAwbNumber(@RequestBody @Valid AwbReservationSearch search)
			throws CustomException {
		BaseResponse<AwbReservationSearch> awbReservationResponse = utilitiesModelConfiguration
				.getBaseResponseInstance();
		AwbReservationSearch response = null;
		try {
			response = awbReservationService.searchNextAwbNumber(search);
			awbReservationResponse.setData(response);
			awbReservationResponse.setSuccess(true);
		} catch (CustomException e) {
			awbReservationResponse.setData(search);
			lOgger.error(EXCEPTION, e);
		}
		return awbReservationResponse;
	}

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation("reservation of  awb number ")
	@RequestMapping(value = "/api/stockmanagment/awbReservation/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<AwbReservation> save(@RequestBody @Valid AwbReservation search) throws CustomException {
		BaseResponse<AwbReservation> awbReservationResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		AwbReservation response = null;
		try {
			response = awbReservationService.save(search);
			awbReservationResponse.setData(response);
			awbReservationResponse.setSuccess(true);
		} catch (CustomException e) {
			awbReservationResponse.setData(search);
			lOgger.error(EXCEPTION, e);
		}
		return awbReservationResponse;
	}

	/**
	 * @param search
	 * @return
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation("fetching the awb reservation with list ")
	@RequestMapping(value = "api/stockmanagment/awbReservation/searchAwbReservationDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> fetchAwbReservationDetails(@RequestBody @Valid AwbReservationSearch search)
			throws CustomException {
		BaseResponse<Object> awbReservationResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		List<AwbReservation> awbReservationList = null;
		try {
			awbReservationList = awbReservationService.fecthAwbReservationDetails(search);
			awbReservationResponse.setData(awbReservationList);
		} catch (CustomException e) {
			awbReservationResponse.setData(search);
			lOgger.error(EXCEPTION, e);
		}
		return awbReservationResponse;
	}
}