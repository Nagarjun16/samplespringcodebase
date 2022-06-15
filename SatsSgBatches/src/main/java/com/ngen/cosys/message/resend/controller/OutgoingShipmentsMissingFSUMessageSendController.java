/**
 * Test API Service for re-firing FSU message events for export shipments which was originally missed out
 */
package com.ngen.cosys.message.resend.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.message.resend.job.OutgoingShipmentsMissingFSUMessageSendJob;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/fsu/api/refire")
public class OutgoingShipmentsMissingFSUMessageSendController {
	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private OutgoingShipmentsMissingFSUMessageSendJob job;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Outgoing Shipments Missing FSU Message Send Service")
	@RequestMapping(value = "/outgoing-shipments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<String> refireFSU() throws CustomException {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);

		this.job.refireMessage();

		response.setData("Outgoing Shipments Missing FSU Message Send Initiated Successfully");
		return response;
	}
}