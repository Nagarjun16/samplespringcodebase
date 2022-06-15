package com.ngen.cosys.impbd.delaystatus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.model.FlightModel;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.REOPEN_FLIGHT, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CLOSE_FLIGHT, repository = NgenAuditEventRepository.FLIGHT)
//@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_DELAY_STATUS, repository = NgenAuditEventRepository.FLIGHT)
public class DelayStatusSearch extends FlightModel {

   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;

   /**
    * Terminal by default
    */
   private int terminals;
   /**
    * dateFrom
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime fromDate;
   /**
    * dateAndTimeTo
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime toDate;

   /**
    * Carrier Group
    */
   private String carrierGroup;

   /**
    * flight key
    */
   private String flight;
   /**
    * date
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate date;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_CLOSED)
   private String flightClosed;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_CLOSED_AT)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightClosedAt;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_REOPENED_AT)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightReopenedAt;

   
   private List<BreakDownSummaryModel> delayDetails;

}