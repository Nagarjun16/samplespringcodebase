package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentRemarksModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentRemarksId;
   private BigInteger shipmentId;
   private BigInteger flightId;

   // @NgenAuditField(fieldName = "Remark Type")
   private String remarkType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
   @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {SaveAWBDocument.class })
   private String shipmentRemarks;

   // @NgenAuditField(fieldName = "Shipment Number")
   private String shipmentNumber;

   // @NgenAuditField(fieldName = "Shipment Date")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentdate;

   //@NgenAuditField(fieldName = "Shipment Type")
   private String shipmentType;
   
   private String hawbNumber;

}