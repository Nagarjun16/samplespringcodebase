/**
 * 
 * CustomerAddressInfo.java
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
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
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
 * Model Class- CustomerAddressInfo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@Validated
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
public class CustomerAddressInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillCustomerInfoId;
   private long shipmentFreightWayBillCustomerAddressInfoId;
   private BigInteger neutralAWBCustomerInfoId;
   private BigInteger neutralAWBCustomerAddressInfoId;
   private BigInteger sidCustomerDtlsId;
   private BigInteger sidCustomerAddressInfoId;
   private String customerInfoId;

   @NotBlank(message = "awb.addr.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @NotNull(message = "awb.addr.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
		   NeutralAWBValidatorGroup.class,MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.STREET_ADDRESS)
   private String streetAddress1;
   private String streetAddress2;

   @NotBlank(message = "awb.place.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @NotNull(message = "awb.place.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @Size(max = 17, message = "data.min.max.length.required", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         NeutralAWBValidatorGroup.class, MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PLACE)
   private String customerPlace;

   @Size(max = 9, message = "data.min.max.length.required", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         NeutralAWBValidatorGroup.class, })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.STATE_CODE)
   private String stateCode;

   @NotBlank(message = "awb.country.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @NotNull(message = "awb.country.not.blank", groups = { NeutralAWBValidatorGroup.class })
   @Size(max = 2, message = "data.min.max.length.required", groups = { NeutralAWBValidatorGroup.class,
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.COUNTRY_CODE)
   private String countryCode;

   @Size(max = 9, message = "data.min.max.length.required", groups = { NeutralAWBValidatorGroup.class, })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         NeutralAWBValidatorGroup.class, MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.POSTAL)
   private String postalCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_CONTACT_INFO)
   private List<CustomerContactInfo> customerContactInfo;

   private BigInteger shipmentCustomerInfoId;

}
