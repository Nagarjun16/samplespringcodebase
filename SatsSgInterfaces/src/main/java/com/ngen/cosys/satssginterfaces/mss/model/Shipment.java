package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.UNLOAD_SHIPMENT, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.FLIGHT, entityFieldName = "flightKey", eventName = NgenAuditEventType.FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT, required = false)
public class Shipment extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private BigInteger shipmentPieces;

   @NgenAuditField(fieldName = "Assigned Pieces")
   private BigInteger assignedPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LOADEDPIECES)
   private BigInteger loadedPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.UNLOADEDPIECES)
   private BigInteger unloadPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private BigDecimal shipmentWeight;

   @NgenAuditField(fieldName = "Assigned Weight")
   private BigDecimal assignedWeight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LOADEDWEIGHT)
   private BigDecimal loadedWeight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.UNLOADEDWEIGHT)
   private BigDecimal unloadWeight;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @NgenAuditField(fieldName = "Shipment date")
   private LocalDate shipmentDate;

   @NgenAuditField(fieldName = "Shipment Number")
   private String shipmentNumber;

   private String partShipment = "0";
   private String transferType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;

   private String weightCode;
   private String natureOfGoods;
   private String shcs;
   private String tagNumbers;

   @NgenAuditField(fieldName = "Booked Pieces")
   private String bookedPieces;

   @NgenAuditField(fieldName = "Booked Weight")
   private String bookedWeight;

   private String shipmentType;

   private BigInteger awbPieces;

   private boolean locked;

   private List<String> shcCodes;

}