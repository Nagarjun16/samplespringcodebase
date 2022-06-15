package com.ngen.cosys.shipment.inactive.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
public class ShipmentFreightOut extends BaseBO {

   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger shipmentInventoryId;
   private BigInteger freightOutId;
   private BigInteger shipmentId;
   private String shipmentLocation;
   private BigInteger pieces;
   private BigDecimal weight;
   private String warehouseLocation;
   private BigInteger flightId;
   private BigInteger inboundFlightId;
   private String pdNumber;
   private String assignedUldTrolley;
   private BigDecimal dryIceWeight;
   private String trackerId;
   private String temperatureRange;
   private String handlingArea;
   private Boolean locked = Boolean.FALSE;
   private String lockReason;
   private String lockedBy;
   private String holdRemarks;
   private String deliveryRequestOrderNo;
   private String deliveryRequestIssuedBy;
   private LocalDateTime deliveryRequestIssuedOn;
   private BigInteger deliveryOrderNo;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDateTime deliveredOn;
   private String deliveredBy;
   private Boolean dispose = Boolean.FALSE;
   private String trmNumber;
   private String partSuffix;

   private BigInteger shipmentHouseId;
   private BigDecimal chargebleWeight;
}