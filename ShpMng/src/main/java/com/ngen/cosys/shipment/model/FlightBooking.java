/**
 * 
 * FlightBooking.java
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
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.FetchRoutingValidatorGroup;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- FlightBooking
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "carrierCode"
      + "flightNumber", originDate = "flightDate", groups = { MaintainFreightWayBillValidator.class,
            FetchRoutingValidatorGroup.class })
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class FlightBooking extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentFreightWayBillId;
   private long shipmentFreightWayBillFlightBookingId;
   private BigInteger neutralAWBId;

   @NotEmpty(message = "g.required")
   @NotNull(message = "flight.no.not.blank")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLTNUM)
   private String flightNumber;

   @NotNull(message = "flight.date.not.blank")
   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLTDATE)
   private LocalDate flightDate;

   @NotEmpty(message = "g.required")
   @NotNull(message = "flight.carrier.not.blank")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIERCODE)
   private String carrierCode;

   private String flightDays;

   private String flightKey;

   private String awbNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate awbDate;

}