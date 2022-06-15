package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd.
 *
 */
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "ULD Number", entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN, repository = NgenAuditEventRepository.ULD)
public class RampCheckInPiggyback extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger piggybackId;
	private BigInteger impRampCheckInId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	private String uldNumber;
	private BigInteger flightId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
	private String origin;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONTENTCOD)
	private String contentCode;
	private String flight;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	
	private String remarks;

}
