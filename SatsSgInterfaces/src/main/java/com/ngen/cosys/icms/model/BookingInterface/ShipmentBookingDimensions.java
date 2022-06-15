package com.ngen.cosys.icms.model.BookingInterface;

import java.time.LocalDate;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT,entityRefFieldName = "flightID", eventName = NgenAuditEventType.MULTIPLE_SHIPMENT_BOOKING, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.SHIPMENT_BOOKING, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB,entityRefFieldName = "shipmentId", eventName = NgenAuditEventType.DIMENSION_BOOKING, repository = NgenAuditEventRepository.AWB)
public class ShipmentBookingDimensions extends BaseBO {
	private static final long serialVersionUID = 1L;
	private long bookingDimensionId;
	private long bookingId;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private int transactionSequenceNo;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.PIECES)
	private int pieces;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.WEIGHT)
	private double weight;
	private String weightUnitCode;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.LENGTH)
	private int length;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.WIDTH)
	private int width;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.HEIGHT)
	private int height;	
	@NgenAuditField(fieldName=NgenAuditFieldNameType.VOLUME)
	private float volume;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.VOLUME_CODE)
	private String volumeUnitCode;
	private String unitCode;
	private String shipmentUnitCode;
	private String userType;
	private String createdBy;
}
