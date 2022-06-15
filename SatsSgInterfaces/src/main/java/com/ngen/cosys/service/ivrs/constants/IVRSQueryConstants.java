/**
 * {@link IVRSQueryConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.constants;

/**
 * IVRS Query Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class IVRSQueryConstants {

   private IVRSQueryConstants() {
      throw new RuntimeException("Creating instance are not allowed");
   }
   //
   public static final String SQL_SELECT_IVRS_API_CONFIG = "sqlSelectIVRSAPIConfig";
   public static final String SQL_SELECT_IVRS_FAX_AWB_REQUEST = "sqlSelectIVRSFAXAWBRequest";
   public static final String SQL_SELECT_IVRS_FAX_REPORT_PARAMS = "sqlSelectIVRSFAXReportParams";
   public static final String SQL_SELECT_IVRS_ENQUIRE_AWB_REQUEST = "sqlSelectIVRSEnquireAWBRequest";
   public static final String SQL_SELECT_MESSAGE_LOG_DETAIL = "sqlSelectIVRSFAXMessageLogDetail";
   
}
