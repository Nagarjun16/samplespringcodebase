/*******************************************************************************
 * Copyright (c) 2021 Coforge Technologies PVT LTD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ngen.cosys.icms.controller;

import com.esotericsoftware.minlog.Log;
/**
 *This controller takes care of the incoming requests from ICMS system for eg:SSM,ASM
 */
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
//import com.ngen.cosys.icms.model.BookingPublishDetails;
import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.service.InboundIcmsService;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;

import io.swagger.annotations.ApiOperation;

import java.math.BigInteger;
import java.util.HashMap;
//import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.apache.commons.codec.binary.Base64;

@NgenCosysAppInfraAnnotation(path = "/api/cmd")
public class InboundIcmsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundIcmsController.class);

	@Autowired
	InboundIcmsService inboundIcmsService;

	/**
	 * Process all inbound ICMS messages for eg: Flight Incoming Message (ASM/SSM)
	 * 
	 * @param requestPayload
	 * @param request
	 * @return
	 */
	@ApiOperation("API method to process incoming message")
	@PostMapping(path = "icms/message/inbound", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ICMSResponseModel> processInboundMessage(@RequestBody String requestPayload,
			HttpServletRequest request, @RequestHeader final Map<String, String> headerValueMap) {
		LOGGER.info("Method Start InboundIcmsController-> processFlightMessage()-> RequestPayload :" + requestPayload);
		BigInteger messageId = BigInteger.ZERO;
		LOGGER.info("Input Recieved" + requestPayload);
		ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
		HashMap<String, String> queryParam = new HashMap<>();
		 // get the start time
	    long start = System.nanoTime();
	    LOGGER.info("Method InboundIcmsController Execution Start time "+start);
		if (!FeatureUtility.isFeatureEnabled(ApplicationFeatures.Icms.IncomingMessage.class)) {

			LOGGER.info("Method End InboundIcmsController-> processFlightMessage(), ICMS FEATURE SWITCHED OFF : {}",
					HttpStatus.OK);
			return new ResponseEntity<>(icmsResponseModel, HttpStatus.OK);
		} else {
			if (headerValueMap.get(ValidationConstant.IS_EDI_SCREEN) != null
					&& headerValueMap.get(ValidationConstant.IS_EDI_SCREEN).equals(ValidationConstant.TRUE)) {
				queryParam.put(ValidationConstant.IS_EDI_SCREEN, ValidationConstant.TRUE);
				queryParam.put(ValidationConstant.LOGIN_USER, headerValueMap.get(ValidationConstant.LOGIN_USER));
			}
			if (requestPayload != null && !requestPayload.isEmpty()) {
				try {

					if (Base64.isBase64(requestPayload)) {
						requestPayload = new String(Base64.decodeBase64(requestPayload.getBytes()));
					}
				} catch (Exception e) {

					LOGGER.error("Unable to decode Base64 encoded payload", e);
					icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
					return new ResponseEntity<>(icmsResponseModel, icmsResponseModel.getHttpStatus());
				}
			}
			icmsResponseModel = inboundIcmsService.processIncomingMessage(requestPayload, messageId, queryParam);
			icmsResponseModel.setHttpStatus(HttpStatus.OK);
			// get the end time
		    long end = System.nanoTime();
		    LOGGER.info("Method InboundIcmsController Execution End time "+end);
		    // execution time in seconds
		    long execution = (end - start);
		    System.out.println("Execution time of Recursive Method is "+execution+" nanoseconds");
		    LOGGER.info("Method InboundIcmsController Execution time of Recursive Method is"+execution+" nanoseconds");
			LOGGER.info("Method End InboundIcmsController-> processFlightMessage(), http Response : {}",
					icmsResponseModel.getHttpStatus());
			return new ResponseEntity<>(icmsResponseModel, icmsResponseModel.getHttpStatus());

		}
	}

}
