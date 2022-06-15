/**
 * 
 * NeutralAwbCustoms.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */


package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This Model Class takes care of the Neutral AWB Customs.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName="shipmentNumber",eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class NeutralAwbCustoms extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private BigInteger neutralAWBLocalAuthorityInfoId;
   
   private BigInteger neutralAWBId;
   @NotEmpty(message = "agent.local.auth.req",groups=NeutralAWBValidatorGroup.class)
   @NotNull(message="agent.local.auth.req",groups=NeutralAWBValidatorGroup.class)
  private String type;
  @Valid
  private List<NeutralAWBLocalAuthDetails> neutralAWBLocalAuthDetails;
}
