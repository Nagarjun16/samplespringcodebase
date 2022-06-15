/**
 * 
 * SHC.java
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

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- SHC
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class SHC extends BaseBO {

   private static final long serialVersionUID = 1L;
   @CheckSpecialHandlingCodeConstraint(groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SPECIALHANDLINGCODE)
   private String specialHandlingCode;
   private String customsOrigin;
   private long shipmentFreightWayBillId;
   private BigInteger neutralAWBId;
   private BigInteger neutralAWBSHCId;
   private long shipmentFreightWayBillSHCId;
   private BigInteger shipmentId;
}
