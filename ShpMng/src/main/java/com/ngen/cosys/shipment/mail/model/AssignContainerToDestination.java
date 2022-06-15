package com.ngen.cosys.shipment.mail.model;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue5;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
@NgenAudit(eventName = NgenAuditEventType.ASSIGN_CONTAINER, repository = NgenAuditEventRepository.MAILBAG, entityType = NgenAuditEntityType.MAILBAG)
public class AssignContainerToDestination extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_CODE)
   private String carrierCode;
   
   @CheckAirportCodeConstraint
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;
   
   private String nestedId; 
   
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.REQUIRED) 
   @NgenAuditField(fieldName=NgenAuditFieldNameType.ULD)
   @NgenAuditEntityValue5(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.ULD)
   private String storeLocation;
   
   private  List<AssignContainerToDestinationDetails> details;

}
