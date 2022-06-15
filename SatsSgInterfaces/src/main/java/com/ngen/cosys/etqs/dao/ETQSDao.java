package com.ngen.cosys.etqs.dao;

import org.springframework.stereotype.Repository;

import com.ngen.cosys.etqs.model.ETQSShipmentInfo;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public interface ETQSDao {
	ETQSShipmentInfo fetchServcieNumber(ETQSShipmentInfo requestModel) throws CustomException;
	
	ETQSShipmentInfo updateQueeNumber(ETQSShipmentInfo requestModel) throws CustomException;
}
