/**
 * ScheduleFlightFact.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
 * Model Class - ScheduleFlightFact.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(scopeName = "prototype")

public class ScheduleFlightFact extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private long flightScheduleID;
   private short sequnceNumber;
   
   private String fact;
}