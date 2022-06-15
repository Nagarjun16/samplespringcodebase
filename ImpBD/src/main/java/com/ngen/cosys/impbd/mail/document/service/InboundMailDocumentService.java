package com.ngen.cosys.impbd.mail.document.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentShipmentModel;

public interface InboundMailDocumentService {

	public InboundMailDocumentModel search(InboundMailDocumentModel requestModel) throws CustomException;

	public InboundMailDocumentModel documentIn(InboundMailDocumentModel requestModel) throws CustomException;
	
	public void update(InboundMailDocumentModel requestModel) throws CustomException;
	
	public List<BigInteger> getDispatchYears() throws CustomException;
	
	void validate(InboundMailDocumentShipmentModel requestModel) throws CustomException;

}