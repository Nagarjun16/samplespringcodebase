/**
 * 
 * ProduceSingPostMessageController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 4 May, 2018 NIIT -
 */
package com.ngen.cosys.satssg.interfaces.singpost.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.processor.SingPostIncomingMessageProcessor;
import com.ngen.cosys.satssg.interfaces.singpost.producer.SingPostIncomingMessagesProducer;

/**
 * This is the entry point to process the incoming SINGPOST messages.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/api/singpost")
public class ProduceSingPostMessageController {

	@Autowired
	SingPostIncomingMessagesProducer produce;
	
	private static final Logger logger = LoggerFactory.getLogger(SingPostIncomingMessageProcessor.class);

	@RequestMapping(value = "/pullMailBagStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> incomingMessages(@RequestBody List<MailBagResponseModel> payload,
			HttpServletRequest request) throws CustomException {
		PullMailBagResponseModel event = new PullMailBagResponseModel();
		event.setMailBag(payload);
        logger.warn("before singpost incoming message event producing");
		produce.publish(event);
		logger.warn("after singpost incoming message event producing and status sent to esb");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}