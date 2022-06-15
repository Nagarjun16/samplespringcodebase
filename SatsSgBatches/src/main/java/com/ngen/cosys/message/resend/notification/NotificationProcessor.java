/**
 * {@link NotificationProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.notification;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base Notification Processor
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class NotificationProcessor {

   protected static final Logger LOGGER = LoggerFactory.getLogger(NotificationProcessor.class);
   
   @Autowired
   SendEmailEventProducer publisher;
   
   /**
    * @param messageNotifications
    * @param template
    * @param mailSubject
    * @throws CustomException
    */
   protected void sendEmail(List<MessageNotification> messageNotifications, String template, String mailSubject,
         String mailBody, List<String> mailingList) throws CustomException {
      LOGGER.info("Notification Processor - Template - {}", template);
      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailSubject(mailSubject);
      emailEvent.setMailBody(getMailBodyContent(messageNotifications, mailBody));
      if (!CollectionUtils.isEmpty(mailingList)) {
         emailEvent.setMailToAddress(Arrays.copyOf(mailingList.toArray(), mailingList.size(), String[].class));
      } else {
         emailEvent.setMailTo("vigneshshiv@niit-tech.com");
      }
      emailEvent.setTemplate(getTemplate(template));
      publisher.publish(emailEvent);
   }
   
   /**
    * @param messageNotifications
    * @return
    */
   private String getMailBodyContent(List<MessageNotification> messageNotifications, String mailBody) {
      // TODO
      return mailBody;
   }
   
   /**
    * @param template
    * @return
    */
   private TemplateBO getTemplate(String template) {
      TemplateBO templateBO = new TemplateBO();
      templateBO.setTemplateName(template);
      return templateBO;
   }
   
}
