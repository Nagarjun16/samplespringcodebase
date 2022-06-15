package com.ngen.cosys.aed.validators;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ngen.cosys.aed.model.Header;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ResponseModel;

@Component
public class HeaderValidations {

	private HeaderValidations() {
		// empty
	}

	static ResponseModel responseModel = null;

	public static ResponseModel modelValidation(Object o) {

		Header model = (Header) o;

		if (model.getMessageId() == null) {
			setErrorcode();
			responseModel.setErrorDescription("messageId con not be null");
		} else if (String.valueOf(model.getMessageId()).length() > 17) {
			setErrorcode();
			responseModel.setErrorDescription("MESSAGE_ID can't be more than 17 Characters");
		}

		else if (model.getMessageType() == null) {
			setErrorcode();
			responseModel.setErrorDescription("messageType con not be null");
		} else if (String.valueOf(model.getMessageType()).length() > 20) {
			setErrorcode();
			responseModel.setErrorDescription("MESSAGE_TYPE can't be more than 20 Characters");
		}

		else if (model.getSendDateTime() == null) {
			setErrorcode();
			responseModel.setErrorDescription("SEND_DATETIME con not be null");
		}

		return responseModel;

	}

	private static void setErrorcode() {
		responseModel = new ResponseModel();
		responseModel.setStatus(ResponseStatus.FAIL);
		responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
	}
}
