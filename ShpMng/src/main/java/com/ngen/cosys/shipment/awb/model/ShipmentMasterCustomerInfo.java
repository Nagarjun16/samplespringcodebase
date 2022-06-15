package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterCustomerInfo extends BaseBO {

   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;

   private BigInteger shipmentId;

   private BigInteger customerId;

   private BigInteger appointedAgentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_TYPE)
   private String customerType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_CODE)
   private String customerCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_NAME)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
   private String customerName;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_EMAIL)
   private String contactEmail;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY_PARTY_CODE)
   private String notifyPartyCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOTIFY_PARTY_NAME)
   private String notifyPartyName;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUNT_NUMBER)
   private String accountNumber;
   private Boolean authorizedPersonnel;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
   @Length(max = 65, message = "Remarks Can't Be More Than 65 character length", groups = { SaveAWBDocument.class })
   @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {SaveAWBDocument.class })
   private String authorizationRemarks;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.APPOINTED_AGENT_CODE)
   private String appointedAgentCode;

   private BigInteger appointedAgent;

   private Boolean overseasCustomer = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_ADDRESS)
   @Valid
   private ShipmentMasterCustomerAddressInfo address;

   private Boolean isDirectCustomer = Boolean.FALSE;

   /*
    * Fwb Contactinfo
    */
   private List<ShipmentMasterCustomerContactInfo> fwbContactInfo;

   /*
    * Fwb Contactinfo
    */
   private List<ShipmentMasterCustomerContactInfo> ivrsContactInfo;

}