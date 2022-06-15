package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "StringFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser","loadedHousePieces", "loadedHouseWeight", "validMailBags", "dlsCompleted", "mailManifestCompleted", "departed" })
public class AirmailHouseCommonModel extends BaseBO {
   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;

   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   private BigInteger shipmentId;

   private String mailBagNumber;

   private BigInteger shipmentHouseId;

   private BigInteger pieces;
   private BigDecimal weight;

   private String agentCode;

   private String origin;
   private String destination;

   // Mail Properties
   private String originOfficeExchange;

   private String originCountry;
   /**
    * Origin City
    */
   private String originCity;

   private String destinationOfficeExchange;

   /**
    * Destination Country
    */
   private String destinationCountry;
   /**
    * Destination City
    */
   private String destinationCity;
   private String nextDestination;
   
   private String mailCategory;
   private String mailType;
   private String mailSubType;
   private BigInteger dispatchYear;
   private String dispatchNumber;
   private String receptacleNumber;
   private Boolean lastBag = Boolean.FALSE;
   private Boolean registered = Boolean.FALSE;
   private String transferCarrier;
   
   private String shipmentLocation;
   private String warehouseLocation;
   private BigInteger shipmentInventoryId;

   private String remarks;


   private String incomingCarrier = null;
   
   private String transferCarrierFrom;
   
   private String outgoingCarrier = null;
   
   private String flightBoardPoint;
   
   private String flightOffPoint = null;

   /**
    * Embargo indicator
    */
   private String embargoFlag;

   /**
    * Damaged flag
    */
   private String damaged;
   
   
   private BigInteger shipmentInventoryHouseId;
   
   private String wareHouseLocation;
   private BigInteger incomingFlightId;
   private String incomingFlightKey;
   private LocalDate incomingFlightDate;
   private String manifestedFlightKey;
   private LocalDate manifestedFlightDate;
   private BigInteger manifestedFltId;
   private BigInteger manifestedFltSegmentId;
   private BigInteger manifestShipmentHouseInfoId;
   
   
   private String manifestULD;
   private BigInteger loadedShipmentInfoId;
   private BigInteger assUldTrolleyId;
   private BigInteger loadedShipmentPieces;
   private BigDecimal loadedShipmentWeight;
   private BigInteger loadedShipmentHouseInfoId;
   private Boolean damage;
   private Boolean xRayResultFlag;
   private BigInteger flightId;
   private Boolean dlsCompleted;
   private Boolean mailManifestCompleted;
   private Boolean departed; 
   private String storeLocation;
   private String carrier;
   private BigInteger loadedHousePieces;
   private BigDecimal loadedHouseWeight;
   private Boolean validMailBags = Boolean.TRUE;
}