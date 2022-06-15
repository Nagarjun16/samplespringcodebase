/**
 * 
 * AddShipmentNumberForReuse.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 17 Jan, 2017 NIIT -
 */
package com.ngen.cosys.shipment.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for keeping all shipment which can be marked for reuse.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_REUSE_AWB_NUMBER, repository = NgenAuditEventRepository.AWB)
public class AddShipmentNumberForReuse extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * This field contains Awb Number Prefix
	 */
	private String awbPrefix;
	/**
	 * This field contains Awb Number Suffix
	 */
	private String awbSuffix;
	/**
	 * This field contains Shipment Number
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
	private String shipmentNumber;
	/**
	 * This field contains Origin
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	@CheckAirportCodeConstraint(mandatory=MandatoryType.Type.NOTREQUIRED)
	private String origin;
	/**
	 * This field contains Remarks
	 */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	@Length(max = 65, message = "INVDRMK")
	private String remarks;
}
