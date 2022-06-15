/**
 * 
 * ShipmentBookingRemark.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 December, 2017    NIIT      -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentBookingRemark Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT,entityRefFieldName = "flightID", eventName = NgenAuditEventType.MULTIPLE_SHIPMENT_BOOKING, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.SHIPMENT_REMARKS, repository = NgenAuditEventRepository.AWB)

public class ShipmentBookingRemark extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private long bookingRemarksId;
	private long flightBookingId;
	private Long partBookingId;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.REMARK_TYPE)
	private String remarkType;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.REMARK)
	private String remarks;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTDATE)
	private LocalDate shipmentDate;
	private BigInteger shipmentId;
	private long flightId;
	private String shipmentRemarks;
	private String shipmentType;
	
	
	
}
