/**
 * OperativeFlightFct.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
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
 * This class takes care for Flight Fact entity. 
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_CREATION_UPDATION, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight", entityType = NgenAuditEntityType.FLIGHT)
public class OperativeFlightFct extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * flightId for operating flight Fact
    */
   private long flightId;
   /**
    * seqNo for operating flight Fact
    */
   private BigDecimal seqNo;
   /**
    * remarks for operating flight Fact
    */
   @NgenAuditField(fieldName = "Remarks")
   private String remarks;
   /**
    * createdUserCode for operating flight Fact
    */
   private String createdUserCode;
   /**
    * createdDateTime for operating flight Fact
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;
   
   /**
    * createdDateTime for operating flight Segment
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime createdOn;
}