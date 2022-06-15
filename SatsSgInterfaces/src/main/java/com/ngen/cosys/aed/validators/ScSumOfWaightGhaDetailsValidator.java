package com.ngen.cosys.aed.validators;

import org.springframework.http.HttpStatus;

import com.ngen.cosys.aed.model.ScSumOfWtGhaRequestDetails;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ResponseModel;

public class ScSumOfWaightGhaDetailsValidator {

	private ScSumOfWaightGhaDetailsValidator() {

	}

	static ResponseModel responseModel = null;

	public static ResponseModel validate(ScSumOfWtGhaRequestDetails details) {

		if (details.getScSumOfWtghaId() == null) {
			setErrorcode();
			responseModel.setErrorDescription("SCSUMOFWTGHA_ID con not be null");
		} else if (String.valueOf(details.getScSumOfWtghaId()).length() > 24) {
			setErrorcode();
			responseModel.setErrorDescription("SCSUMOFWTGHA_ID can't be more than 24 Characters");
		} else if (details.getRecordInd() == null) {
			setErrorcode();
			responseModel.setErrorDescription("RECORD_IND con not be null");
		} else if (String.valueOf(details.getRecordInd()).length() > 1) {
			setErrorcode();
			responseModel.setErrorDescription("RECORD_IND can't be more than 1 Characters");
		} else if (details.getMawbNo() == null) {
			setErrorcode();
			responseModel.setErrorDescription("MAWB_NO con not be null");
		} else if (String.valueOf(details.getMawbNo()).length() > 35) {
			setErrorcode();
			responseModel.setErrorDescription("MAWB_NO can't be more than 35 Characters");
		} else if (details.getSumTotalGrossWt() == null) {
			setErrorcode();
			responseModel.setErrorDescription("messageId con not be null");
		} else if (String.valueOf(details.getSumTotalGrossWt()).length() > 15) {
			// setErrorcode();
			// responseModel.setErrorDescription("SUM_TOTAL_GROSS_WT can't be more than 15
			// Characters");
		} else if (details.getSumTotalGrossWtUom() == null) {
			setErrorcode();
			responseModel.setErrorDescription("SUM_TOTAL_GROSS_WT_UOM con not be null");
		} else if (String.valueOf(details.getSumTotalGrossWtUom()).length() > 3) {
			setErrorcode();
			responseModel.setErrorDescription("SUM_TOTAL_GROSS_WT_UOM can't be more than 3 Characters");
		} else if (details.getScPecentTollerence() == null) {
			setErrorcode();
			responseModel.setErrorDescription("SC_PERCENT_TOLERANCE con not be null");
		} else if (String.valueOf(details.getScPecentTollerence()).length() > 3) {
			// setErrorcode();
			// responseModel.setErrorDescription("SC_PERCENT_TOLERANCE can't be more than 3
			// Characters");
		}

		return responseModel;

	}

	private static void setErrorcode() {
		responseModel = new ResponseModel();
		responseModel.setStatus(ResponseStatus.FAIL);
		responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
	}

}
