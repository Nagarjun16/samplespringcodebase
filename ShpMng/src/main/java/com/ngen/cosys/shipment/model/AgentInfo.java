/**
 * 
 * ShipmentFreightWayBillAgentInfo.java
 * 
 * Copyright
 * 
 * <PRE <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 January , 2018 NIIT -
 */

package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- ShipmentFreightWayBillAgentInfo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class AgentInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillAgentInfoId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBAgentInfoId;
   @Size(max = 14, message = "awb.acct.no.max", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "accountNumber")
   private String accountNumber;
   private String agentCode;
   @NgenAuditField(fieldName = "iATACargoAgentNumericCode")
   private BigInteger iATACargoAgentNumericCode;
   @NgenAuditField(fieldName = "cargAgentNumericCode")
   private String cargAgentNumericCode;

   @Digits(fraction = 0, integer = 7, message = "agent.cass.no.max", groups = {
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "iATACargoAgentCASSAddress")
   private BigInteger iATACargoAgentCASSAddress;
   @NgenAuditField(fieldName = "participantIdentifier")
   @Size(max = 3, message = "agent.participant.identifier.max ", groups = {
         MaintainFreightWayBillValidator.class })
   private String participantIdentifier;
   @NgenAuditField(fieldName = "agentName")
   private String agentName;
   @NgenAuditField(fieldName = "agentPlace")
   private String agentPlace;
}
