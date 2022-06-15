/**
 * {@link MailResendDAO}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.dao;

import java.util.List;

import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Mail Resend DAO
 * 
 * @author NIIT Technologies Ltd.
 */
public interface MailResendDAO {

   /**
    * GET List of Failed mail details
    * 
    * @return
    * @throws CustomException
    */
   List<MailResendDetail> getListOfFailedMailDetails() throws CustomException;
   
   /**
    * GET list of failed wrong mail address details
    * 
    * @return
    * @throws CustomException
    */
   List<MailResendDetail> getListOfFailedWrongMailAddressDetails() throws CustomException;
   
   /**
    * Update Resend Mail Status
    * 
    * @param resendMailDetails
    * @throws CustomException
    */
   void updateResendMailStatus(List<MailResendDetail> resendMailDetails) throws CustomException;
   
   /**
    * GET Notify mail address for Rejected mails
    * 
    * @return
    * @throws CustomException
    */
   String getNotifyMailAddressForRejectedMails() throws CustomException;
   
}
