/**
 *   ChangeOfAwbHawb.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.changeofawb.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validatorgroup.ChangeAwbValidationGroup;
import com.ngen.cosys.shipment.validatorgroup.ChangeOfHawbGroup;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Model used for change of awb use case
 * @author yuganshu.k
 *
 */

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_CHANGE_AWB_HAWB, repository = NgenAuditEventRepository.AWB)
public class ChangeOfAwbHawb extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED,groups= {ChangeOfHawbGroup.class})
	private String shipmentNumber;
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NEW_SHIPMENT_NUMBER)
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED,groups= {ChangeAwbValidationGroup.class})
	private String newShipmentNumber;
	@NotNull(message = "data.mandatory.required",groups= {ChangeAwbValidationGroup.class})
	private String reasonOfChangeAwb;
	private String reasonOfChangeAwbForAuditTrail;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
	@NotNull(message = "data.mandatory.required",groups= {ChangeOfHawbGroup.class})
	private String hawbNumber;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NEW_HAWB_NUMBER)
	@NotNull(message = "data.mandatory.required",groups= {ChangeOfHawbGroup.class})
	private String newHawbNumber;
	@NotNull(message = "data.mandatory.required",groups= {ChangeOfHawbGroup.class})
	private String reasonOfChangeHawb;
	private String reasonOfChangeHawbForAuditTrail;
	private int shipmentFreightHouseListByHAWBId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REASON_OF_CHANGE)
	private String shipmentRemark;
	
	private int shipmentHouseId;
	
	}
