/** 
 * This is a ESBRouterTypeUtils class used for report service ESB constants
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 11/07/2018
 */
package com.ngen.cosys.esb.connector.enums;

/**
 * This class is for Constant of ESB Router
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum ESBRouterTypeUtils {

   MESSAGE_ID(Type.MESSAGE_ID),
   LOGGER_ENABLED(Type.LOGGER_ENABLED),
   INTERFACE_SYSTEM(Type.INTERFACE_SYSTEM),
   SYSTEM_NAME(Type.SYSTEM_NAME),
   TENANT_ID(Type.TENANT_ID);
   
   public class Type {

      private Type() {}

      public static final String MESSAGE_ID = "MESSAGE_ID";
      public static final String LOGGER_ENABLED = "LOGGER_ENABLED";
      public static final String INTERFACE_SYSTEM = "INTERFACE_SYSTEM";
      public static final String SYSTEM_NAME = "SYSTEM_NAME";
      public static final String TENANT_ID = "TENANT-ID";

   }

   String name;

   /**
    * @param value
    */
   ESBRouterTypeUtils(String value) {
      this.name = value;
   }

   /**
    * @return
    */
   public String getName() {
      return this.name;
   }
  
}
