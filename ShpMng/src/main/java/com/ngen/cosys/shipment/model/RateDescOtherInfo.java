/**
 * 
 * RateDescOtherInfo.java
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

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- RateDescOtherInfo
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
public class RateDescOtherInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long rateDescriptionOtherInfoId;
   private long shipmentFreightWayBillId;
   private BigInteger neutralAWBId;
   private BigInteger neutralAWBRateDescriptionOtherInfoId;
   @NgenAuditField(fieldName = "dimensionLength")
   private BigInteger dimensionLength;
   @NgenAuditField(fieldName = "dimensionWIdth")
   private BigInteger dimensionWIdth;
   @NgenAuditField(fieldName = "dimensionHeight")
   private BigInteger dimensionHeight;
   @NgenAuditField(fieldName = "numberOfPieces")
   private BigInteger numberOfPieces;

   private BigDecimal volumeAmount;
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @Min(value = 6, message = "awb.harmonised.comm.code.len")
   @NgenAuditField(fieldName = "harmonisedCommodityCode")
   private String harmonisedCommodityCode;
   private String countryCode;
   private String serviceCode;
   private String measurementUnitCode;
   private BigDecimal weight;
   private String volumeUnitCode;

   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @NgenAuditField(fieldName = "uldNumber")
   private String uldNumber;
   @Min(value = 6, message = "awb.slac.len")
   @NgenAuditField(fieldName = "slacCount")
   private BigInteger slacCount;
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         MaintainFreightWayBillValidator.class ,NeutralAWBValidatorGroup.class})
   @NgenAuditField(fieldName = "natureOfGoodsDescription")
   private String natureOfGoodsDescription;
   private String rateLine;
   private BigInteger neutralAWBRateDescriptionId;
   private String type;
   @NgenAuditField(fieldName = "noDimensionAvailable")
   private boolean noDimensionAvailable;
   private long shipmentFreightWayBillRateDescriptionId;
   private long shipmentFreightWayBillRateDescriptionOtherInfoId;
}
