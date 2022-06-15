/**
 * 
 * HandoverInboundUldTrollyController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.framework.util.LoggerUtil;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.impbd.service.HandoverInboundUldTrollyService;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validators.HandoverValidationGroup;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class HandoverInboundUldTrollyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandoverInboundUldTrollyController.class);

	@Autowired
	private HandoverInboundUldTrollyService handoverInboundUldTrollyService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	private static final String FETCH_ADD_UPDATE_TROLLY_ERROR = "TROLLY1";
	private static final String FETCH_INBOUND_TROLLY_ERROR = "TROLLY2";
	private static final String FETCH_DELETE_TROLLY_NO_ERROR = "TROLLY4";
	private static final String FETCH_ADD_TROLLY_ERROR = "TROLLY5";
	private static final String FETCH_EDIT_DATA_ERROR = "TROLLY6";

	/**
	 * This method fetches Export Handover Inbound Trolly information.
	 * 
	 * @return ResponseEntity<BaseResponse<HandoverInboundTrolly>
	 * @throws CustomException
	 */
	@ApiOperation("Fectch All Handover Inbound Trolly list")
	@RequestMapping(value = "/api/import/inboundtrolly/fetchInboundTrolly", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<HandoverInboundTrolly>>> fetchInboundTrolly(
			@ApiParam(value = "InboundTrollyList", required = true) @Validated({
					HandoverValidationGroup.class }) @RequestBody FlightModel inboundTrolly)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HandoverInboundTrolly>> handoverInboundList = utilitiesModelConfiguration
				.getBaseResponseInstance();
		handoverInboundList.setData(handoverInboundUldTrollyService.fetchInboundTrolly(inboundTrolly));
		if (handoverInboundList.getData() == null) {
			throw new CustomException(FETCH_INBOUND_TROLLY_ERROR, "", ErrorType.ERROR);
		}
		LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "fetchInboundTrolly", Level.DEBUG,
				inboundTrolly, handoverInboundList));
		return new ResponseEntity<>(handoverInboundList, HttpStatus.OK);
	}

	/**
	 * This method is Add the Handover Inbound Trolly Details
	 * 
	 * @return ResponseEntity<BaseResponse<HandoverInboundTrolly>
	 * @throws CustomException
	 */
	@ApiOperation("Add the Handover Inbound Trolly Details")
	@RequestMapping(value = "/api/import/inboundtrolly/insertInbpoundTrolly", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<HandoverInboundTrolly>>> insertInbpoundTrolly(
			@ApiParam(value = "AddUpdateTrolly", required = true) @Validated({
					HandoverValidationGroup.class }) @RequestBody HandoverInboundTrolly handoverInbound)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HandoverInboundTrolly>> handoverInboundList = utilitiesModelConfiguration
				.getBaseResponseInstance();
		handoverInboundList.setData(handoverInboundUldTrollyService.insertInboundTrolly(handoverInbound));
		if (handoverInboundList.getData() == null) {
			throw new CustomException(FETCH_ADD_TROLLY_ERROR, "", ErrorType.ERROR);
		}
		LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "addUpdateTrolly", Level.DEBUG,
				handoverInbound, handoverInboundList));
		return new ResponseEntity<>(handoverInboundList, HttpStatus.OK);
	}

	/**
	 * This method is Update the Handover Inbound Trolly Details
	 * 
	 * @return ResponseEntity<BaseResponse<HandoverInboundTrolly>
	 * @throws CustomException
	 */
	@ApiOperation("Update the Handover Inbound Trolly Details")
	@RequestMapping(value = "/api/import/inboundtrolly/addUpdateTrolly", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<HandoverInboundTrolly>>> addUpdateTrolly(
			@ApiParam(value = "AddUpdateTrolly", required = true) @Validated({
					HandoverValidationGroup.class }) @RequestBody HandoverInboundTrolly handoverInbound)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HandoverInboundTrolly>> handoverInboundList = utilitiesModelConfiguration
				.getBaseResponseInstance();
		handoverInboundList.setData(handoverInboundUldTrollyService.addUpdateTrolly(handoverInbound));
		if (handoverInboundList.getData() == null) {
			throw new CustomException(FETCH_ADD_UPDATE_TROLLY_ERROR, "", ErrorType.ERROR);
		}
		LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "addUpdateTrolly", Level.DEBUG,
				handoverInbound, handoverInboundList));
		return new ResponseEntity<>(handoverInboundList, HttpStatus.OK);

	}

	/**
	 * This method is Delete the Service Number Details
	 * 
	 * @return ResponseEntity<BaseResponse<HandoverInboundContainerTrolly>
	 * @throws CustomException
	 */
	@ApiOperation("Delete the Inbound Trolly Details ")
	@RequestMapping(value = "/api/import/inboundtrolly/deleteTrollyNo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<HandoverInboundContainerTrolly>>> deleteTrollyNo(
			@ApiParam(value = "deleteTrollyNo", required = true) @RequestBody HandoverInboundContainerTrolly deleteTrollyDetails)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HandoverInboundContainerTrolly>> handoverInboundList = this.utilitiesModelConfiguration
				.getBaseResponseInstance();
		handoverInboundList.setData(handoverInboundUldTrollyService.deleteTrollyNo(deleteTrollyDetails));
		if (handoverInboundList.getData() == null) {
			throw new CustomException(FETCH_DELETE_TROLLY_NO_ERROR, "", ErrorType.ERROR);
		}
		LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "deleteTrollyNo", Level.DEBUG,
				deleteTrollyDetails, handoverInboundList));
		return new ResponseEntity<>(handoverInboundList, HttpStatus.OK);
	}

	/**
	 * This method edit Handover Inbound Trolly list information.
	 * 
	 * @return ResponseEntity<BaseResponse<HandoverInboundTrolly>
	 * @throws CustomException
	 */
	@ApiOperation("Edit the Inbound Trolly Details")
	@RequestMapping(value = "/api/import/inboundtrolly/editInboundTrolly", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse<List<HandoverInboundTrolly>>> editInboundTrolly(
			@ApiParam(value = "prelodgeShipment", required = true) @Valid @RequestBody HandoverInboundTrolly inboundTrolly)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<List<HandoverInboundTrolly>> handoverInboundList = utilitiesModelConfiguration
				.getBaseResponseInstance();
		handoverInboundList.setData(handoverInboundUldTrollyService.editInboundTrolly(inboundTrolly));
		if (handoverInboundList.getData() == null) {
			throw new CustomException(FETCH_EDIT_DATA_ERROR, "", ErrorType.ERROR);
		}
		LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "fetchExport", Level.DEBUG, inboundTrolly,
				handoverInboundList));
		return new ResponseEntity<>(handoverInboundList, HttpStatus.OK);
	}
}