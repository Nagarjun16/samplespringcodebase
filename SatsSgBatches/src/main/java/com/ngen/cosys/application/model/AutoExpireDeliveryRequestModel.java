package com.ngen.cosys.application.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AUTO_EXPIRE_PO, repository = NgenAuditEventRepository.AWB)
public class AutoExpireDeliveryRequestModel extends BaseBO {

   /**
    * Default generated serail version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger deliveryRequestId;
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = "Shipment Number")
   private String shipmentNumber;

   @NgenAuditField(fieldName = "deliveryRequestOrderNo")
   private String deliveryRequestOrderNo;

   @NgenAuditField(fieldName = "Shipment Date")
   private LocalDate shipmentDate;

}