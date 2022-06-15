package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;


import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ASSIGNULDTROLLEYTOFLIGHT, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGOMANIFEST, repository = NgenAuditEventRepository.FLIGHT)
public class Segment extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NgenAuditField(fieldName="Segment ID")
	private BigInteger segmentId;
	@NgenAuditField(fieldName="Segment")
	private String segment;
	@NgenAuditField(fieldName="Board Point")
	private String flightBoardPoint;
	@NgenAuditField(fieldName="Off Point")
	private String flightOffPoint;
}
