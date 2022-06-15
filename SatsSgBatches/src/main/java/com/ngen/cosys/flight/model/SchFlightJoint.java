/**
 * SchFlightJoint.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.util.List;

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
 * Model Class - SchFlightJoint.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope(scopeName = "prototype")

public class SchFlightJoint extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private long flightScheduleID;

   private String jointFlightKey;
   
   private String jointFlightName;
  
   private List<String> boardPoint;
}