package com.ngen.cosys.icms.model.operationFlight;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

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

/**
 * Model class contains operational flight segment details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
public class OperationalFlightSegmentInfo extends BaseBO{
 
    private static final long serialVersionUID = 1L;
    @NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPOINT)
    private String segmentOrigin;
    
    @NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String segmentDestination;
	private String segmentStatus;
	private String departureTime;
	private String arrivalTime;
	private int segmentOrder;
	private BigInteger flightId;
	private String dateSTD;
	private String dateSTA;
	private String noFreight;
	private String technicalStop;
	private String nilCargoIndicator;
	private String noMailFlag;
	private String flightKey;
	private String flightDate;
	private Boolean isDeleteSegment=false;
	private String createdUserId = "COSYS";
	

}
