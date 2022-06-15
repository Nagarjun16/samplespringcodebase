package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterCustomerContactInfo extends BaseBO {

   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger id;
   
   private BigInteger shipmentCustomerAddInfoId;
   
   private BigInteger shipmentId;
   
   private String customerType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_TYPE)
   private String contactTypeCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_NUMBER)
   private String contactTypeDetail;

}