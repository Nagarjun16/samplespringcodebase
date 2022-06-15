package com.ngen.cosys.etqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.etqs.dao.ETQSDao;
import com.ngen.cosys.etqs.model.ETQSShipmentInfo;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class ETQSServiceImpl implements ETQSService {

	@Autowired
	ETQSDao etqsDao;

	@Override
	public ETQSShipmentInfo getServcieNumber(ETQSShipmentInfo requestModel) throws CustomException {

		return etqsDao.fetchServcieNumber(requestModel);
	}

	@Override
	public ETQSShipmentInfo updateQueeNumber(ETQSShipmentInfo requestModel) throws CustomException {

		return etqsDao.updateQueeNumber(requestModel);
	}

}
