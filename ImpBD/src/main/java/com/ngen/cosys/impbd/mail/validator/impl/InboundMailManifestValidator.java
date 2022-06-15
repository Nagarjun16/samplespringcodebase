package com.ngen.cosys.impbd.mail.validator.impl;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.validator.MailValidator;

@Component
public class InboundMailManifestValidator implements MailValidator {

	@Override
	public void validate(BaseBO baseBO) throws CustomException {
		InboundMailManifestModel manifestModel = (InboundMailManifestModel) baseBO;
		// Validate the Document Model
	}

}