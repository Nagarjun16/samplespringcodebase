/**
 * 
 * CustomerInfo.java
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
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

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- CustomerInfo
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
public class CustomerInfo extends BaseBO {

   private static final long serialVersionUID = 1L;

   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillCustomerInfoId;
   private long overseasConsignee_ID;

   private BigInteger neutralAWBId;
   private BigInteger neutralAWBCustomerInfoId;
   private BigInteger sidHeaderId;
   private BigInteger sidCustomerDtlsId;
   private BigInteger shipmentCustomerInfoId;
   private BigInteger shipmentId;

   private boolean flag = false;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_TYPE)
   private String customerType;

   @NotEmpty(message = "customer.name.req", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @NotNull(message = "customer.name.req", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @Size(max = 70, message = "data.min.max.length.required", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         NeutralAWBValidatorGroup.class, MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_NAME)
   private String customerName;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUNT_NUMBER)
   private String customerAccountNumber;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_CODE)
   private String customerCode;
   
   private BigInteger customerId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DEST)
   private String destination;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_ADDRESS)
   private CustomerAddressInfo customerAddressInfo;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.APPOINTED_AGENT)
   private String appointedAgent;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RCAR_TYPE_CODE)
   private String rcarType;
}