package com.ngen.cosys.shipment.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NgenAudits({
@NgenAudit(eventName = NgenAuditEventType.SHIPMENT_ON_HOLD, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHC, entityType = NgenAuditEntityType.AWB),
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_LOCATION, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHC, entityType = NgenAuditEntityType.AWB)
})
public class ShipmentInventoryShcModel extends BaseBO {
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * shipment handling code
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
   private String shcInv;

}
