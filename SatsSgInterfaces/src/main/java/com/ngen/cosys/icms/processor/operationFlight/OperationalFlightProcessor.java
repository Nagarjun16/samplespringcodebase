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

package com.ngen.cosys.icms.processor.operationFlight;

import java.sql.SQLException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.processor.BaseFlightProcessor;
import com.ngen.cosys.icms.schema.operationFlight.FlightOperation;
import com.ngen.cosys.icms.schema.operationFlight.OperationalFlightPublish;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
import com.ngen.cosys.icms.exception.MessageParseException;

/**
 * This Class is used for processing Operational Flight messages.
 *
 */

@Component
public class OperationalFlightProcessor extends BaseFlightProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationalFlightProcessor.class);
	@Autowired
	private OperationalFlightProcessorHelper flightProcessorHelper;

	public ICMSResponseModel processOperationalFlightMessage(OperationalFlightInfo flightInfo)
			throws SQLException, MessageProcessingException {
		LOGGER.info("start processOperationalFlightMessage method ::");
		ICMSResponseModel icmsResponse = new ICMSResponseModel();
		try {
			// Business validations
			validateAircraftType(flightInfo);
			validateServiceTypeAndFllightType(flightInfo);
			validateCarrierCode(flightInfo.getCarrier());
			validateLegOrigin(flightInfo);
			validateLegDestination(flightInfo);
			flightProcessorHelper.validateLegOrder(flightInfo.getFlightLegInfo());
			// check flight overlapping and insert/update Flight details into DB tables on
			// the basis of flightStatus
			if (ValidationConstant.STATUS_ACTIVE.equalsIgnoreCase(flightInfo.getFlightStatus())
					|| ValidationConstant.STATUS_TBA.equalsIgnoreCase(flightInfo.getFlightStatus())) {
				this.checkConditionsForActiveFlightStatus(flightInfo);
			} else if (ValidationConstant.STATUS_CAN.equalsIgnoreCase(flightInfo.getFlightStatus())) {
				this.checkConditionsForCancelFlightStatus(flightInfo);

			} else {
				throw new MessageProcessingException("Flight status doesn't exist");
			}
		} catch (Exception e) {
			LOGGER.error("Method End processOperationalFlightMessage->  Exception :: " + e.getMessage());
			e.printStackTrace();
			icmsResponse.setHttpStatus(HttpStatus.CONFLICT);
			icmsResponse.setErrorMessage(e.getMessage());
			return icmsResponse;
		}
		LOGGER.info("End processOperationalFlightMessage method ::");
		icmsResponse.setHttpStatus(HttpStatus.OK);
		return icmsResponse;
	}

	private void checkConditionsForCancelFlightStatus(OperationalFlightInfo flightInfo) {
		if (operationalFlightDao.validationForActiveFlight(flightInfo) == 1) {
			if (!flightProcessorHelper.isValidWorkedOnCondition(flightInfo)) {
				this.operationalFlightDao.cancelFltOperationalFlight(flightInfo);
			} else {
				throw new MessageProcessingException("Flight is Worked On");
			}
		} else if (operationalFlightDao.validationForCancelFlight(flightInfo) == 1) {
			throw new MessageProcessingException("Flight is already cancelled");
		} else {
			throw new MessageProcessingException("Flight doesn't exist");
		}
	}

	private void checkConditionsForActiveFlightStatus(OperationalFlightInfo flightInfo) throws ParseException,MessageProcessingException {
		if (flightProcessorHelper.validateActiveFlightOverlapping(flightInfo)) {
			flightProcessorHelper.updateFltOperationalFlight(flightInfo);
		} else if (flightProcessorHelper.validateCancelFlightOverlapping(flightInfo)) {
			operationalFlightDao.deleteExistingCancelFlight(flightInfo);
			flightProcessorHelper.checkForAutoFlight(flightInfo);
			operationalFlightDao.insertFltOperationalFlight(flightInfo);
		} else {
			if(flightProcessorHelper.validateFlightOverlapping(flightInfo)) {
				flightProcessorHelper.checkForAutoFlight(flightInfo);
				operationalFlightDao.insertFltOperationalFlight(flightInfo);
			}
		}
	}

	/**
	 * This method contains business validations
	 * 
	 * @param flightSchedulePublish
	 * @return
	 * @throws MessageParseException
	 * @throws ParseException
	 * @throws ParseException
	 */

	public OperationalFlightPublish businessValidations(OperationalFlightPublish operationalFlightPublish)
			throws MessageParsingException, ParseException {
		FlightOperation flightOperation = operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation();
		// XML parsing validations
		validationForFlightNumberAndDate(flightOperation.getFlightNumber(), flightOperation.getFlightDate(), null);
		return operationalFlightPublish;
	}

}
