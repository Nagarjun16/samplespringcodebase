/**
 * 
 * WeighingScalePayload.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.service.wscale.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the Weighing Scale which actually gets IP Address and Port Number from 
 * interface and sent to Mule ESB to get Weight detail
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WeighingScalePayload {

   private String wscaleIP;
   private String wscalePort;
   private String terminal;
   private BigInteger messageId;
   private String tenantID;
   boolean loggerEnabled = Boolean.FALSE;
   
}
