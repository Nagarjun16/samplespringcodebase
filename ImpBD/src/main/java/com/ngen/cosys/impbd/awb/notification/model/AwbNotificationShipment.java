package com.ngen.cosys.impbd.awb.notification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AwbNotificationShipment extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger shipmentId;
   private BigInteger flightId;
   private BigInteger pieces;
   private BigInteger shipmentPieces;
   //
   private String shipmentNumber;
   private String flightKey;
   private String customerName;
   private String customerCode;
   private String origin;
   private String destination;
   private String consigneeName;
   //
   private Boolean documentReceived;
   //
   private BigDecimal weight;
   private BigDecimal shipmentWeight;
   private BigDecimal charges;
   //
   private LocalDate shipmentDate;
   private LocalDate flightDate;
   //
   private String emailAddresses;
   //
   private List<AwbNotificationShipment> shipments;
   
}
