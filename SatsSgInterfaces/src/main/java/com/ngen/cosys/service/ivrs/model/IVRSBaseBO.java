/**
 * {@link IVRSBaseBO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS Base BO
 *
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
@NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
   eventName = NgenAuditEventType.IVRS)
public class IVRSBaseBO extends BaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   @NgenAuditField(fieldName = "Message Sequence No")
   @JsonProperty(value = "msgSeqNo")
   private String messageSequenceNo;
   @NgenAuditField(fieldName = "Request Date Time")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonProperty(value = "datetime")
   private LocalDateTime requestDateTime;
   //
   private BigInteger messageId;
   
}
