/**
 * {@link IncomingInterfaceConfig}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message Incoming Interface Config
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IncomingInterfaceConfig {

   private String systemName;
   private String endPointUrl;
   private String medium; // MQ/HTTP
   private String messageType;
   
}
