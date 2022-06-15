package com.ngen.cosys.impbd.flightdiscrepancylist.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectSegment;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.FlightType;

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
@NgenCosysAppAnnotation
@CheckValidFlightConstraint(originDate = "flightOriginDate", number = "flightKey", type = FlightType.Type.IMPORT, groups = {
      FlightValidatorGroup.class })
@UserCarrierValidation(shipmentNumber = "", flightKey = "flightKey", loggedInUser = "loggedInUser", type = "FLIGHT", groups = {FlightValidatorGroup.class })
@NgenAudit(entityFieldName = "flightKey", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_DISCREPANCY, repository = NgenAuditEventRepository.FLIGHT, entityRefFieldName = "flightId")
public class FlightDiscrepancyListModel extends FlightModel {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private boolean sendEvent;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;

   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightOriginDate;

   private BigInteger flightDiscrepncyListSentBy;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDiscrepncyListSentAt;

   private boolean flightCompletedAt;

   private List<FlightDiscrepancyList> flightDiscrepancyList;

   private Integer status;
   

   @InjectSegment(flightNumber = "flightKey", flightDate = "flightOriginDate", flightType = "I", segment = "segment", flightId = "")
   private String segment;
   
   @NgenAuditField(fieldName = "Version")
   private BigInteger version;
}