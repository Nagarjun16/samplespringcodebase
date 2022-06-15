/**
 * {@link ExcelReportRequest}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.model;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Excel Report request has Report Name and mandate values for SEARCH
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExcelReportRequest extends BaseBO {

   /**
    * Default Serial Version UID
    */
   private static final long serialVersionUID = 1L;
   //
   @NotNull
   private String reportName;
   private String carrierCode;
   private LocalDateTime fromDate;
   private LocalDateTime toDate;
   private Map<String, Object> parameters;
   
}
