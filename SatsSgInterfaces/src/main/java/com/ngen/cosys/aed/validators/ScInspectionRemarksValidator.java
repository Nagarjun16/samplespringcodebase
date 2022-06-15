package com.ngen.cosys.aed.validators;

import org.springframework.http.HttpStatus;

import com.ngen.cosys.aed.model.ScInspecRmkGhaRequestDetails;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ResponseModel;

public class ScInspectionRemarksValidator {

	private ScInspectionRemarksValidator() {
		// empty
	}

	static ResponseModel responseModel = null;

	public static ResponseModel validate(ScInspecRmkGhaRequestDetails details) {

		if (details.getScSumOfWtghaId() == null) {
			setErrorcode();
			responseModel.setErrorDescription("SCINSPECTRMKGHA_ID con not be null");
		} else if (String.valueOf(details.getScSumOfWtghaId()).length() > 24) {
			setErrorcode();
			responseModel.setErrorDescription("SCINSPECTRMKGHA_ID can't be more than 24 Characters");
		} else if (details.getMawbNo() == null) {
			setErrorcode();
			responseModel.setErrorDescription("MAWB_NO con not be null");
		} else if (String.valueOf(details.getMawbNo()).length() > 35) {
			setErrorcode();
			responseModel.setErrorDescription("MAWB_NO can't be more than 35 Characters");
		} else if (details.getPermitNo() == null) {
			setErrorcode();
			responseModel.setErrorDescription("PERMIT_NO con not be null");
		} else if (String.valueOf(details.getPermitNo()).length() > 11) {
			setErrorcode();
			responseModel.setErrorDescription("PERMIT_NO can't be more than 11 Characters");
		}

		return responseModel;

	}

	private static void setErrorcode() {
		responseModel = new ResponseModel();
		responseModel.setStatus(ResponseStatus.FAIL);
		responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
	}
}
