
/**
 * 
 * NeutralAWBLocalAuthDetails.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */

package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
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
 * This Model Class takes care of the Neutral AWBLocal AuthDetails.
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
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName="shipmentNumber",eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class NeutralAWBLocalAuthDetails extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private BigInteger neutralAWBLocalAuthorityDetailsId;

   private BigInteger neutralAWBLocalAuthorityInfoId;

   private String referenceNumber;

   private BigInteger customerAppAgentId;

   private String license;
   

   private String remarks;

   private String exemptionCode;
   private String aces;
  
 

}
