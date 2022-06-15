/**
 * {@link IncomingFFMLog}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Incoming FFM Message log
 * 
 * @author Coforge PTE Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class IncomingFFMLog {

   private BigInteger incomingFFMLogId;
   private String flightKey;
   private LocalDateTime dateSTA;
   private String airportCode;
   private boolean nilCargo;
   private boolean lastPartReceived;
   private boolean allPartsReceived;
   private LocalDateTime messageProcessedTime;
   private String messageStatus;
   private String message; // Final Message to be processed
   private String createdUserCode;
   private LocalDateTime createdDateTime;
   private String lastUpdatedUserCode;
   private LocalDateTime lastUpdatedDateTime;  
   //
   private List<IncomingFFMLogDetail> incomingFFMLogDetails;
   //
   private String flagCRUD; // ADD/UPD
   private boolean messageRead;
   private Set<Integer> sequenceNos;
   private Integer totalPartGroup=0; // All Parts Received Group count
   // More than 1 Part with All Parts received case
   private Map<Integer, String> partGroupMessage;
   // If total Part is 3 received 2 Part Groups 
   // Part 1-2-3 Sequence No will be 1-2-3
   // Part 1-3-2 Sequence No will be 4-6-5 (+1) each part
   private Map<Integer, BigInteger> partGroupSequenceNos;
   
}
