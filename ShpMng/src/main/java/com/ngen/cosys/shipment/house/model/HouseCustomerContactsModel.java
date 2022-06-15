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
import com.ngen.cosys.shipment.house.validator.HouseWayBillConsigneeValidationGroup;
import com.ngen.cosys.shipment.house.validator.HouseWayBillShipperValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "ShipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB)
public class HouseCustomerContactsModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger houseCustomerAddressId;
   private BigInteger id;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TYPEE)
   @Size(max = 3, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String type;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DETAIL)
   @Size(max = 25, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9]*$", message = "data.invalid.contact.details", groups = {
         HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String detail;

}