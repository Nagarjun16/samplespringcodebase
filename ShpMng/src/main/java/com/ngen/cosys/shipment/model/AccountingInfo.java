/**
 * 
 * ShipmentFreightWayBillAccountingInformation.java
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

import javax.validation.constraints.Pattern;

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
 * Model Class- ShipmentFreightWayBillAccountingInformation
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
public class AccountingInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillAccountingInformationId;
   private long shipmentFreightWayBillId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBAccountingInfoId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.INFOIDENTIFIER)
   private String informationIdentifier;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUNTINGINFO)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
		   NeutralAWBValidatorGroup.class})
   private String accountingInformation;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SUPPCUSTINFO)
   private String supplementaryCustomerInformation;

}
