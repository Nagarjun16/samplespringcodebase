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
public class HouseDescriptionOfGoodsModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = -2698669626597300395L;

   private BigInteger houseId;
   private BigInteger id;

   @Size(max = 65, message = "data.min.max.length.required", groups = { HouseWayBillValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         HouseWayBillValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTENT)
   private String content;

}