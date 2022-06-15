package com.ngen.cosys.shipment.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class SenderReference extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillSenderReferenceId;

   @Size(max = 3, message = "awb.opi.apt.city.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "airportCityCode")
   private String airportCityCode;

   @Size(max = 2, message = "awb.Office.fun.desg.len", groups = {
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "officeFunctionDesignator")
   private String officeFunctionDesignator;

   @Size(max = 2, message = "awb.company.desg.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "companyDesignator")
   private String companyDesignator;

   @NgenAuditField(fieldName = "fileReference")
   private String fileReference;

   @Pattern(regexp = "^[A-Z]{0,3}$", message = "awb.opi.parti.identifier", groups = {
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "refParticipantIdentifier")
   private String refParticipantIdentifier;

   @Size(max = 17, message = "awb.opi.participant.name", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "refParticipantCode")
   private String refParticipantCode;

   @Size(max = 3, message = "awb.opi.apt.city.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "refAirportCityCode")
   private String refAirportCityCode;

}
