/**
 * {@link ReportFrequency}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.common;

/**
 * CiQ Report Frequency
 * 
 * @author NIIT Technologies Ltd
 */
public class ReportFrequency {

   private ReportFrequency() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   //
   public static final String DAILY = "D";
   public static final String WEEKLY = "W";
   public static final String MONTHLY = "M";
   
}
