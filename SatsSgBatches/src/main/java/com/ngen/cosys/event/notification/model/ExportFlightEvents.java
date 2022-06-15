/**
 * {@link ExportFlightEvents}
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
 * Export Flight Events holds of event information for data extract
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class ExportFlightEvents extends EventTypeConfig {
   
   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private boolean buildUpCompleted;
   private boolean dlsCompleted;
   private boolean manifestCompleted;
   private boolean outboundFlightCompleted;
   //
   private LocalDateTime lastExecutionTime;
   
}
