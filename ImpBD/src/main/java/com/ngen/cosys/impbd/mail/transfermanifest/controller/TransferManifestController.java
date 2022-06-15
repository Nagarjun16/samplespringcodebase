/**
 * 
 * TransferManifestController.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * v1         14 April, 2017   NIIT      -
 */
package com.ngen.cosys.impbd.mail.transfermanifest.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.mail.manifest.controller.InboundMailManifestController;
import com.ngen.cosys.impbd.mail.transfermanifest.model.SearchTransferManifestDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierResponse;
import com.ngen.cosys.impbd.mail.transfermanifest.service.TransferManifestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
@RequestMapping("/api/mail/transfermanifest")
public class TransferManifestController {

	@Autowired
	private UtilitiesModelConfiguration utility;

	@Autowired
	private TransferManifestService service;

	@Autowired
	AirmailStatusEventProducer producer;

	private static final Logger logger = LoggerFactory.getLogger(InboundMailManifestController.class);

	/**
	 * @param fetches
	 *            transfer manifest details
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation(value = "fetch Transfer Manifest Details")
	@RequestMapping(value = "/fetchDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<List<TransferCarrierResponse>> fetchTransferManifestDetails(
			@ApiParam("fetch Transfer Manifest Details") @Valid @RequestBody SearchTransferManifestDetails search)
			throws CustomException {
		BaseResponse<List<TransferCarrierResponse>> baseResponse = utility.getBaseResponseInstance();
		List<TransferCarrierResponse> searchRes = service.fetchTransferManifestDetails(search);
		if (searchRes != null) {
			baseResponse.setData(searchRes);
		}
		return baseResponse;
	}

	/**
	 * @param saves
	 *            transfer manifest details
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation(value = "save Transfer Manifest Details")
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<List<TransferCarrierDetails>> save(
			@ApiParam("save Transfer Manifest Details") @Valid @RequestBody List<TransferCarrierDetails> request)
			throws CustomException {
		BaseResponse<List<TransferCarrierDetails>> baseResponse = utility.getBaseResponseInstance();
		List<TransferCarrierDetails> resp = service.saveTransferManifestDetails(request);
		if (resp != null) {
			baseResponse.setData(resp);
			AirmailStatusEventParentModel airmailStatusEventParentModel = new AirmailStatusEventParentModel();
			List<AirmailStatusEvent> events = new ArrayList<>();
			request.forEach(obj -> {
				AirmailStatusEvent event = new AirmailStatusEvent();
				event.setSourceTriggerType("TRANSFERMANIFEST");
				event.setCarrierCode(obj.getTransferCarrier());
				event.setTransferCarrierTo(obj.getTransferCarrier());
				event.setShipmentId(obj.getShipmentId());
				event.setMailBag(obj.getMailBagNumber());
				event.setNextDestination(obj.getNextDestination());
				event.setFlightId(new BigInteger(obj.getFlightId()));
				event.setPieces(BigInteger.valueOf(obj.getPieces()));
				event.setWeight(BigDecimal.valueOf(obj.getWeight()));
				event.setTenantId(obj.getTenantAirport());
				event.setCreatedBy(obj.getCreatedBy());
				event.setCreatedOn(obj.getCreatedOn());
				event.setStatus("CREATED");
				events.add(event);
			});
			airmailStatusEventParentModel.setAllMessage(events);
			producer.publish(airmailStatusEventParentModel);
		}
		return baseResponse;
	}
}