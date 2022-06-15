/**
 * 
 * SenderReferenceInformation.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class takes care of the Sender Reference Information.
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Component
@Scope(scopeName = "prototype")
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName="shipmentNumber",eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class SenderReferenceInformation extends BaseBO {
   private static final long serialVersionUID = 1L;
   private BigInteger neutralAWBId;
   private BigInteger neutralAWBSenderReferenceId;
   private String airportCityCode;
   private String officeFunctionDesignator;
   private String companyDesignator;
   private BigInteger fileReference;
   private String participantIdentifier;
   private BigInteger participantCode;
   private String participantAirportCityCode;
}
