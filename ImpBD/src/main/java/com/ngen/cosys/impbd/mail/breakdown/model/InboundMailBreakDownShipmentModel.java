package com.ngen.cosys.impbd.mail.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailBreakDownValidationGroup;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.validator.annotations.CheckHousewayBillConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CheckHousewayBillConstraint(groups = {InboundMailBreakDownValidationGroup.class},mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "type", hwbNumberField = "mailBagNumber")
//@NgenAudit(eventName = NgenAuditEventType.AIRMAIL_IMPORT, repository = NgenAuditEventRepository.MAIL_BREAKDOWN, entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN)
@NgenAudit(eventName = NgenAuditEventType.MAIL_BREAKDOWN, repository = NgenAuditEventRepository.MAILBAG, entityFieldName = "mailBagNumber", entityType = NgenAuditEntityType.MAILBAG)
public class InboundMailBreakDownShipmentModel extends InboundBreakdownShipmentHouseModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String origin;
   private String destination;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.MAILBAGNUMBER)
   private String mailBagNumber;
   private String remarks;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   private BigInteger breakDownPieces;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   private BigInteger flightId;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime dateSTA;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTLOCATION)
   private String shipmentLocation;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCAITON)
   private String warehouseLocation;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
   private String uldNumber;
   private BigInteger impShipmentVerificationId;
   private BigInteger shipmentHouseId;
   private BigInteger shipmentInventoryId;
   private BigInteger shipmentInventoryHouseId;
   private BigInteger impBreakDownULDTrolleyInfoId;
   private BigInteger impBreakDownHouseInfoId;
   private BigInteger impBreakDownStorageInfoId;
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
   private BigInteger invPcs;
   private BigDecimal invWgt;
   private boolean bup;
   private boolean uldPopup;
   private boolean releaseDest;
   private String containerDestination;
   private boolean closedTransit;
   private List<String> allContainerDest;
   private String shipmentLocationType;
}