/**
 * {@link MailResendService}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.email.service;

import java.util.List;

import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Mail Resend Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface MailResendService {

   /**
    * GET List of Failed mail details
    * 
    * @return
    * @throws CustomException
    */
   List<MailResendDetail> getListOfFailedMailDetails() throws CustomException;
   
   /**
    * GET List of failed wrong mail address details
    * 
    * @return
    * @throws CustomException
    */
   List<MailResendDetail> getListOfFailedWrongMailAddressDetails() throws CustomException;
   
   /**
    * Resend Mail Status
    * 
    * @param resendMailDetails
    * @throws CustomException
    */
   void updateResendMailStatus(List<MailResendDetail> resendMailDetails) throws CustomException;
   
   /**
    * GET Notify Mail Address for rejected mails
    * 
    * @return
    * @throws CustomException
    */
   String getNotifyMailAddressForRejectedMails() throws CustomException;
   
}
