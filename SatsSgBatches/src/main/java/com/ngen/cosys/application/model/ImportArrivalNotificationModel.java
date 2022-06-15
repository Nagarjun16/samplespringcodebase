package com.ngen.cosys.application.model;

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
public class ImportArrivalNotificationModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigInteger flightId;
   private BigInteger pieces;
   private BigInteger shipmentPieces;

   private String shipmentNumber;
   private String houseNumber;
   private BigInteger housePieces;
   private BigDecimal houseWeight;
   private String flightKey;
   private String customerName;
   private String customerCode;
   private String origin;
   private String destination;
   private String consigneeName;
   private String shipmentType;

   private Boolean documentReceived = Boolean.FALSE;

   private BigDecimal weight;
   private BigDecimal shipmentWeight;
   private BigDecimal charges;

   private LocalDate shipmentDate;
   private LocalDate flightDate;

   private String emailAddresses;

   private List<ImportArrivalNotificationModel> shipments;

}