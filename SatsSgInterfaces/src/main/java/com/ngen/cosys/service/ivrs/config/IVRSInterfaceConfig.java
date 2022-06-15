/**
 * {@link IVRSInterfaceConfig}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS Interface config
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IVRSInterfaceConfig {

   private String performCallUrl;
   private String sendFaxUrl;
   private String reportServiceUrl;
   
}
