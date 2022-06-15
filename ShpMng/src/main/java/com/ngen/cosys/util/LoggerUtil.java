/**
 * 
 * LoggerUtil.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          24 November, 2017   NIIT      -
 */
package com.ngen.cosys.util;

import org.slf4j.event.Level;

/**
 * This is the Logger Utility class which helps to construct Log message.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class LoggerUtil  {

   /**
    * This method constructs the Error Message to log based on the below parameters.
    * 
    * @param className, not null - The originating Class Name
    * @param methodName, not null - The originating Method Name
    * @param logLevel, not null - The Logger Level
    * @param paramObj, not null - The Parameter Object
    * @param returnObj, not null - The Response Object
    * @return
    */
   public static String getLoggerMessage(String className, String methodName, Level logLevel, 
     Object paramObj, Object returnObj) {
     StringBuilder sb = new StringBuilder();
     sb.append("Message at ").append(logLevel.toString()).append(" level from ")
     .append(className).append(".").append(methodName).append(" \n")
     .append(" ... Parameter => [").append(paramObj!=null?paramObj.toString():"NULL").append("] \n");
     if(returnObj!=null) {
       sb.append(" ... Response => [").append(returnObj.toString()).append("] \n");	 
     }
     return sb.toString();
   }
}