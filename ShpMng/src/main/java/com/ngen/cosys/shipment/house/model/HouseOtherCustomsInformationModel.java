package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "ShipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB)
public class HouseOtherCustomsInformationModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 3907065459746621848L;

   private BigInteger houseId;
   private BigInteger id;

   @Size(max = 2, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.COUNTRY)
   private String country;

   @Size(max = 3, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.IDENTIFIER)
   private String identifier;

   @Size(max = 2, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CSRC_IDENTIFIER)
   private String csrcIdentifier;

   @Size(max = 35, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SCSRC_INFO)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         HouseWayBillValidationGroup.class })
   private String scsrcInformation;

}