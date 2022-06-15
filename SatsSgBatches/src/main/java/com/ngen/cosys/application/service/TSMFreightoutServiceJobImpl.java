package com.ngen.cosys.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.TSMFreightoutJobDao;
import com.ngen.cosys.application.model.TSMFreightoutModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class TSMFreightoutServiceJobImpl implements TSMFreightoutServiceJob{
	
	@Autowired
	TSMFreightoutJobDao freightOutDao;

	@Override
	public List<TSMFreightoutModel> getShipmentInfoForFreightOut() throws CustomException {
		
		return freightOutDao.getShipmentInfoForFreightOut();
	}

	@Override
	public List<TSMFreightoutModel> getTSMOutgoingInventoryDetails(TSMFreightoutModel requestModel)
			throws CustomException {

		return freightOutDao.getTSMOutgoingInventoryDetails(requestModel);
	}

	@Override
	public void tsmInventoryToFreightOut(TSMFreightoutModel requestModel) throws CustomException {
		freightOutDao.tsmInventoryToFreightOut(requestModel);
	}

}
