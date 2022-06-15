package com.ngen.cosys.impbd.mail.validator.impl;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.validator.MailValidator;

@Component
public class InboundMailDocumentValidator implements MailValidator {

	@Override
	public void validate(BaseBO baseBO) throws CustomException {
		InboundMailDocumentModel documentModel = (InboundMailDocumentModel) baseBO;
		// Validate the Document Model
	}

}