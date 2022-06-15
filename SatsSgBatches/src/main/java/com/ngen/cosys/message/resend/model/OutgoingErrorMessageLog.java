/**
 * {@link OutgoingErrorMessageLog}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Outgoing Message Log
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class OutgoingErrorMessageLog extends OutgoingMessageLog {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger outgoingErrorMessageLogId;
   private String errorCode;
   private String errorMessage;
   private String lineItem;
   private boolean processed;
   //
   private OutgoingResendMessageLog outgoingResendMessageLog;
   
}
