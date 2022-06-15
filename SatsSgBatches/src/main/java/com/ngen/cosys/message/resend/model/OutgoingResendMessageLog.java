/**
 * {@link OutgoingResendMessageLog}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Outgoing Resend Message Log
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class OutgoingResendMessageLog extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger outgoingResendMessageLogId;
   private BigInteger outgoingErrorMessageLogId;
   private BigInteger outgoingMessageLogId;
   private LocalDateTime failedTime;
   private Integer retryLimit;
   private String alertStatus;
   private LocalDateTime notificationTime;
   private LocalDateTime resentTime;
   
}
