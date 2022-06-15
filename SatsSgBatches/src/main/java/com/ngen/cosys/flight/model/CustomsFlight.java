package com.ngen.cosys.flight.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
@NgenAudit(eventName = NgenAuditEventType.ACES_CUSTOMS, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
public class CustomsFlight extends BaseBO {
	private static final long serialVersionUID = 1L;
	private Long customsFlightId;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDING_POINT)
	private String flightBoardPoint;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFF_POINT)
	private String flightOffPoint;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.EXPORTIMPORTINDICATOR)
	private String importExportIndicator;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTTYPE)
	private String flightType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CANCELLED)
	private boolean flightCancelFlag;
	private boolean withCargoFlag;
	private LocalDateTime mrscompletedDate;
	private LocalDateTime mrssentDate;
	private String acknowledgeCode;
	private LocalDateTime fmaAcknowledgeDate;
	private LocalDateTime fnaAcknowledgeDate;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	
	   @NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.FLIGHT)
	   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.FLIGHT)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	private LocalDateTime flightDate;
	private LocalDateTime oprFlightDate;
	
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACESDATE)
	private LocalDateTime date = LocalDateTime.now();
	
  	@NgenAuditField(fieldName = "Date At Origin")
	private LocalDateTime dateorigin;
	
	@NgenAuditField(fieldName = "Flight Time")
	private LocalDateTime flighttime;


}
