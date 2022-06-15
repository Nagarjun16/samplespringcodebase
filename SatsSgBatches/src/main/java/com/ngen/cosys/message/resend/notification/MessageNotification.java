/**
 * {@link MessageNotification}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.notification;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message notification model holds of mandate details for e-mail
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class MessageNotification {

   private String message;
   private String failedReason;
   private LocalDateTime failedTime;
   private Integer retryLimit;
   
}
