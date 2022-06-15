/**
 * {@link MailResendServiceImpl}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.email.dao.MailResendDAO;
import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Mail Resend Service Impl
 * 
 * @author NIIT Technologies Ltd.
 */
@Service
public class MailResendServiceImpl implements MailResendService {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailResendService.class);
   
   @Autowired
   MailResendDAO mailResendDAO;
   
   /**
    * @see com.ngen.cosys.email.service.MailResendService#getListOfFailedMailDetails()
    */
   @Override
   public List<MailResendDetail> getListOfFailedMailDetails() throws CustomException {
      LOGGER.debug("Mail Resend Service :: GET List of Failed Mail Details - {}");
      return mailResendDAO.getListOfFailedMailDetails();
   }

   /**
    * @see com.ngen.cosys.email.service.MailResendService#getListOfFailedWrongMailAddressDetails()
    */
   @Override
   public List<MailResendDetail> getListOfFailedWrongMailAddressDetails() throws CustomException {
      LOGGER.debug("Mail Resend Service :: GET List of Failed Wrong Mail Address Details - {}");
      return mailResendDAO.getListOfFailedWrongMailAddressDetails();
   }

   /**
    * @see com.ngen.cosys.email.service.MailResendService#updateResendMailStatus(java.util.List)
    */
   @Override
   public void updateResendMailStatus(List<MailResendDetail> resendMailDetails) throws CustomException {
      LOGGER.debug("Mail Resend Service :: RESEND mail status - {}");
      mailResendDAO.updateResendMailStatus(resendMailDetails);
   }

   /**
    * @see com.ngen.cosys.email.service.MailResendService#getNotifyMailAddressForRejectedMails()
    */
   @Override
   public String getNotifyMailAddressForRejectedMails() throws CustomException {
      LOGGER.debug("Mail Resend Service :: NOTIFY mail address for rejected mails - {}");
      return mailResendDAO.getNotifyMailAddressForRejectedMails();
   }

}
