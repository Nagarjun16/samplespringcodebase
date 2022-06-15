package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckCarrierCodeConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterRoutingInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private BigInteger shipmentId;
   private BigInteger shipmentMasterRoutingId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TO)
   @CheckAirportCodeConstraint(groups = { SaveAWBDocument.class }, message = MandatoryType.Type.NOTREQUIRED)
   private String fromPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BY)
   @CheckCarrierCodeConstraint(groups = { SaveAWBDocument.class }, mandatory = MandatoryType.Type.NOTREQUIRED)
   private String carrier;

}