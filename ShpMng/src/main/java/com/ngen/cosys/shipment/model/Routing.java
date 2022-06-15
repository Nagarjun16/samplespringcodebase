/**
 * 
 * Routing.java
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
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.validator.annotations.CheckAirportCityCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckCarrierCodeConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- Routing
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
public class Routing extends BaseBO {

	private static final long serialVersionUID = 1L;
	
	@CheckAirportCityCodeConstraint(groups = {
			MaintainFreightWayBillValidator.class }, mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TO)
	private String airportCode;

	@NotEmpty(message = "g.required")
	@NotNull(message = "g.carrier.code.cannot.blank")
	@CheckCarrierCodeConstraint(groups = {
			MaintainFreightWayBillValidator.class }, mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BY)
	private String carrierCode;
	private long shipmentFreightWayBillRoutingId;
	private long shipmentFreightWayBillId;
	private BigInteger neutralAWBId;
	private BigInteger neutralAWBRoutingId;
	private BigInteger sidHeaderId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FROM)
	private String from;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FROM_ORIGIN)
	private String fromOrigin;
	private String viaDest;
	private String toDest;
	@NotEmpty(message = "g.required")
	@NotNull(message = "awb.destination.empty")
	private String to;
	private String flightKey;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	private String tocarrierCode;
	private String svc;
	private String shipmentId;
}
