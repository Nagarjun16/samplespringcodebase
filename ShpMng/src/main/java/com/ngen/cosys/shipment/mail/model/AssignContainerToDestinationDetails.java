package com.ngen.cosys.shipment.mail.model;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue5;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

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
public class AssignContainerToDestinationDetails extends BaseBO  {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.MAILBAGNUMBER)
   private String mailBagNumber;
   
   private int shipmentId; 
   
   private int shipmentInventoryId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DISPATCH_NUMBER)
   private String dispatchNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RECEPTACLE_NUMBER)
   private String receptacleNumber;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private int pieces;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private int weight;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CATEGORY)
   private String category;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_TYPE)
   private String mailType;
   
   private int year;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_CODE)
   private String carrierCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AGENT_CODE)
   private String agentCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NEXT_DESTINATION)
   private String nextDestination;
   
   private String shipmentNumber;
   
   @NgenAuditField(fieldName=NgenAuditFieldNameType.ULD)
   @NgenAuditEntityValue5(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.ULD)
   private String storeLocation;
  

}
