/**
 * 
 * \ * SingleShipmentBooking.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 15 December, 2017 NIIT -
 */
package com.ngen.cosys.export.booking.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.events.payload.FlightBookingPayLoad;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents SingleShipmentBooking Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SingleShipmentBooking extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private Long bookingId;
   private String shipmentNumber;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightOriginDate;
   private String origin;
   private String destination;
   private BigInteger pieces;
   private BigInteger partPieces;
   private Integer oldPieces;
   private BigDecimal grossWeight;
   private BigDecimal partWeight;
   private Double oldGrossWeight;
   private String weightUnitCode;
   private String flightKey;
   private Integer densityGroupCode;
   private Double volumeWeight = 0.00;
   private String volumeUnitCode;
   private String natureOfGoodsDescription;
   private int serviceFlag;
   private int blockSpace;
   private int manual;
   private String proposedRouting;
   private Long shipperCustomerId;
   private boolean mergeSelect = false;
   private String partIdentifier;
   private BigInteger shipmentId;
   private String basePort;
   private Boolean bookingNotExist = Boolean.FALSE;
   private Boolean errorFlag = Boolean.FALSE;
   private boolean flightDetailAvailable = true;
   private String shipperName;
   private boolean shipmentInShipmentMaster;
   private boolean bookingByEvent;
   private boolean suffixAvail;
   private BigInteger totalPieces;
   private boolean pshFlag;
   private Long flightId;
   private String bookingCancellationReason;
   private int totalFlightPieces;
   private double totalFlightWeight;
   private Boolean primaryPart= false;
   private List<Long> changedStatusCodes;
   private Boolean isCancelBookingFlag =Boolean.FALSE;
   private List<FlightBookingPayLoad> fltPayLoadList;
   private String partSuffix;
   private boolean fromAddPart;
   private String flightBoardPoint;
   private String flightOffPoint;

   
   //document verification details
   private BigInteger breakDownPieces;
   private BigDecimal breakDownWeight;
   private boolean documentReceivedFlag;
   private boolean photoCopyAwbFlag;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime dateATA;
   private BigInteger manifestPiece;
   private BigDecimal manifestWeight;

   private BigInteger invPiece;
   private BigDecimal invWeight;
}
