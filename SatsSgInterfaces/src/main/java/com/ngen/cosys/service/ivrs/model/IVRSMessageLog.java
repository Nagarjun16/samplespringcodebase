/**
 * {@link IVRSMessageLog}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS Message Log detail
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IVRSMessageLog {

   private BigInteger messageId;
   private String interfaceSystem;
   private String messageType;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String status;
   private BigInteger messageSequenceNo; // Message Format
   private LocalDateTime createdTime;
   //
   private BigInteger errorMessageId;
   private String errorStatus;
   
}
