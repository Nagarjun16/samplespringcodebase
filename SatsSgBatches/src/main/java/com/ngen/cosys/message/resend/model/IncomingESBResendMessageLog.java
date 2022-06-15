/**
 * {@link IncomingESBResendMessageLog}
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
 * Incoming ESB Resend Message Log
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IncomingESBResendMessageLog extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger incomingESBResendMessageLogId;
   private BigInteger incomingESBErrorMessageLogId;
   private BigInteger incomingESBMessageLogId;
   private LocalDateTime failedTime;
   private Integer retryLimit;
   private String alertStatus;
   private LocalDateTime notificationTime;
   private LocalDateTime resentTime;
   
}
