package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.validator.annotations.CheckHousewayBillConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CheckHousewayBillConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "type", hwbNumberField = "mailBagNumber")
public class InboundMailBreakDownShipmentAirmailInterfaceModel extends InboundBreakdownShipmentHouseAirmailInterfaceModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String origin;
   private String destination;

   private String mailBagNumber;
   private String remarks;
   private String shipmentNumber;
   private BigInteger breakDownPieces;
   
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime dateSTA;
   private String shipmentLocation;

   private String warehouseLocation;

   private String uldNumber;

   private BigInteger individualPieces;
   private BigInteger pieces;
   private BigDecimal individualWeight;
   private String transferCarrierFrom;
   private String nextDestination;
   private String incomingCarrier = null;
   private String incomingCountry = null;
   private String incomingCity = null;
   private String outgoingCarrier = null;
   private String outgoingCountry;
   private String flightOffPoint = null;

   private String outgoingCity;
   private String originOfficeExchange;
   private String destinationOfficeExchange;
   private String agentCode;
   private String originCountry;
   /**
    * Origin City
    */
   private String originCity;
   /**
    * Destination Country
    */
   private String destinationCountry;
   /**
    * Destination City
    */
   private String destinationCity;
   /**
    * Embargo indicator
    */
   private String embargoFlag;
   
   /**
    * Damaged flag
    */
   private String damaged;
}