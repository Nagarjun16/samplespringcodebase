/**
 * 
 * NominatedHandlingParty.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 January , 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- NominatedHandlingParty
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
public class NominatedHandlingParty extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillNominatedHandlingPartyId;
   private long nominatedHandlingPartyId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBNominatedHandlingPartyId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_PARTY)
   private String handlingPartyName;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_PLACE)
   private String handlingPartyPlace;

}
