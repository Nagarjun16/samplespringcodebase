/**
 * 
 * ReportPayload.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          11 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.report.logger.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the Report Payload which used report service Logger
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ReportPayload extends BaseBO {

   private static final long serialVersionUID = 1L;
   
   private BigInteger reportServiceLogId;
   private String reportName;
   private String reportFormat;
   private String reportParams;
   private String printerQueueName;
   private String printerType;
   private String landscapeFlag;
   private String reportStatus;
   private String failedReason;
   
}
