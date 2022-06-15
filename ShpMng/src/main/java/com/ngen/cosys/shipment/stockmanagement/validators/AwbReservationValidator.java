package com.ngen.cosys.shipment.stockmanagement.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;

@Component
public class AwbReservationValidator {
	

	public void checkValidationForSearch(AwbReservationSearch search) {
		if(StringUtils.isEmpty(search.getDestination()) || search.getDestination()==null) {
			search.addError("g.required", "searchReservedFormGroup.destination", ErrorType.ERROR);
		}
		if(StringUtils.isEmpty(search.getCore()) || search.getCore()==null) {
			search.addError("g.required", "searchReservedFormGroup.core", ErrorType.ERROR);
		}
		if(search.getTerminalId()==null) {
			search.addError("g.required", "searchReservedFormGroup.terminalId", ErrorType.ERROR);
		}
		search.setErrorFlag(true);
	}
	
	 public void checkValidationForSave(AwbReservationSearch search) {
		if(StringUtils.isEmpty(search.getDestination()) || search.getDestination()==null) {
			search.addError("g.required", "awbReservationGroup.destination", ErrorType.ERROR);
		}
		if(StringUtils.isEmpty(search.getCore()) || search.getCore()==null) {
			search.addError("g.required", "awbReservationGroup.core", ErrorType.ERROR);
		}
		if(search.getTerminalId()==null) {
			search.addError("g.required", "awbReservationGroup.terminalId", ErrorType.ERROR);
		}
		search.setErrorFlag(true);
	}
	
}
