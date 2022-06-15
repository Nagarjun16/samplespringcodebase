package com.ngen.cosys.etqs.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.etqs.model.ETQSShipmentInfo;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class ETQSDaoImpl extends BaseDAO implements ETQSDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	private static final Logger LOGGER = LoggerFactory.getLogger(ETQSDaoImpl.class);

	@Override
	public ETQSShipmentInfo fetchServcieNumber(ETQSShipmentInfo requestModel) throws CustomException {

		return this.fetchObject("getServiceInfo", requestModel, sqlSessionShipment);
	}

	@Override
	public ETQSShipmentInfo updateQueeNumber(ETQSShipmentInfo requestModel) throws CustomException {

		this.updateData("updatePrelodgeService", requestModel, sqlSessionShipment);

		return requestModel;
	}

}
