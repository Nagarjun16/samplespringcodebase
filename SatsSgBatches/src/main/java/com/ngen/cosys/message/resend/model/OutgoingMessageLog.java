/**
 * {@link OutgoingMessageLog}
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
 * Outgoing Message Log
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class OutgoingMessageLog extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger outgoingMessageLogId;
   private String medium; // Channel Sent
   private String interfacingSystem;
   private String senderOriginAddress;
   private String messageType;
   private String subMessageType;
   private String carrierCode;
   private String flightNumber;
   private LocalDateTime flightOriginDate;
   private String shipmentNumber;
   private LocalDateTime shipmentDate;
   private LocalDateTime requestedOn;
   private LocalDateTime sentOn;
   private LocalDateTime acknowledgementReceivedOn;
   private String message;
   private Integer versionNo;
   private Integer sequenceNo;
   private String messageContentEndIndicator;
   private String status;
   private String messageFormat;
   private String airportCode;
   private BigInteger messageLogId;
   
}
