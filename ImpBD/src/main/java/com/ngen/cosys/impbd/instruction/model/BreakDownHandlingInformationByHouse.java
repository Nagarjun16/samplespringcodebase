package com.ngen.cosys.impbd.instruction.model;

import java.math.BigInteger;

import javax.validation.constraints.Pattern;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.instruction.validator.BreakDownHandlingInstructionValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION, repository = NgenAuditEventRepository.AWB)
public class BreakDownHandlingInformationByHouse extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 5001583677179416977L;

   private BigInteger impBreakDownHandlingInformationByHouseId;

   private BigInteger impBreakDownHandlingInformationId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "data.invalid.contact.details", groups = BreakDownHandlingInstructionValidationGroup.class)
   private String houseNumber;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_INSTRUCTION)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = BreakDownHandlingInstructionValidationGroup.class)
   private String breakdownInstruction;

}