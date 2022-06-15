package com.ngen.cosys.etqs.service;

import org.springframework.stereotype.Service;

import com.ngen.cosys.etqs.model.ETQSShipmentInfo;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public interface ETQSService {
	
	ETQSShipmentInfo getServcieNumber(ETQSShipmentInfo requestModel) throws CustomException;
	
	ETQSShipmentInfo updateQueeNumber(ETQSShipmentInfo requestModel) throws CustomException;
	
	
}
