/**
 * {@link MessageDetail}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.core;

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
public class MessageDetail {

   private Object payload;
   private MessageResponse response;
   private Object errorMessageLog;
   
}
