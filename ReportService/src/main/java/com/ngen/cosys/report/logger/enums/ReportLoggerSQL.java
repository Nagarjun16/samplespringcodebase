/** 
 * This is a Report Logger SQL class used for report service SQL query constants
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 11/07/2018
 */
package com.ngen.cosys.report.logger.enums;

/**
 * This class is for Constant ReportLoggerSQL Query
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum ReportLoggerSQL {

   INSERT_REPORT_LOG("sqlInsertReportLog"),
   UPDATE_REPORT_LOG("sqlUpdateReportLog");
   
   private String query;
   
   /**
    * Initialize 
    * 
    * @param query
    */
   ReportLoggerSQL(String query) {
      this.query = query;
   }
   
   /**
    * Get query value
    * 
    * @return
    */
   public String getQuery() {
      return this.query;
   }
   
}
