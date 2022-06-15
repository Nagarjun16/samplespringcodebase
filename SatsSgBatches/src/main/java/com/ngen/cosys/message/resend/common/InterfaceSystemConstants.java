/**
 * {@link InterfaceSystemConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.common;

/**
 * Interface System Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class InterfaceSystemConstants {

   private InterfaceSystemConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   //
   public static final String AED1 = "AED1";
   public static final String AED4 = "AED4";
   public static final String AED6 = "AED6";
   public static final String SYSTEM_AED = "AED";
   public static final String SYSTEM_AED1 = "AED_GHAMAWBNO";
   public static final String SYSTEM_AED4 = "AED_GHAMAWBINFO";
   public static final String SYSTEM_AED5 = "AED_GHASCANINFO";
   public static final String SYSTEM_AED6 = "AED_GHAFLIGHTSCHD";
   public static final String SYSTEM_CCN = "CCN";
   public static final String SYSTEM_ICS = "ICS";
   public static final String SYSTEM_RX = "RX";
   public static final String SYSTEM_ARINC = "ARINC";
   public static final String SYSTEM_EDIFLY = "EDIFLY";
   public static final String SYSTEM_UFIS = "UFIS";
   public static final String SYSTEM_ITREK = "ITREK_UIM";
   public static final String SYSTEM_SINGPOST = "SINGPOST";
   //
   public static final String MANUAL = "MANUAL";
   public static final String BATCH = "BATCH";
}
