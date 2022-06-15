package com.ngen.cosys.shipment.temperatureLogEntry.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class takes care Stock.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.TEMPARATURE_LOG_ENTRY, repository = NgenAuditEventRepository.AWB, entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB)
public class TemperatureLogEntry extends BaseBO {
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = "Shipment Number")
   private String shipmentNumber;
   private int shipmentId;
   @NgenAuditField(fieldName = "Temperature")
   private String temperature;
   private String temperatureRange;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "Captured On")
   private LocalDateTime capturedOn;
   @NgenAuditField(fieldName = "Activity")
   private String activity;
   @NgenAuditField(fieldName = "Shipment Description")
   private String shipmentDescription;
   private String activityDescription;
   @NgenAuditField(fieldName = "Location Code")
   private String locationCode;
   private Boolean flagSave;
   private int ShipmentTemperatureLogEntryId;
}