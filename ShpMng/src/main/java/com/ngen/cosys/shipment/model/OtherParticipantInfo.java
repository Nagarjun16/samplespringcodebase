/**
 * 
 * OtherParticipantInfo.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 January , 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

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
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- OtherParticipantInfo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class OtherParticipantInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillOtherParticipantInformationId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBOtherParticipantInfoId;

   @Size(max = 35, message = "awb.participant.name.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_PARTCIPANT_NAME)
   private String participantName;

   @Size(max = 3, message = "awb.opi.apt.city.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_AIRPORT_CODE)
   private String airportCityCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_OFF_DSG)
   private String officeFunctionDesignator;

   @Size(max = 2, message = "awb.company.desg.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_COM_DSG)
   private String companyDesignator;

   @Pattern(regexp = "^[A-Z0-9]{0,15}$", message = "awb.file.ref.len", groups = {
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_FR)
   private String fileReference;

   @Pattern(regexp = "^[A-Z]{0,3}$", message = "awb.opi.parti.identifier", groups = {
         MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_PI)
   private String opiParticipantIdentifier;

   @Size(max = 17, message = "awb.opi.participant.name", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_PC)
   private String opiParticipantCode;

   @Size(max = 3, message = "awb.opi.apt.city.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.OPI_AC)
   private String opiAirportCityCode;

}
