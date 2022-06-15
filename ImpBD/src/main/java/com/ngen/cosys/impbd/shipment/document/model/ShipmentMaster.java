package com.ngen.cosys.impbd.shipment.document.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;
import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber",  entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION, repository = NgenAuditEventRepository.AWB)
public class ShipmentMaster extends ShipmentModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = -9207013787317412804L;

   @NgenAuditField(fieldName = "Shipment Type")
   private String shipmentType;
   
   @NgenAuditField(fieldName = "originOfficeExchange")
   private String originOfficeExchange;
   
   @NgenAuditField(fieldName = "mailCategory")
   private String mailCategory;
   
   @NgenAuditField(fieldName = "mailSubCategory")
   private String mailSubCategory;
   
   @NgenAuditField(fieldName = "destinationOfficeExchange")
   private String destinationOfficeExchange;

   @NgenAuditField(fieldName = "dispatchYear")
   private BigInteger dispatchYear;

   @NgenAuditField(fieldName = "SVC")
   private Boolean svc = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "partShipment")
   private Boolean partShipment = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "registered")
   private Boolean registered = Boolean.FALSE;
   
   @NgenAuditField(fieldName = "manuallyCreated")
   private Boolean manuallyCreated = Boolean.FALSE;

   @NgenAuditField(fieldName = "Document Received On")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentReceivedOn;

   @NgenAuditField(fieldName = "Document Pouch Received On")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentPouchReceivedOn;

   //@NgenAuditField(fieldName = "Photo Copy Received On")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime photoCopyReceivedOn;
   
   private BigInteger requestFlightId;

   @NgenAuditField(fieldName = "routing")
   private List<ShipmentMasterRoutingInfo> routing;
   
   @NgenAuditField(fieldName = "shcs")
   private List<ShipmentMasterShc> shcs;
   
   @NgenAuditField(fieldName = "shcHandlingGroup")
   private List<ShipmentMasterShcHandlingGroup> shcHandlingGroup;
   
   @NgenAuditField(fieldName = "remarks")
   private List<ShipmentRemarksModel> remarks;
   
	//AISATS 
	private String handledByDOMINT;
	
	private String handledByMasterHouse;
	
	private BigDecimal chargeableWeight;

}