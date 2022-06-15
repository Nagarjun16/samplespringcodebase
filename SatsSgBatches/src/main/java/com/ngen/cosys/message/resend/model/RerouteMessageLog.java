/**
 * {@link IncomingFFMLog}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Incoming FFM Message log
 * 
 * @author Coforge PTE Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class RerouteMessageLog {

   private BigInteger interfaceIncomingMessageLogId;
   private String messageStatus;
   private String message; 
   private String createdUserCode;
   private LocalDateTime createdDateTime;
   private String lastUpdatedUserCode;
   private LocalDateTime lastUpdatedDateTime;  
   
   
}
