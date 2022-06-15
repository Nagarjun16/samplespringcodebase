package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
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
public class HouseOtherChargeDeclarationModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 4653464536404497709L;

   private BigInteger houseId;
   private BigInteger id;

   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillValidationGroup.class })
   @Size(max = 3, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CURRENCYCODE)
   private String currencyCode;

   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillValidationGroup.class })
   @Size(max = 1, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PCINDICATOR)
   private String pcIndicator;

   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillValidationGroup.class })
   @Size(max = 1, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OTHERCHARGE)
   private String otherCharge;

   @Size(max = 12, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIAGEVALUE)
   private String carriageValue;

   @Size(max = 12, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMVALUE)
   private String customValue;

   @Size(max = 11, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.INSURANCEVALUE)
   private String insuranceValue;

}