/**
 * {@link DashboardConstants}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.common;

/**
 * Dashboard Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class DashboardConstants {
   //
   private DashboardConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   //
   public static final String EVENT_COMPLETED = "YES";
   public static final String EVENT_NOT_COMPLETED = "NO";
   public static final String EXPORT = "EXPORT";
   public static final String IMPORT = "IMPORT";
   public static final String EXPORT_FLIGHT_DASHBOARD = "exportFlightDashboard";
   public static final String IMPORT_FLIGHT_DASHBOARD = "importFlightDashboard";
   // Job Group and Job names
   public static final String JOB_GROUP = "DASHBOARD";
   public static final String EXPORT_FLIGHT_DASHBOARD_JOB = "ExportFlightDashboardJob";
   public static final String IMPORT_FLIGHT_DASHBOARD_JOB = "ImportFlightDashboardJob";
   
}
