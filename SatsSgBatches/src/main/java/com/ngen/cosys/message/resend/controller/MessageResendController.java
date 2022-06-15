/**
 * {@link MessageResendController}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.message.resend.job.IncomingESBMessageResendJob;
import com.ngen.cosys.message.resend.job.IncomingFFMMessageResendJob;
import com.ngen.cosys.message.resend.job.IncomingFHLMessageResendJob;
import com.ngen.cosys.message.resend.job.IncomingFWBMessageResendJob;
import com.ngen.cosys.message.resend.job.OutgoingMessageResendJob;

import io.swagger.annotations.ApiOperation;

/**
 * Message Resend Controller for Outgoing & Incoming ESB related
 * 
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(path = "/message-resend/api/testing")
public class MessageResendController {

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private IncomingESBMessageResendJob incomingESBMessageResendJob;

	@Autowired
	private OutgoingMessageResendJob outgoingMessageResendJob;

	@Autowired
	private IncomingFFMMessageResendJob incomingFFMMessageResendJob;

	@Autowired
	private IncomingFWBMessageResendJob incomingFWBMessageResendJob;

	@Autowired
	private IncomingFHLMessageResendJob incomingFHLMessageResendJob;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Incoming ESB Message Resend Service")
	@PostRequest(value = "/incoming-esb", method = RequestMethod.POST)
	public BaseResponse<String> incomingESBMessageResendService() throws CustomException {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);
		incomingESBMessageResendJob.messageResendProcess();
		response.setData("Incoming ESB Message Resend Process completed successfully");
		return response;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Outgoing Message Resend Service")
	@PostRequest(value = "/outgoing", method = RequestMethod.POST)
	public BaseResponse<String> outgoingMessageResendService() throws CustomException {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);
		outgoingMessageResendJob.messageResendProcess();
		response.setData("Outgoing Message Resend Process completed successfully");
		return response;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Incoming FFM ESB Message Resend Service")
	@PostRequest(value = "/incoming-ffm-esb", method = RequestMethod.POST)
	public BaseResponse<String> incomingFFMESBMessageResendService() throws Exception {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);
		this.incomingFFMMessageResendJob.messageSequenceProcess();
		response.setData("Incoming FFM ESB Message Resend Process completed successfully");
		return response;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Incoming FWB ESB Message Resend Service")
	@PostRequest(value = "/incoming-fwb-esb", method = RequestMethod.POST)
	public BaseResponse<String> incomingFWBESBMessageResendService() throws CustomException {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);
		// Re-Processing
		this.incomingFWBMessageResendJob.messageSequenceReProcess();
		// Sequence Process
		this.incomingFWBMessageResendJob.messageSequenceProcess();
		response.setData("Incoming FWB ESB Message Resend Process completed successfully");
		return response;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Incoming FHL ESB Message Resend Service")
	@PostRequest(value = "/incoming-fhl-esb", method = RequestMethod.POST)
	public BaseResponse<String> incomingFHLESBMessageResendService() throws CustomException {
		BaseResponse<String> response = beanFactory.getBean(BaseResponse.class);
		// Re-Processing
		this.incomingFHLMessageResendJob.messageSequenceReProcess();
		// Sequence Process
		this.incomingFHLMessageResendJob.messageSequenceProcess();
		response.setData("Incoming FHL ESB Message Resend Process completed successfully");
		return response;
	}
}