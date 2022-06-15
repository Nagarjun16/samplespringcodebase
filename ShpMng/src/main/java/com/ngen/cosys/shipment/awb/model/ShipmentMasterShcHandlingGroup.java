package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;

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
@NgenAudit(eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB, entityType = NgenAuditEntityType.AWB)
public class ShipmentMasterShcHandlingGroup extends BaseBO {

   private static final long serialVersionUID = 1L;
   private BigInteger id;
   private BigInteger shipmentId;
   
   //@NgenAuditField(fieldName = "Handling Group Id")
   private BigInteger handlingGroupId;

}