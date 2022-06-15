/**
 * {@link CiQReportLog}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CiQ Report Log
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class CiQReportLog extends CiQReport {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger reportLogId;
   private LocalDateTime triggeredTime;
   private LocalDateTime generatedTime;
   private String status;
   private String source;
   private String  messageType;
   private String  carrierCode;
   private boolean transitFlag;
}
