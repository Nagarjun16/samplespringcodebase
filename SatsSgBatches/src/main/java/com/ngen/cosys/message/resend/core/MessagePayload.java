/**
 * {@link MessagePayload}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.core;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message Payload used for Resend function
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class MessagePayload {

   private String qname;
   private Object payload;
   private String tenantID;
   private BigInteger messageId;
   private boolean loggerEnabled;
   
}
