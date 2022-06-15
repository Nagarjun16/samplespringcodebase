package com.ngen.cosys.impbd.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.ECC_SHIPMNT, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB)

public class SpecialHandlingCode extends BaseBO{

   /**
    * Auto generated SUID
    */
   private static final long serialVersionUID = -3068383246422214224L;
   @NgenAuditField(fieldName = "SHC Code")
   @CheckSpecialHandlingCodeConstraint(mandatory = MandatoryType.Type.REQUIRED)
   private String shc;

   private int worksheetShipmentID;
}
