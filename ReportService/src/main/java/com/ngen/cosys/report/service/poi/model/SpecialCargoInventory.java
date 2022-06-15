package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoInventory extends BaseBO{

	/**
	 * 
	 */
   private static final long serialVersionUID = -3132565186459523324L;
   private BigInteger shipmentInventoryBookingId;
   private BigInteger shipmentInventoryFlightBookingId;
   private BigInteger shipmentInventoryId;
   private BigInteger shipmentInventoryShipmentId;
   private String shipmentInventoryShipmentLocation;
   private List<SpecialCargoInventory> inventoryList;
   private List<SpecialCargoSHC> inventorySHCList = new ArrayList<SpecialCargoSHC>();
   private BigInteger shipmentInventoryPieces;
   private BigDecimal shipmentInventoryWeight;
   private BigInteger totalInventoryPieces;
   private BigDecimal totalInventoryWeight;
   private String shipmentInventoryWarehoueLocation;
   private String shipmentInventoryPartSuffix;
   private String inventoryShc;
   private BigInteger shipmentMasterSHCBookingId;
   private BigInteger shipmentMasterSHCFlightBookingId;
   private BigInteger shipmentMasterSHCId;
   private BigInteger shipmentMasterSHCShipmentId;
   private String bookingSHC;
   private String awbSHC;
   private BigInteger flightBookingDetailSHCBookingId;
   private BigInteger flightBookingDetailSHCFlightBookingId;
   private BigInteger flightBookingSHCId;
   private BigInteger bookingId;
   private BigInteger requestingPieces;
   private BigInteger availablePieces;
   private boolean locked;
   private boolean loadedCheck;
   
   private List<SpecialCargoRequest> requestList = new ArrayList<SpecialCargoRequest>();
   private BigInteger requestPieces;
   private List<SpecialCargoInventory> handoverInventoryList = new ArrayList<SpecialCargoInventory>();
   private String requestedShipmentLocation;
   private List<SpecialCargoSHC> requstedShcList;
   private BigInteger handoverPieces;
   private BigDecimal handoverWight;
   private boolean handoverFlag;
   private String inventoryShcForDisplay;
   private BigInteger requestId;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime requestDateTime;
   private String requestBy;
   private String handOverLocForReq;
   private String requestedWHLocation;
}
