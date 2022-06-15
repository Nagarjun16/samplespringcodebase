package com.ngen.cosys.impbd.summary.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY, repository = NgenAuditEventRepository.FLIGHT)
public class BreakDownSummaryTonnageHandledModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 7549567964609170805L;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARGO_TYPE)
   @NotNull(message = "ERROR_CARGO_TYPE_CANNOT_BE_EMPTY", groups = BreakDownSummaryValidation.class)
   private String cargoType;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARK)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "ERROR_CHARACTER_ALLOW_ALPHANUM_HYP_SPACE")
   private String remark;
   private String addTonnage;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TONNAGE)
   private BigDecimal tonnage;
   private BigInteger rowNumber;
   private BigInteger flightId;
   private BigInteger summaryId;
   private BigInteger tonnageId;
   
   private String serviceContractor;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ADD_TONNAGE)
   private BigDecimal additionalTonnage=BigDecimal.ZERO;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SUBSTRACT_TONNAGE)
   private BigDecimal subtractTonnage=BigDecimal.ZERO;
   private BigDecimal oldAdditionalTonnage=BigDecimal.ZERO;
   private BigDecimal oldSubtractTonnage=BigDecimal.ZERO;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICE_CONTRACTOR)
   private String serviceContractorName;
}