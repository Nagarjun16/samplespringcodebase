package com.ngen.cosys.shipment.house.model;

import java.math.BigDecimal;
import java.math.BigInteger;

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
 * This model class represents ShipmentBookingDimension Request from UI.
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
public class HouseDimensionDetailsModel extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	
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
	@NgenAuditField(fieldName=NgenAuditFieldNameType.VOLUME_UNIT_CODE)
	private String shipmentUnitCode;
	private String flagUpdate ="Y";
	@NgenAuditField(fieldName=NgenAuditFieldNameType.VOLUME)
	private float volume;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.VOLUME_CODE)
	private String volumeCode;
	private boolean checkBoxFlag=false;
	private BigInteger houseDimensionId;
	private BigInteger houseDimensionDtlsId;
	private BigInteger houseId;
	private BigDecimal dimensionVolume;
	 private BigDecimal dimnVolumetricWeight;
}
