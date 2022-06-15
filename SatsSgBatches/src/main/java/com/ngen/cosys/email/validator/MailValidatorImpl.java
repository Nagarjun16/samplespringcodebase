/**
 * 
 * MailValidatorImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          17 JUN, 2018   NIIT      -
 */
package com.ngen.cosys.email.validator;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.util.LoggerUtil;

/**
 * Mail Validator for Mail Service
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class MailValidatorImpl implements MailValidator {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailValidatorImpl.class);
   
   /**
    * @see com.ngen.cosys.email.validator.MailValidator#validate(com.ngen.cosys.framework.model.BaseBO)
    * 
    */
   @Override
   public boolean validate(EMailEvent emailEvent) throws CustomException {
      //
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "MailValidator validate method",
            Level.DEBUG, null, null));
      // Validate Mail Header | Validate Mail Body | Validate Mail Template
      return (validateMailHeader(emailEvent) && (validateMailBody(emailEvent) || validateTemplate(emailEvent)));
   }

   /**
    * @param emailEvent
    * @return
    */
   private boolean validateMailHeader(EMailEvent emailEvent) {
      // Mail FromAddress & Sendar Name is Mandatory 
      // Either ToAddress(TO)/CC/BCC or ToAddresses(ToAddress)/CCAddress/BCCAddress
      // Removed: !StringUtils.isEmpty(emailEvent.getMailFrom()) && !StringUtils.isEmpty(emailEvent.getSenderName())  
      if (((!StringUtils.isEmpty(emailEvent.getMailTo()) || !StringUtils.isEmpty(emailEvent.getMailCC())
            || !StringUtils.isEmpty(emailEvent.getMailBCC()))
            || ((Objects.nonNull(emailEvent.getMailToAddress()) && emailEvent.getMailToAddress().length > 0)
                  || (Objects.nonNull(emailEvent.getMailCCAddress()) && emailEvent.getMailCCAddress().length > 0)
                  || (Objects.nonNull(emailEvent.getMailBCCAddress()) && emailEvent.getMailBCCAddress().length > 0)))) {
         return true;
      }
      return false;
   }

   /**
    * @param emailEvent
    * @return
    */
   private boolean validateMailBody(EMailEvent emailEvent) {
      // Mail Subject & Mail Body is Mandatory
      return !StringUtils.isEmpty(emailEvent.getMailSubject()) && !StringUtils.isEmpty(emailEvent.getMailBody());
   }

   /**
    * @param emailEvent
    * @return
    */
   private boolean validateTemplate(EMailEvent emailEvent) {
      // Template Name is Mandatory
      return Objects.nonNull(emailEvent.getTemplate())
            && !StringUtils.isEmpty(emailEvent.getTemplate().getTemplateName());
   }
	
}
