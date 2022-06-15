package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.InboundRampValidationGroup;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd.
 *
 */
@ApiModel
@Getter
@ToString
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "ULD Number", entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.ULD)
public class RampCheckInPiggybackList extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger impRampCheckInPiggyBackULDInfoId;
   private BigInteger impRampCheckInId;
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
         InboundRampValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
   private String uldNumber;
   private boolean isSuccess = Boolean.TRUE;
   
}
