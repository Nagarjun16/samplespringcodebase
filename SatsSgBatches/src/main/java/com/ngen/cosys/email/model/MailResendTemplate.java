/**
 * {@link MailResendTemplate}
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
 * Mail Resend Template
 * 
 * @author NIIT Technologies Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
public class MailResendTemplate {
   //
   private BigInteger messageLogId;
   //
   private BigInteger templateLogId;
   private BigInteger templateId;
   private String templateName;
   private String templatePayload;
   private String templateParams;
   
}
