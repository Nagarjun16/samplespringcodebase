/**
 * 
 * CustomerContactInfo.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 January , 2018    NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- CustomerContactInfo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
public class CustomerContactInfo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	private long shipmentFreightWayBillCustomerAddressInfoId;
	private long shipmentFreightWayBillCustomerContactInfoId;
	private BigInteger neutralAWBCustomerAddressInfoId;
	private BigInteger sidCustomerAddressInfoId;
	@NotBlank(message = "g.required")
	@NotNull(message = "g.required")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_TYPE)
	private String contactIdentifier;
	@NotBlank(message = "g.blank")
	
	@NotNull(message = "g.required")
	@Size(max = 25, message = "awb.contact.dtl.max")
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_NUMBER)
	private String contactDetail;
	private String neutralAWBAddressInfoId;
	private BigInteger expNeutralAWBCustomerContactInfoId;
	
	private String contactTypeCode;
	private BigInteger shipmentCustomerAddInfoId;
	
}
