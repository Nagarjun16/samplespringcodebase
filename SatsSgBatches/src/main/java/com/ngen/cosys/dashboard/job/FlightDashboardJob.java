/**
 * {@link FlightDashboardJob}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.job;

import java.util.List;

import com.ngen.cosys.dashboard.model.EventNotification;

/**
 * Flight Dashboard Job
 * 
 * @author NIIT Technologies Ltd
 */
public interface FlightDashboardJob {

   /**
    * GET Event configurations
    * 
    * @param module
    *       Export/Import
    * @return
    */
   List<EventNotification> getEventConfigurations(String module);
   
}
