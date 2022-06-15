package com.ngen.cosys.impbd.mail.validator;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;

public interface MailValidator {

	void validate(BaseBO baseBO) throws CustomException;

}
