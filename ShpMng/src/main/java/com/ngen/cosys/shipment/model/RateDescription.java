/**
 * 
 * RateDescription.java
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
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- RateDescription
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class RateDescription extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillRateDescriptionId;
   private BigInteger neutralAWBId;
   private BigInteger neutralAWBRateDescriptionId;
   private String rateLineNumber;
   
   @CheckWeightConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.GROSS_WEIGHT)
   @NotNull(message = "awb.gross.wgt.req",groups=NeutralAWBValidatorGroup.class)
   private BigDecimal grossWeight;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_CLASS_CODE)
   @NotNull(message = "billing.rate.class",groups=NeutralAWBValidatorGroup.class)
   private String rateClassCode;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_COMMODITY_NUMBER)
   private String commodityItemNo;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGEABLE_WEIGHT)
   private BigDecimal chargeableWeight;
  
   @Range(min=(long) 0.0001, max=999999999, message="billing.rate.charge", groups = {MaintainFreightWayBillValidator.class})
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_CHARGE)
   private BigDecimal rateChargeAmount;
   
   @Range(min=(long) 0.0001, max=999999999, message="billing.rate.charge", groups = {MaintainFreightWayBillValidator.class})
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTAL_CHARGE_AMT)
   private String totalChargeAmount;
   
   @NotNull(message = "data.pieces.required" ,groups= {NeutralAWBValidatorGroup.class})
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_PIECES)
   private BigInteger numberOfPieces;
   
   @NotNull(message = "export.weight.unitcode.required" ,groups= {MaintainFreightWayBillValidator.class})
   @Size(max = 1, message = "awb.wgt.unit.code")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_WEIGHT_UNIT_CODE)
   private String weightUnitCode;
   
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_DES_OTHER_INFO_NAWB)
   private List<RateDescOtherInfo> rateDescriptionOtherInfoForNawb;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RATE_DES_OTHER_INFO)
   private List<RateDescOtherInfo> rateDescriptionOtherInfo;


}
