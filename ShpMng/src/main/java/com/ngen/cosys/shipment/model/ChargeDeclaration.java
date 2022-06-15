/**
 * 
 * ChargeDeclaration.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 January , 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- ChargeDeclaration
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class ChargeDeclaration extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillChargeDeclarationId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBChargeDeclarationId;
   private BigInteger sidHeaderId;
   @NotNull(message = "billing.currency.code.req")
   @NotEmpty(message = "billing.currency.code.req")
   @Size(max = 3, message = "billing.currency.code.len")
   @NotBlank(message = "billing.currency.code.req", groups = { MaintainFreightWayBillValidator.class,
         NeutralAWBValidatorGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CURRENCYCODE)
   private String currencyCode;

   @NotBlank(message = "billing.chg.code.req", groups = { NeutralAWBValidatorGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGE_CODE)
   private String chargeCode;
 
   private String fwbCarriageValueDeclaration;
   private String fwbCustomsValueDeclaration;
   private String fwbInsuranceValueDeclaration;
   @NgenAuditField(fieldName = "carriageValueDeclarationNawb")
   private BigDecimal carriageValueDeclarationNawb;
   @NgenAuditField(fieldName = "customsValueDeclarationNawb")
   private BigDecimal customsValueDeclarationNawb;
   @NgenAuditField(fieldName = "insuranceValueDeclarationNawb")
   private BigDecimal insuranceValueDeclarationNawb;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIAGEVALUE)
   private String carriageValueDeclaration;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMVALUE)
   private String customsValueDeclaration;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.INSURANCEVALUE)
   private String insuranceValueDeclaration;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PCINDICATOR)
   @NotEmpty(message = "billing.chg.dec.code.req", groups = { NeutralAWBValidatorGroup.class })
   @NotNull(message = "billing.chg.dec.code.req")
   @NotBlank(message = "billing.chg.dec.code.len", groups = {
         MaintainFreightWayBillValidator.class })
   private String prepaIdCollectChargeDeclaration;
   @NgenAuditField(fieldName = "noCarriageValueDeclared")
   private String noCarriageValueDeclared;
   @NgenAuditField(fieldName = "noCustomsValueDeclared")
   private String noCustomsValueDeclared;
   @NgenAuditField(fieldName = "noInsuranceValueDeclared")
   private String noInsuranceValueDeclared;

}
