package com.ngen.cosys.satssginterfaces.mss.model;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckHousewayBillConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
@CheckHousewayBillConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "shipmentType", hwbNumberField = "mailBagNumber")
@NgenAudit(eventName = NgenAuditEventType.MAIL_ACCEPTANCE, repository = NgenAuditEventRepository.MAILBAG, entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.MAILBAG)
public class MailExportAcceptanceRequest extends BaseBO {
   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;

   /**
    * This field contains Shipment Type
    */
   @NgenAuditField(fieldName = "Shipment Type")
   private String shipmentType;

   /**
    * This field contains Service Number
    */
   private String serviceNumber;
   @NgenAuditField(fieldName = "Shipment Number")
   private String shipmentNumber;
   @NgenAuditField(fieldName = "Shipment Date")
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   /**
    * This field contains Mail Number
    */
   @NgenAuditField(fieldName = "Mail Bag Number")
   private String mailBagNumber;

   /**
    * This field contains Customer Id
    */
   private BigInteger customerId;

   private String incomingCarrier;
   private String outgoingCarrier;
   private String incomingCountry;
   private String incomingCity;
   private String outgoingCountry;
   private String outgoingCity;
   private String originOfficeExchange;
   private String originCountry;
   private String originCity;
   private String destinationOfficeExchange;
   private String destinationCountry;
   private String destinationCity;
   private String nextDestination;
   @NgenAuditField(fieldName = "Embargo")
   private String embargoFlag;
   /**
    * This field contains Customer Name
    */
   @NgenAuditField(fieldName = "Customer Name")
   private String customerName;

   /**
    * This field contains Customer Code
    */
   private String agentCode;

   /**
    * This field contains Total Pieces
    */
   private BigInteger totalPieces;

   /**
    * This field contains Total Weight
    */
   private BigDecimal totalWeight;

   /**
    * This field contains Shipment Id
    */
   private int shipmentId;

   /**
    * This field contains Dn Number
    */
   @NgenAuditField(fieldName = "DN Number")
   private String dnNumber;

   /**
    * This field contains Origin
    */
   private String dnOrigin;

   /**
    * This field contains Destination
    */
   private String dnDestination;

   /**
    * This field contains Remarks
    */
   private String dnRemarks;

   /**
    * This field contains Service Id
    */
   private int serviceId;

   /**
    * This field contains Document Information Id
    */
   private int documentInfoId;

   /**
    * This field contains Shipment Inventory Id
    */
   private int shipmentInventoryId;

   /**
    * This field contains Shipment House Id
    */
   private int houseId;

   /**
    * This field contains Shipment Customer Information Id
    */
   private int shipmentCustomerInfoId;

   /**
    * This field contains Shipment Master Handling Area Id
    */
   private int shipmentMasterHandlAreaId;

   /**
    * This field contains Store Location
    */
   // @CheckShipmentLocationConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @NgenAuditField(fieldName = "ULD Number")
   private String uldNumber;

   /**
    * This field contains Carrier Code
    */
   private String carrierCode;

   /**
    * This field contains Assisted Carrier
    */
   private boolean isSatsAssistedCar;

   /**
    * This field contains Mail Bag Category
    */
   private String mailBagMailCategory;

   /**
    * This field contains Mail Bag Sub Category
    */
   private String mailBagMailSubcategory;

   /**
    * This field contains Dispatch Year
    */
   private int mailBagDispatchYear;

   /**
    * This field contains Registered Indicator
    */
   private int mailBagRegisteredIndicator;

   /**
    * This field contains Mail Bag Number eAcceptance Count
    */
   private int mailBagNumbereAcceptanceCount;

   /**
    * This field contains Mail Bag Number Shipment House Count
    */
   private int mailBagNumberShipmentHouseCount;

   /**
    * This field contains Mail Bag Number Inventory House
    */
   private int mailBagNumberInventoryHouse;

   /**
    * This field contains Booking Id
    */
   private int bookingId;

   /**
    * This field contains PA Flight Id
    */
   private String paFlightId;

   /**
    * This field contains PA Flight Key
    */
   private String paFlightKey;

   /**
    * This field contains PA Flight Segment
    */
   private ArrayList<String> paFlightSeg;

   /**
    * This field contains PA Flight Date
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate paFlightDate;
   
   private String paFlightCarrier;

   /**
    * This field contains country code
    */
   private String countryCode;

   /**
    * This field contains warehouseLocation
    */
   @NgenAuditField(fieldName = "Warehouse Location")
   private String warehouseLocation;
   private boolean intact;

   /**
    * This field contains searchMode
    */
   private String searchMode;
   private String damaged;
   private BigInteger inventoryPieces;
   private BigDecimal inventoryWeight;
   
   private BigInteger pieces;
   private BigDecimal weight;
   /**
    * This field contains Mail Export Acceptance List
    */
   @Valid
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = "Mail Export Acceptance ")
   private List<MailExportAcceptance> mailExportAcceptance;
   
   private boolean validateContainerDest;
   
   private boolean fieldLocked;
   
   private boolean closedTransit;
   private boolean fromMSS;
   private String user;
}