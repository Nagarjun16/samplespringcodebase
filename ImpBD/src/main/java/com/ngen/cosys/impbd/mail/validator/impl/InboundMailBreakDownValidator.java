package com.ngen.cosys.impbd.mail.validator.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.validator.MailValidator;

@Component
public class InboundMailBreakDownValidator implements MailValidator {

   private String form = "mailBreakdownWorklistForm";

   @Override
   public void validate(BaseBO baseBO) throws CustomException {
      InboundMailBreakDownModel breakDownModel = (InboundMailBreakDownModel) baseBO;

   }

}