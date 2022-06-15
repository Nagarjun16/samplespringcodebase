/**
 * OperativeFlightJoint.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care for Flight Joint entity.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@ApiModel
@Component
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION_UPDATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight", entityType = NgenAuditEntityType.FLIGHT)
public class OperativeFlightJoint extends BaseBO {
   /**
     * The default serialVersionUID.
     */
   private static final long serialVersionUID = 1L;
   /**
    * date std of operating flight joint
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "DepartureDateTime")
   private LocalDateTime departureDateTime;
   /**
    * boarding point of operating flight joint
    */
   @NgenAuditField(fieldName = "BoardingPoint")
   private String boardingPoint;
   /**
    * carrier code of operating flight joint
    */
   @NgenAuditField(fieldName = "JointFlightCarCode")
   private String jointFlightCarCode;
   /**
    * Carrier no for operating flight joint
    */
   @NgenAuditField(fieldName = "JointFlightCarNo")
   private String jointFlightCarNo;
   /**
    * seqNo for operating flight joint
    */
   private String seqNo;
   /**
    * carDesc for operating flight joint
    */
   @NgenAuditField(fieldName = "Car Description")
   private String carDesc;
   /**
    * createdUserCode for operating flight joint
    */
   private String createdUserCode;
   /**
    * createdDateTime for operating flight joint
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;
   /**
    * createdDateTime for operating flight Segment
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime createdOn;
    /**
    * flightId for operating flight joint
    */
    private long flightId;
}