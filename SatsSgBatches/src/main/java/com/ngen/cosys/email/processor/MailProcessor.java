/**
 * 
 * MailProcessor.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 17 JUN, 2018 NIIT -
 */
package com.ngen.cosys.email.processor;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.email.config.MailProperties;
import com.ngen.cosys.email.validator.MailValidator;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.template.engine.TemplateEngine;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mail.config.MailConfig;
import com.ngen.cosys.mail.config.MailProps;

/**
 * Email functionality component for Mail Processor Implementation
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component("SatsSgBatchesMailProcessor")
public class MailProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailProcessor.class);

   @Autowired
   MailClient mailClient;

   @Autowired
   MailValidator validator;
   
   @Autowired
   MailConfig mailConfig;

   @Autowired
   @Qualifier("templateEngineNGC")
   TemplateEngine templateEngine;

   /**
    * @param mailBO
    * @throws CustomException
    */
   public boolean sendEmail(EMailEvent emailEvent) throws CustomException {
      //
      boolean mailSent = false;
      if (!validator.validate(emailEvent)) {
         LOGGER.debug("Mail Processor Send Email Validation not successful {} ", emailEvent);
         return false;
      }
      //
      templateEngine.constructMailTemplate(emailEvent);
      String channelConfig = !StringUtils.isEmpty(emailEvent.getChannelConfig()) // 
            ? emailEvent.getChannelConfig() //
            : "STANDARD_MAIL";
      MailProps mailProps = mailConfig.getConfiguredMailProperties(channelConfig);
      //
      if (Objects.nonNull(mailProps)) {
         MailProperties.setMailConfig(mailProps);
         boolean mailConnect = false;
         if (Objects.nonNull(MailProperties.properties)) {
            mailConnect = mailClient.connect(MailProperties.properties);
         }
         // Mail Connect is successful then Email
         if (mailConnect) {
            mailSent = mailClient.send(emailEvent);
         }
      }
      // Send acknowledgement
      return mailSent;
   }

}