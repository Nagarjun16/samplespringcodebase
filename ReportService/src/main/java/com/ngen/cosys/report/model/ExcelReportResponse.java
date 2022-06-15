/**
 * {@link ExcelReportResponse}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Excel Report Response has Report Name and data (Encoded) for the request
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class ExcelReportResponse extends BaseBO {
   
   /**
    * Default Serial VersionUID
    */
   private static final long serialVersionUID = 1L;
   //
   private String reportName;
   private String reportData;
   
}
