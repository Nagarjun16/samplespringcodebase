package com.ngen.cosys.impbd.model;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@Getter
@ToString
@Setter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = NgenAuditFieldNameType.ULDNUMBER, entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.ULD)
public class RampCheckInSHCInput extends BaseBO{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @CheckSpecialHandlingCodeConstraint
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
   private String shc;
}
