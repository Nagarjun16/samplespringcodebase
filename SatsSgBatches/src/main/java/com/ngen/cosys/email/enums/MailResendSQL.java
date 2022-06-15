/**
 * {@link MailResendSQL}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.enums;

/**
 * Mail Resend SQL's
 *
 * @author NIIT Technologies Ltd.
 */
public enum MailResendSQL {

   SELECT_FAILED_MAIL_DETAILS("sqlSelectFailedMailDetails"), //
   SELECT_FAILED_WRONG_MAIL_ADDRESS_DETAILS("sqlSelectFailedWrongMailAddressDetails"), //
   UPDATE_RESEND_MAIL_STATUS("sqlUpdateResendMailStatus"), //
   NOTIFY_MAIL_ADDRESS_FOR_REJECTED_MAILS("sqlNotifyMailAddressForRejectedMails");
   
   private String queryId;
   
   /**
    * @param value
    */
   private MailResendSQL(String value) {
      this.queryId = value;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
