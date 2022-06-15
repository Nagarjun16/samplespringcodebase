package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterLocalAuthorityInfo extends BaseBO {

   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private long shipmentMasterLocalAuthInfoId;
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LOCAL_AUTHORITY_TYPE)
   private String type;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LOCAL_AUTHORITY_DETAILS)
   @Valid
   private List<ShipmentMasterLocalAuthorityDetails> details;

}