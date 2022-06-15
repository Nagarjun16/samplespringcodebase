/**
 * 
 * \ * ShipmentBookingDetails.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 15 December, 2017 NIIT -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.events.payload.FlightBookingPayLoad;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.processing.engine.rule.payload.RuleShipmentExecutionDetails;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentBookingDetails Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING_CANCELLATION, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
public class ShipmentBookingDetails extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private Long bookingId;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.ORIGIN)
   private String origin;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.DESTINATION)
   private String destination;
   private Integer pieces;
   private Integer oldPieces;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.WEIGHT)
   private Double grossWeight;
   private Double oldGrossWeight;
   private String weightUnitCode;
   private Integer densityGroupCode;
   private Double volumeWeight = 0.00;
   private String volumeUnitCode;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.NATUREOFGOODS)
   private String natureOfGoodsDescription;
   private int serviceFlag;
   private int blockSpace;
   private int manual;
   private String proposedRouting;
   private Long shipperCustomerId;
   private String flagCreatePart;
   private String flagMergePart;
   private String flagDeletePart;
   private String flagUpdatePart;
   private String flagUpdate = "Y";
   private String flagCreate;
   private boolean mergeSelect = false;
   private String partIdentifier;
   private BigInteger shipmentId;
   private String basePort;
   private Boolean bookingNotExist = Boolean.FALSE;
   private Boolean errorFlag = Boolean.FALSE;
   private boolean flightDetailAvailable = true;
   private String shipperName;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTSHC)
   private String sccCodes;

   private boolean shipmentInShipmentMaster;

   private boolean bookingByEvent;
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName=NgenAuditFieldNameType.PART_BOOKING_DETAILS)
   @Valid
   private List<ShipmentFlightPartBookingDetails> partBookingList=new ArrayList<ShipmentFlightPartBookingDetails>();

   private RuleShipmentExecutionDetails ruleShipmentExecutionDetails;

   private boolean suffixAvail;
   private BigInteger totalPieces;
   private boolean pshFlag;
   private Long flightId;
   @NgenAuditField(fieldName=NgenAuditFieldNameType.REASON_FOR_CANCELLATION)
   private String bookingCancellationReason;
   
   private int totalFlightPieces;
   private double totalFlightWeight;
   private Boolean primaryPart= false;
   private List<Long> changedStatusCodes;
   private Boolean isCancelBookingFlag =Boolean.FALSE;
   private List<FlightBookingPayLoad> fltPayLoadList;
   private String partSuffix;
   private boolean fromAddPart=Boolean.FALSE;
   private BigInteger remainingPcs;
   private BigDecimal remainingWt;
   private Integer indexForProprtionalWeight;
   private String ruleEngineWarningAndInfoMessage;
   private boolean skipRuleEngineFlag;
   private String carrier;
   private String sqCarrierGrpPrimaryPrefix;
   @Valid
   private List<ShipmentFlightPartBookingDetails> partUpdateList;
   private String combinedShcs;
   private boolean updateBookingConfirmSaveFlag=Boolean.FALSE;
   private LocalDateTime documentRecievedOn;
   private LocalDateTime photocopy;
   private boolean finalizeOrPartConfirmDone=Boolean.FALSE;
   private boolean acceptanceFlag=Boolean.FALSE;
   
   private boolean routingMismatch;
   private boolean skipRoutingValidation;
   private String flightRouteWhichHasMismatch;
   private String shipmentRoute;
   private String flightKeyForCarrier;
   
   private String handoverShipment;
   private boolean skipHandoverFlag = Boolean.FALSE;
   private String commitTransaction;
   private boolean throughTransitFlag = Boolean.FALSE;
   private boolean awbFreeze = Boolean.FALSE;
   private String ubrNumber;
   private String bookingRemarks;
   private List<ShipmentBookingDimensions> shipmentBookingDimensionList;
   private int allocatedPiece = 0;
   private double allocatedWeight = 0.0;
   private double allocatedVolume = 0.0;
   private String carrierCode;
   private String handlingInformationRemark;
   private boolean isAnyWorkedOnPartPresent;
   private List<String> workedOnPartSuffixList;
 }
