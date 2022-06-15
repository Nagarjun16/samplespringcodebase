/**
 * {@link IVRSConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.constants;

/**
 * IVRS Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class IVRSConstants {

   private IVRSConstants() {
      throw new RuntimeException("Creating instance are not allowed");
   }
   //
   public static final String HTTP = "HTTP"; // CHANNEL
   public static final String COSYS = "COSYS"; // ORIGIN
   public static final String SYSTEM_IVRS = "IVRS";
   public static final String SYSTEM_FAX = "FAX";
   public static final String SYSTEM_REPORT = "REPORT";
   public static final String RELATIVE_PATH_PATTERN = "{}";
   public static final String API_PERFORM_CALL = "performcall";
   //
   public static final String SUCCESS_RESPONSE = "S";
   public static final String FAILURE_RESPONSE = "F";
   public static final String FAILURE_ERROR_CODE = "Invalid AWB Number";
   public static final String FAILURE_ERROR_DESC = "Requested AWB details not found";
   public static final String FAILURE_UPDATE_ERROR_CODE = "IVRX0";
   public static final String FAILURE_UPDATE_ERROR_DESC = "IVRS Acknowledgement Failed";
   //
   public static final String TENANT_ID = "TENANT-ID";
   public static final String MESSAGE_ID = "MESSAGE_ID";
   public static final String ESB_MESSAGE_ID = "ESB_MESSAGE_ID";
   public static final String LOGGER_ENABLED = "LOGGER_ENABLED";
   public static final String SYSTEM_NAME = "SYSTEM_NAME";
   public static final String INTERFACE_SYSTEM = "INTERFACE_SYSTEM";
   
}
