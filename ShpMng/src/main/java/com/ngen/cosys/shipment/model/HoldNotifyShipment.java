package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHIPMENT_UNHOLD, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CHANGE_HOLD_NOTIFY_GROUP, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class HoldNotifyShipment extends BaseBO {
	
	   private static final long serialVersionUID = 1L;
	   private String partSuffix;
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	   private String shipmentNumber;
	   private LocalDate shipmentDate;
	   private String origin;
	   private String destination;
	   private String shipmentShc;
	   private String natureOfGoodsDescription;
	   private String location;
	   private String warehouseLocation;
	   private BigInteger piecesInv;
	   private BigDecimal weightInv;

	   private String user;

//	   @JsonFormat(pattern = "ddMMMyyyy HH:mm")
	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime datetime;
	   
	   private String reasonForHold;
	   private String remarks;
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.HOLD_NOTIFY_GROUP)
	   private String holdNotifyGroup;

	   private Boolean select;
	   private int shipmentInventoryId;
	   private int shipmentId;
	   private String shipmentLocation;
	   private List<ShipmentInventoryShcModel> shcListInv;
	   @JsonFormat(pattern = "ddMMMyyyy")
	   private LocalDateTime ackDate;
   
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.UN_HOLD_BY)
	   private String unholdBy;
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.UN_HOLD_TIME)
	   private LocalDateTime unholdTime;
	   
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY_GROUP_UPDATED_BY)
	   private String notifyGroupUpdatedBy;
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY_GROUP_UPDATED_TIME)
	   private LocalDateTime notifyGroupUpdatedTime;
	   @NgenAuditField(fieldName = NgenAuditFieldNameType.UN_HOLD_REMARKS)
	   private String unHoldRemarks;
}
