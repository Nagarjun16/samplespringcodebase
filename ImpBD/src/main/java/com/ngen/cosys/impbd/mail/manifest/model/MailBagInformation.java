package com.ngen.cosys.impbd.mail.manifest.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailBagInformation extends BaseBO {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentInventoryId;
   private BigInteger shipmentId;
   private BigInteger houseId;
   private BigInteger flightId;
   private String storageLocation;
   private String breakDownLocation;
   private String mailBagNumber;
   private String nextDestination;
   private String originOfficeExchange;
   private String destinationOfficeExchange;
   private String shipmentNumber;
   private String carrierCode;
   private BigInteger pieces;
   private BigDecimal weight;
   private String origin;
   private String destination;
   private boolean bup;
   private String uldKey;
   private String transferCarrierTo;
   private Boolean transferred = Boolean.FALSE;
   private Boolean delivered = Boolean .FALSE;
   private BigInteger inventoryFlightId;
   private String loadedUldTrolley;
   private String loadedHouse;
   private String incomingCarrier = null;
   private String incomingCountry = null;
   private String incomingCity = null;
   private String outgoingCarrier = null;
   private String outgoingCountry;
   private String outgoingCity;
   private String agentCode;
   private String originCountry;
   private String originCity;
   private String destinationCountry;
   private String destinationCity;
   private String embargoFlag;
   private String damaged;
   private boolean closedTransit; 
}
