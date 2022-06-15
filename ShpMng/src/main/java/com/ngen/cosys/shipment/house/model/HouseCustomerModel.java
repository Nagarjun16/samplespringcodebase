package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//bug-81 fix
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HWB_LIST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(entityFieldName = "ShipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB)
public class HouseCustomerModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger houseId;
   private BigInteger id;

   private String code;
   private BigInteger appointedAgent;
   
   private String appointedAgentCode;
   
   /**
	 * 
	 * bug-81 fix
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NAME)
   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Size(max = 70, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
		   HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String name;
   /**
	 * 
	 * bug-81 fix end
	 * */

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TYPEE)
   private String type;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ADDRESS)
   private HouseCustomerAddressModel address;
   /**
	 * 
	 * bug-81 new fix
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_NUMBER)
   private String shipmentNumber;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
   private String hawbNumber;
   /**
	 * 
	 * bug-81 new fix end
	 * */
   private String oldName;
   private String oldCode;
}
