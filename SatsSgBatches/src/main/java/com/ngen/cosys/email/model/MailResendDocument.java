/**
 * {@link MailResendDocument}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mail Resend Document log
 * 
 * @author NIIT Technologies Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
public class MailResendDocument {
   //
   private BigInteger messageLogId;
   //
   private BigInteger documentLogId;
   private BigInteger documentId;
   private String documentName;
   private String documentFormat;
   private String documentData;
   
}
