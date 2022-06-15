/**
 * {@link CiQReportEnums}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.enums;

/**
 * CiQ Report Enums
 * 
 * @author NIIT Technologies Ltd
 */
public enum CiQReportEnums {

   SELECT_CIQ_REPORT_DAILY_CONFIG("sqlSelectCiQReportScheduledConfig"), //
   INSERT_CIQ_REPORT_LOG("sqlInsertCiQReportLog"), //
   UPDATE_CIQ_REPORT_LOG("sqlUpdateCiQReportLog"), //
   SELECT_CIQ_REPORT_LOG("sqlSelectCiQReportLog"), //
   CHECK_REPORT_DATA_EXISTS("sqlIsReportDataExists")
   ;
   
   private String queryId;
   
   /**
    * @param value
    */
   private CiQReportEnums(String value) {
      this.queryId = value;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
