package com.ngen.cosys.uncollectedfreightout.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.SHIPMENT_IRP_BATCHJOB, repository = NgenAuditEventRepository.AWB, entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB)
public class ShipmentRemarksModel extends BaseBO {
   
   /**
    * System generated serial version id 
    */
   private static final long serialVersionUID = -171427877249099384L;
   
   private BigInteger id;
  // @NgenAuditField(fieldName = "Flight Id")
   private BigInteger flightId;
   @NgenAuditField(fieldName = "Remark Type")
   private String remarkType;
   @NgenAuditField(fieldName = "Shipment Remarks")
   private String shipmentRemarks;
  // @NgenAuditField(fieldName = "shipmentId")
   private BigInteger shipmentId;
   @NgenAuditField(fieldName = "shipmentType")
   private String shipmentType;
   @NgenAuditField(fieldName = "shipmentNumber")
   private String shipmentNumber;
   @NgenAuditField(fieldName = "shipmentDate")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   @NgenAuditField(fieldName = "Flight")
   private String flight;
   

}
