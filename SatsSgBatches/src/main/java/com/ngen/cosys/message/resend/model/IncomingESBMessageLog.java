/**
 * {@link IncomingESBMessageLog}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Incoming Message Log
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IncomingESBMessageLog extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger incomingESBMessageLogId;
   private String medium; // Channel Received
   private String interfacingSystem;
   private String senderOriginAddress;
   private String messageType;
   private String subMessageType;
   private String carrierCode;
   private String flightNumber;
   private LocalDateTime flightOriginDate;
   private String shipmentNumber;
   private LocalDateTime shipmentDate;
   private LocalDateTime receivedOn;
   private String message;
   private Integer versionNo;
   private Integer sequenceNo;
   private String messageContentEndIndicator;
   private String status;
   private String airportCode;
   private BigInteger messageESBSequenceNo;
   private String senderAddress;
   //
   private boolean duplicate;
   private boolean processed;
   private String exceptionMessage;
   
}
