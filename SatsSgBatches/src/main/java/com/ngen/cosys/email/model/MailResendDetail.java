/**
 * {@link MailResendDetail}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mail Resend Detail
 * 
 * @author NIIT Technologies Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
public class MailResendDetail {

   private BigInteger messageLogId;
   private String messageSubject;
   private String messagePayload;
   private String messageSender;
   private String messageRecipients;
   private Integer receiptCount;
   private String messageStatus;
   private String messageReference;
   private String failedReason;
   private String createdBy;
   private LocalDateTime createdTime;
   private String updatedBy;
   private LocalDateTime updatedTime;
   //
   private MailResendTemplate template;
   private List<MailResendDocument> documents;
   //
   private boolean isResendSuccess;
   private String resendStatus;
   
}
