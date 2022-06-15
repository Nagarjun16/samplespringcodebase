package com.ngen.cosys.icms.model.scheduleFlight;


import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;


import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef3;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *Model class contains schedule flight details
 */
@Component
@NgenCosysAppAnnotation
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
@ToString
@Setter
@Getter
public class ScheduleFlightInfo extends BaseBO {

   private static final long serialVersionUID = 1L;

   @NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.CARRIER)
   private String carrier;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_TYPE)
   private String flightType;
   private String flightNo;
   @NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
   private String serviceType;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
   private String aircraftType;
   private BigInteger flightId;
   private BigInteger flightSchedulePeriodId;
   private BigInteger flightScheduleId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FROMDATE)
   @NgenAuditEntityRef2(parentEntityType = NgenAuditEntityType.FLIGHT, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE) 
   private LocalDate scheduleStartDate;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TODATE)
   @NgenAuditEntityRef3(parentEntityType = NgenAuditEntityType.FLIGHT, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE) 
   private LocalDate scheduleEndDate;
   private boolean flightOnMon;
   private boolean flightOnTue;
   private boolean flightOnWed;
   private boolean flightOnThu;
   private boolean flightOnFri;
   private boolean flightOnSat;
   private boolean flightOnSun;
   private String flightScheduleStatus;
   private String flightScheduleRemark;
   private int scheduleSequenceNo;
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTLEGS)
   private List<ScheduleFlightLegInfo> scheduleFlightLegList;
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTSEGMENTS)
   private List<ScheduleFlightSegmentInfo> scheduleFlightSegmentList;
   private String createdUserId = "COSYS";
   

}