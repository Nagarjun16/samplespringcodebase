/**
 * {@link ImportFlightEvents}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Import Flight Events holds event info for data extract
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class ImportFlightEvents extends EventTypeConfig {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private boolean breakdownCompleted;
   private boolean rampCheckInCompleted;
   private boolean inboundFlightCompleted;
   //
   private LocalDateTime lastExecutionTime;
   
}
