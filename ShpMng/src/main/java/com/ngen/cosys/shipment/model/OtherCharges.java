/**
 * 
 * ShipmentFreightWayBillOtherCharges.java
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
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- ShipmentFreightWayBillOtherCharges
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
public class OtherCharges extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillOtherChargesId;
   private BigInteger neutralAWBId;
   private BigInteger neutralAWBOtherChargesId;

   @NotNull(message = "billing.charge.indicator.req")
   @NotBlank(message = "billing.charge.indicator.req")
   @NotEmpty(message = "billing.charge.indicator.req")
   @Size(max = 1, message = "billing.charge.indicator.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_PC_INDICATOR)
   private String otherChargeIndicator;
   @Size(max = 2, message = "billing.chg.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NotBlank(message = "billing.chg.code.req")
   @NotEmpty(message = "billing.chg.code.req")
   @NotNull(message = "billing.chg.code.req")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_CHARGE_CODE)
   private String otherChargeCode;
   
   private String chargeType;

   @Size(max = 1, message = "entitlementCode", groups = { MaintainFreightWayBillValidator.class })
   private String entitlementCode;

   @NotNull(message = "billing.charge.amount.req")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_CHARGE_AMMOUNT)
   private String chargeAmount;
}
