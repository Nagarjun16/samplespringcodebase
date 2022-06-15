package com.ngen.cosys.application.service;

import java.util.List;

import com.ngen.cosys.application.model.TSMFreightoutModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface TSMFreightoutServiceJob {

	List<TSMFreightoutModel> getShipmentInfoForFreightOut() throws CustomException;

	List<TSMFreightoutModel> getTSMOutgoingInventoryDetails(TSMFreightoutModel requestModel) throws CustomException;

	void tsmInventoryToFreightOut(TSMFreightoutModel requestModel) throws CustomException;

}
