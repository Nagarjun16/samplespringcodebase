/**
 * {@link OutgoingInterfaceConfig}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message Outgoing Interface Config
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class OutgoingInterfaceConfig {

   private String connectorMQUrl;
   private String connectorHTTPUrl;
   
}
