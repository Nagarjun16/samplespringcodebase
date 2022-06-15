package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.validator.AWBDocumentConstraintsValidator;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB, entityType = NgenAuditEntityType.AWB)
public class ShipmentMasterLocalAuthorityDetails extends BaseBO {

   /*
    * Default system serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentMasterLocalAuthInfoDtlsId;
   private BigInteger transactionSequenceNo;
   
   private long shipmentMasterLocalAuthInfoId;

   private BigInteger customerAppAgentId;

   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_REFERENCE)
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "g.pattern.not.match" , groups = {SaveAWBDocument.class })
   private String referenceNumber;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.APPOINTED_AGENT_NAME)
   private String appointedAgentName;

   @Length(max=20, message="agent.license.len", groups = {SaveAWBDocument.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LICENSE)
   private String license;
   
   @Length(max=65, message="INVDRMK", groups = {SaveAWBDocument.class })
   @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {SaveAWBDocument.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
   private String remarks;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DELIVERY_ORDER_NO)
   private String deliveryOrderNo;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TS_REDOC_FLIGHT_KEY)
   private String tsRedocFlightKey;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TS_REDOC_FLIGHT_DATE)
   private String tsRedocFlightDate;

}
