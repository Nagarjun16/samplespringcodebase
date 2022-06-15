/**
 * {@link DashboardBatchLog}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dashboard Flights Monitoring
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class DashboardBatchLog implements Serializable {
   // Generated Serial VersionUID
   private static final long serialVersionUID = -8433480463605083262L;
   //
   private BigInteger dashboardBatchLogId;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private Integer totalFlights;
   private LocalDateTime nextFireTime;
   private LocalDateTime createdDateTime;
   private String createdUserCode;
   private LocalDateTime lastUpdatedDateTime;
   private String lastUpdatedUserCode;
   
}
