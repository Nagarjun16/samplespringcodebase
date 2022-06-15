package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;
import java.util.List;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//bug-81 fix
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HWB_LIST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(entityFieldName = "ShipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB)
public class HouseCustomerAddressModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger houseCustomerId;
   private BigInteger id;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.STREETADDRESS)
   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Size(max = 70, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
		   HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String streetAddress;

   private String city;
   /**
	 * 
	 * bug-81 fix
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.COUNTR_Y)
   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Size(max = 2, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class})
   @Pattern(regexp = "^[A-Za-z]*$", message = "data.invalid.telex.Onlycharacters", groups = {
         HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String country;
   /**
	 * 
	 * bug-81 fix
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PLACE)
   @NotNull(message = "data.required.mandatory", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Size(max = 17, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String place;
   /**
	 * 
	 * bug-81 fix end
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.STATE)
   @Size(max = 9, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String state;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.POSTALL)
   @Size(max = 9, message = "data.min.max.length.required", groups = { HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         HouseWayBillShipperValidationGroup.class,HouseWayBillConsigneeValidationGroup.class })
   private String postal;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACTS)
   private List<HouseCustomerContactsModel> contacts;
   
   //
   private BigInteger houseId;
	private String type;
   
}