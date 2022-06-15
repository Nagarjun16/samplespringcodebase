/**
 * SchFlightSeg.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
 * Model Class - SchFlightSeg.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)

public class SchFlightSeg extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private long flightScheduleID;
   private short codSegOrder;
   
   private String brdPt;
  
   private String offPt;
  
   private boolean flgLeg;
   
   private String serviceType;
   
   private boolean freightNA;
   
   private boolean techStop;
   private boolean noCargo;
  
   private boolean noMail;
}