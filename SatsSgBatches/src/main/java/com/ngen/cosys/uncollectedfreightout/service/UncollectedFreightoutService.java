package com.ngen.cosys.uncollectedfreightout.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;

public interface UncollectedFreightoutService {

	void sendDateForUncollectedFreightout(UncollectedFreightoutShipmentModel requestModel) throws CustomException;
	List<String> getEmailAddress() throws CustomException;

}
